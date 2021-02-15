package shopApp.mvcTests.controllers;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProductControllerTests {

    @Autowired
    MockMvc mvc;

    @Test
    public void givenGetAllProductsGETRequestReturnsStatusOkAndNotNullListOfProducts() throws Exception {
        mvc.perform(
                get("/products").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.productList", Matchers.notNullValue()))
                .andExpect(content().contentTypeCompatibleWith("application/hal+json"))
                .andExpect(jsonPath("$._links.self.href", is("http://localhost/products")));
    }

    @Test
    public void givenGETproductRequestByValidProductIdReturnsProductWithIndicatedIdAndOkStatus() throws Exception {
        mvc.perform(
                get("/products/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json"))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.productName", Matchers.notNullValue()))
                .andExpect(jsonPath("$.productPrice", Matchers.greaterThan(0)))
                .andExpect(jsonPath("$._links.self.href", Matchers.containsString("http://localhost/products/")));
    }

    @Test
    public void givenGETproductRequestByInvalidProductIdReturnsErrorStatusWithException() throws Exception {
        mvc.perform(
                get("/products/0").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoSuchElementException))
                .andExpect(result -> assertEquals("No value present", result.getResolvedException().getMessage()));
    }
}
