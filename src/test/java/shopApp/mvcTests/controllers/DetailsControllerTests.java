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
import shopApp.model.user.customer.Address;
import shopApp.model.user.customer.Customer;
import shopApp.model.user.customer.Details;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DetailsControllerTests {

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
    void givenAuthenticatedCustomerGETDetailsReturnsOkStatusAndValidDetailsJSON() throws Exception {
        mvc.perform(
                get("/customers/details").contentType(MediaType.APPLICATION_JSON).header(authHeaderKey, testJWT))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json"))
                .andExpect(jsonPath("$.firstName", is("empty")))
                .andExpect(jsonPath("$.secondName", is("empty")))
                .andExpect(jsonPath("$.email", is("empty")))
                .andExpect(jsonPath("$.address.country", is("empty")))
                .andExpect(jsonPath("$.address.state", is("empty")))
                .andExpect(jsonPath("$.address.city", is("empty")))
                .andExpect(jsonPath("$.address.street", is("empty")))
                .andExpect(jsonPath("$.address.postalCode", is("empty")));
    }

    @Test
    void givenNotAuthenticatedCustomerGETDetailsReturnsForbiddenStatus() throws Exception {
        mvc.perform(
                get("/customers/details").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void givenValidCustomerDetailsPUTDetailsReturnsOkStatusAndUpdatedDetailsJSON() throws Exception {
        Details details = new Details();
        details.setFirstName("Test");
        details.setSecondName("Test");
        details.setEmail("@Test.com");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String testDetailsJSON = objectWriter.writeValueAsString(details);
        mvc.perform(
                put("/customers/details").contentType(MediaType.APPLICATION_JSON).header(authHeaderKey, testJWT)
                        .content(testDetailsJSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json"))
                .andExpect(jsonPath("$.firstName", is("Test")))
                .andExpect(jsonPath("$.secondName", is("Test")))
                .andExpect(jsonPath("$.email", is("@Test.com")));
    }


    @Test
    void givenNotValidCustomerDetailsPUTDetailsRequestReturnsBadRequestStatusAndThrowsConstraintViolationException() throws Exception {
        Details details = new Details();
        details.setFirstName("");
        details.setSecondName("");
        details.setEmail("");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String testDetailsJSON = objectWriter.writeValueAsString(details);
        mvc.perform(
                put("/customers/details").contentType(MediaType.APPLICATION_JSON).header(authHeaderKey, testJWT)
                        .content(testDetailsJSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenAuthenticatedCustomerGETAddressesReturnsOkStatusAndValidAddressJSON() throws Exception {
        mvc.perform(
                get("/customers/details/addresses").contentType(MediaType.APPLICATION_JSON).header(authHeaderKey, testJWT))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.country", is("empty")))
                .andExpect(jsonPath("$.state", is("empty")))
                .andExpect(jsonPath("$.city", is("empty")))
                .andExpect(jsonPath("$.street", is("empty")))
                .andExpect(jsonPath("$.postalCode", is("empty")));
    }

    @Test
    void givenNotAuthenticatedCustomerGETAddressesReturnsForbiddenStatus() throws Exception {
        mvc.perform(
                get("/customers/details/addresses").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void givenValidCustomerAddressPUTAddressesRequestReturnsOkStatusAndUpdatedAddressJSON() throws Exception {
        Address address = new Address();
        address.setCountry("Address");
        address.setState("Address");
        address.setCity("Address");
        address.setStreet("Address");
        address.setPostalCode("Address");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String testAddressJSON = objectWriter.writeValueAsString(address);
        mvc.perform(
                put("/customers/details/addresses").contentType(MediaType.APPLICATION_JSON).header(authHeaderKey, testJWT)
                        .content(testAddressJSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.country", is("Address")))
                .andExpect(jsonPath("$.state", is("Address")))
                .andExpect(jsonPath("$.city", is("Address")))
                .andExpect(jsonPath("$.street", is("Address")))
                .andExpect(jsonPath("$.postalCode", is("Address")));
    }

    @Test
    void givenNotValidCustomerAddressPUTAddressesRequestReturnsBadRequestStatus() throws Exception {
        Address address = new Address();
        address.setCountry("");
        address.setState("");
        address.setCity("");
        address.setStreet("");
        address.setPostalCode("");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String testDetailsJSON = objectWriter.writeValueAsString(address);
        mvc.perform(
                put("/customers/details/addresses").contentType(MediaType.APPLICATION_JSON).header(authHeaderKey, testJWT)
                        .content(testDetailsJSON))
                .andExpect(status().isBadRequest());
    }


    @AfterAll
    public void deleteTestUserFromDatabaseAfterTestWithTestJWT() throws Exception {
        mvc.perform(
                delete("/users").contentType(MediaType.APPLICATION_JSON).header(authHeaderKey, testJWT))
                .andExpect(status().isOk());
    }
}
