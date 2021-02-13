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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthenticationControllerTests {

    @Autowired
    MockMvc mvc;

    String testJWT;

    @BeforeAll
    public void RegisterValidTestCustomerInDatabase() throws Exception {
        Customer customer = new Customer();
        customer.setUsername("TestCustomer");
        customer.setPassword("TestCustomer");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String testCustomerJSON = objectWriter.writeValueAsString(customer);
        mvc.perform(
                post("/customers").contentType(MediaType.APPLICATION_JSON).content(testCustomerJSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenValidUsernamePasswordPairAuthenticationReturnsValidJWT() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("TestCustomer");
        request.setPassword("TestCustomer");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
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
    public void givenInvalidUsernamePasswordPairAuthenticationReturnsForbiddenStatus() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String authTestJSON = objectWriter.writeValueAsString(request);
        mvc.perform(
                post("/authentication").contentType(MediaType.APPLICATION_JSON).content(authTestJSON))
                .andExpect(status().isBadRequest());
    }

    @AfterAll
    public void deleteTestUserFromDatabaseAfterTestWithTestJWT() throws Exception {
        mvc.perform(
                delete("/users").contentType(MediaType.APPLICATION_JSON).header("Authorization", testJWT))
                .andExpect(status().isOk());
    }
}
