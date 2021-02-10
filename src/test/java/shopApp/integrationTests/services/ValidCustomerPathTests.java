package shopApp.integrationTests.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ValidCustomerPathTests {

    @Autowired
    MockMvc mvc;

    static String authHeaderValue = "Bearer ";
    final static String authHeaderName = "Authorization";

    @Test
    @Order(1)
    public void registerTestCustomerAndAuthenticate() throws Exception {
        Customer customer = new Customer();
        customer.setUsername("TestCustomer");
        customer.setPassword("TestCustomer");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String testCustomerJSON = objectWriter.writeValueAsString(customer);
        //Registration
        mvc.perform(
                post("/customers").contentType(MediaType.APPLICATION_JSON).content(testCustomerJSON))
                .andExpect(status().isOk());
        //Authentication
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername(customer.getUsername());
        request.setPassword(customer.getPassword());
        String authTest = objectMapper.writeValueAsString(request);
        MvcResult mvcResult = mvc.perform(
                post("/authentication").contentType(MediaType.APPLICATION_JSON).content(authTest))
                .andExpect(status().isOk())
                .andReturn();
        String jwt = mvcResult.getResponse().getContentAsString();
        JsonNode node = new ObjectMapper().readTree(jwt);
        authHeaderValue = authHeaderValue + node.get("jwt").textValue();
    }

    @Test
    @Order(2)
    void checkCustomerDetailsForValidCustomer() throws Exception {
        mvc.perform(
                get("/customers/details").contentType(MediaType.APPLICATION_JSON).header(authHeaderName, authHeaderValue))
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
    @Order(3)
    void updateCustomerDetailsWithValidDataReturnsNewDetails() throws Exception {
        Details details = new Details();
        Address address = new Address();
        address.setCountry("Test");
        address.setState("Test");
        address.setCity("Test");
        address.setStreet("Test");
        address.setPostalCode("Test");
        details.setFirstName("Test");
        details.setSecondName("Test");
        details.setEmail("@Test.com");
        details.setAddress(address);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String testDetailsJSON = objectWriter.writeValueAsString(details);
        mvc.perform(
                put("/customers/details").contentType(MediaType.APPLICATION_JSON).header(authHeaderName, authHeaderValue)
        .content(testDetailsJSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json"))
                .andExpect(jsonPath("$.firstName", is("Test")))
                .andExpect(jsonPath("$.secondName", is("Test")))
                .andExpect(jsonPath("$.email", is("@Test.com")))
                .andExpect(jsonPath("$.address.country", is("Test")))
                .andExpect(jsonPath("$.address.state", is("Test")))
                .andExpect(jsonPath("$.address.city", is("Test")))
                .andExpect(jsonPath("$.address.street", is("Test")))
                .andExpect(jsonPath("$.address.postalCode", is("Test")));
    }

    @Test
    @Order(4)
    void getCurrentAddress() throws Exception {
        mvc.perform(
                get("/customers/details/addresses").contentType(MediaType.APPLICATION_JSON).header(authHeaderName, authHeaderValue))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.country", is("Test")))
                .andExpect(jsonPath("$.state", is("Test")))
                .andExpect(jsonPath("$.city", is("Test")))
                .andExpect(jsonPath("$.street", is("Test")))
                .andExpect(jsonPath("$.postalCode", is("Test")));
    }

    @Test
    @Order(5)
    void updateCurrentAddressReturnsUpdated() throws Exception {
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
                put("/customers/details/addresses").contentType(MediaType.APPLICATION_JSON).header(authHeaderName, authHeaderValue)
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
    @Order(6)
    public void givenAllProductsURL_whenCallGETMethod_thenReceiveJSONOkRespond() throws Exception {
        mvc.perform(
                get("/products").contentType(MediaType.APPLICATION_JSON).header(authHeaderName, authHeaderValue))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json"));
    }

    @Test
    @Order(7)
    public void givenTenthProductId_whenCallGETMethod_thenReceiveJSONOkRespond() throws Exception {
        mvc.perform(
                get("/products/1").contentType(MediaType.APPLICATION_JSON).header(authHeaderName, authHeaderValue))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json"))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.productName", is("TestProduct")))
                .andExpect(jsonPath("$.productPrice", is(10000)))
                .andExpect(jsonPath("$._links.self.href", is("http://localhost/products/1")));
    }

    @Test
    @Order(7)
    void getCartWhenEmpty() throws Exception {
        mvc.perform(
                get("/customers/carts").contentType(MediaType.APPLICATION_JSON).header(authHeaderName, authHeaderValue))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json"))
                .andExpect(jsonPath("$.totalCost", Matchers.equalTo(0)))
                .andExpect(jsonPath("$.cartItems", Matchers.hasSize(0)))
                .andExpect(jsonPath("$._links.self.href", is("http://localhost/customers/carts")))
                .andExpect(jsonPath("$._links.OrderCart.href", is("http://localhost/customers/orders")));
    }

    @Test
    @Order(8)
    void addProductWithId1ToEmptyCartReturnOK() throws Exception {
        mvc.perform(
                put("/customers/carts/1").contentType(MediaType.APPLICATION_JSON).header(authHeaderName,authHeaderValue))
                .andExpect(status().isOk());
    }

    @Test
    @Order(9)
    void getCartWhenContainsProductWithId1() throws Exception {
        mvc.perform(
                get("/customers/carts").contentType(MediaType.APPLICATION_JSON).header(authHeaderName, authHeaderValue))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json"))
                .andExpect(jsonPath("$.totalCost", Matchers.equalTo(10000)))
                .andExpect(jsonPath("$.cartItems", Matchers.hasSize(1)))
                .andExpect(jsonPath("$._links.self.href", is("http://localhost/customers/carts")))
                .andExpect(jsonPath("$._links.OrderCart.href", is("http://localhost/customers/orders")));
    }

    @Test
    @Order(10)
    void orderCartToReceiveOrderForProceeding() throws Exception {
        mvc.perform(
                post("/customers/orders").contentType(MediaType.APPLICATION_JSON).header(authHeaderName, authHeaderValue))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json"))
                .andExpect(jsonPath("$.totalCost", Matchers.equalTo(10000)))
                .andExpect(jsonPath("$.orderItems", Matchers.hasSize(1)))
                .andExpect(jsonPath("$._links.self.href", Matchers.containsString("http://localhost/customers/orders")))
                .andExpect(jsonPath("$._links.OrderHistory.href", is("http://localhost/customers/orders")))
                .andExpect(jsonPath("$._links.Payment.href", is("http://localhost/payment/make")));
    }

    @Test
    @Order(15)
    public void deleteTestUserAfterTest() throws Exception {
        mvc.perform(
                delete("/users").contentType(MediaType.APPLICATION_JSON).header(authHeaderName, authHeaderValue))
                .andExpect(status().isOk());
    }
}
