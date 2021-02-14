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
public class CartControllerTests {

    @Autowired
    MockMvc mvc;

    String testJWT;
    String authHeaderKey = "Authorization";
    ObjectWriter objectWriter;
    MvcResult cartItemID;

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
    void givenValidAuthenticatedUserGETCartReturnsCartJSONAndOkStatus() throws Exception {
        mvc.perform(
                get("/customers/carts").contentType(MediaType.APPLICATION_JSON).header(authHeaderKey, testJWT))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json"))
                .andExpect(jsonPath("$.totalCost", Matchers.greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.cartItems", Matchers.notNullValue()))
                .andExpect(jsonPath("$._links.self.href", is("http://localhost/customers/carts")))
                .andExpect(jsonPath("$._links.OrderCart.href", is("http://localhost/customers/orders")));
    }

    @Test
    void givenNotAuthenticatedUserGETCartReturnsForbiddenStatus() throws Exception {
        mvc.perform(
                get("/customers/carts").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void givenValidAuthenticatedUserPUTProductToCartReturnsStatusOk() throws Exception {
        cartItemID = mvc.perform(
                put("/customers/carts/2").contentType(MediaType.APPLICATION_JSON).header(authHeaderKey,testJWT))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void givenValidAuthenticatedUserPUTProductToCartWithInvalidIdReturnsBadRequest() throws Exception {
        mvc.perform(
                put("/customers/carts/0").contentType(MediaType.APPLICATION_JSON).header(authHeaderKey,testJWT))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenCartWithItemsRemoveItemReturnsOkStatus() throws Exception {
        /*
        MvcResult cartItemID = mvc.perform(
                put("/customers/carts/1").contentType(MediaType.APPLICATION_JSON).header(authHeaderKey,testJWT))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

         */

        mvc.perform(
                delete("/customers/carts/" + cartItemID.getResponse().getContentAsString()).contentType(MediaType.APPLICATION_JSON).header(authHeaderKey,testJWT))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void givenCartWithItemsChangeItemAmountReturnsOkStatus() throws Exception {
        MvcResult cartItemID = mvc.perform(
                put("/customers/carts/1").contentType(MediaType.APPLICATION_JSON).header(authHeaderKey,testJWT))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        mvc.perform(
                put("/customers/carts/" + cartItemID.getResponse().getContentAsString() + "/6").contentType(MediaType.APPLICATION_JSON).header(authHeaderKey,testJWT))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void givenCartWithoutItemsChangeItemAmountReturnsBadRequest() throws Exception {
        mvc.perform(
                put("/customers/carts/0/0").contentType(MediaType.APPLICATION_JSON).header(authHeaderKey,testJWT))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
    @Test

    void givenCartWithItemsClearCartReturnsStatusOk() throws Exception {
        /*MvcResult cartItemID = mvc.perform(
                put("/customers/carts/1").contentType(MediaType.APPLICATION_JSON).header(authHeaderKey,testJWT))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

         */

        mvc.perform(
                delete("/customers/carts").contentType(MediaType.APPLICATION_JSON).header(authHeaderKey,testJWT))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @AfterAll
    public void deleteTestUserFromDatabaseAfterTest() throws Exception {
        mvc.perform(
                delete("/users").contentType(MediaType.APPLICATION_JSON).header(authHeaderKey, testJWT))
                .andExpect(status().isOk());
    }
}
