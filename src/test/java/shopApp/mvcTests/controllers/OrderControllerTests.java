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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    void givenEmptyCartorderCartReturnsBadRequestStatus() throws Exception {
        mvc.perform(
                post("/customers/orders").contentType(MediaType.APPLICATION_JSON).header(authHeaderKey, testJWT))
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
