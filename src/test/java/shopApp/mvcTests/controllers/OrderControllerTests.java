package shopApp.mvcTests.controllers;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import shopApp.model.jwt.AuthenticationRequest;
import shopApp.model.user.customer.Customer;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderControllerTests {

    @Autowired
    MockMvc mvc;

    String testJWT;
    String authHeaderKey = "Authorization";
    ObjectWriter objectWriter;

    @BeforeAll
    public void RegisterValidTestCustomerInDatabaseAndAuthenticateForGettingTestJWT() throws Exception {
        Customer testCustomer = new Customer();
        testCustomer.setUsername("TestCustomer");
        testCustomer.setPassword("TestCustomer");
        initializeObjectWriter();
        String testCustomerJSON = objectWriter.writeValueAsString(testCustomer);
        mvc.perform(
                post("/customers").contentType(MediaType.APPLICATION_JSON).content(testCustomerJSON))
                .andExpect(status().isOk());
        authenticateForObtainingTestJWT(testCustomer);
    }
    private void initializeObjectWriter(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
    }

    private void authenticateForObtainingTestJWT(Customer testCustomer) throws Exception {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername(testCustomer.getUsername());
        request.setPassword(testCustomer.getPassword());
        String authTestJSON = objectWriter.writeValueAsString(request);
        MvcResult mvcResult = mvc.perform(
                post("/authentication").contentType(MediaType.APPLICATION_JSON).content(authTestJSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwt", Matchers.notNullValue(String.class)))
                .andReturn();
        String jwt = mvcResult.getResponse().getContentAsString();
        JsonNode node = new ObjectMapper().readTree(jwt);
        testJWT = "Bearer " + node.get("jwt").textValue();
    }

    @Test
    void givenEmptyCartOrderCartReturnsBadRequestStatus() throws Exception {
        mvc.perform(
                post("/customers/orders").contentType(MediaType.APPLICATION_JSON).header(authHeaderKey, testJWT))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenNotEmptyCartOrderCartReturnsStatusOkAndOrderJSON() throws Exception {
        addItemsToCart();
        mvc.perform(
                post("/customers/orders").contentType(MediaType.APPLICATION_JSON).header(authHeaderKey, testJWT))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json"))
                .andExpect(jsonPath("$.totalCost", Matchers.equalTo(10000)))
                .andExpect(jsonPath("$.orderItems", Matchers.notNullValue()))
                .andExpect(jsonPath("$._links.self.href", Matchers.containsString("http://localhost/customers/orders/")))
                .andExpect(jsonPath("$._links.OrderHistory.href", is("http://localhost/customers/orders")))
                .andExpect(jsonPath("$._links.Payment.href", is("http://localhost/payment/make")));
    }

    private void addItemsToCart() throws Exception {
        mvc.perform(
                put("/customers/carts/1").contentType(MediaType.APPLICATION_JSON).header(authHeaderKey,testJWT))
                .andExpect(status().isOk());
    }

    @Test
    void givenEmptyOrderHistoryGETHistoryReturnsStatusOkAndEmptyList() throws Exception {
        mvc.perform(
                get("/customers/orders").contentType(MediaType.APPLICATION_JSON).header(authHeaderKey, testJWT))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void givenNonExistingOrderReadOrderReturnsBadRequest() throws Exception {
        mvc.perform(
                get("/customers/orders/0").contentType(MediaType.APPLICATION_JSON).header(authHeaderKey, testJWT))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenExistingForDifferentCustomerOrderReadOrderReturnsBadRequest() throws Exception {
        mvc.perform(
                get("/customers/orders/13").contentType(MediaType.APPLICATION_JSON).header(authHeaderKey, testJWT))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @AfterAll
    public void deleteTestUserFromDatabaseAfterTestWithTestJWT() throws Exception {
        mvc.perform(
                delete("/users").contentType(MediaType.APPLICATION_JSON).header(authHeaderKey, testJWT))
                .andExpect(status().isOk());
    }
}
