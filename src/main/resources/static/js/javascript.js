
class User{

    constructor(username, password) {
        this.username = username;
        this.password = password;
    }

    static registration(){
        let url, customer, json;

        url = "/customers";
        customer = new User($('#username').val(), $('#password').val());
        json = JSON.stringify(customer);

        Request.request(Request.type(2), url, function (){}, json);
    }

    static signIn() {
        let url, customer, json, setJWT;

        url = "/authentication";
        customer = new User($('#username').val(), $('#password').val());
        json = JSON.stringify(customer);
        setJWT = function (token){
            localStorage.setItem("Authorization", 'Bearer ' + token.jwt);
        }

        Request.request(Request.type(2), url, setJWT, json);
    }
}

class Request {

    static type(type){
        switch (type){
            case 1: return "GET";
            case 2: return "POST";
            case 3: return "PUT";
            case 4: return "DELETE";
        }
    }

    static request(type, url, callback, payload){
        console.log(payload);
        $.ajax({
            url:url,
            type:type,
            data:payload,
            contentType:"application/json; charset=utf-8",
            dataType:"json",
            success: function (response){
                callback(response);
            }
        })
    }

    static authenticatedRequest(type, url, callback, payload){
        let tokenValue;

        tokenValue = localStorage.getItem('Authorization');

        $.ajax({
            url:url,
            headers:{"Authorization" : tokenValue},
            type:type,
            data:payload,
            contentType:"application/json; charset=utf-8",
            dataType:"json",
            success: function (response){
                callback(response);
            }
        })
    }
}

class Product {

    constructor(product) {
        this.productID = product.id;
        this.productName = product.productName;
        this.productPrice = product.productPrice;
        this.selfLink = product._links.self.href;
        this.addToCartLink = product._links.addToCart.href;
    }

    static getProduct() {
        let url, displayProduct;

        url = "/products/2";

        displayProduct = function (response){
            let name, price, addToCartButton, div, product;

            product = new Product(response);
            name = product.productName + '</br>';
            price = product.productPrice + '</br>';

            addToCartButton = '<button type="button" id="product"> Add to cart </button>';

            div = '<div class="item">' + name + price + addToCartButton + '</br> </div>';

            $("#productsButtons").prepend(div);

            $("#product").on("click", null, product, function(){
                console.log(product.addToCartLink);
               Request.authenticatedRequest(Request.type(3), product.addToCartLink, function (){});
                })
        }

        Request.authenticatedRequest(Request.type(1), url, displayProduct);
    }

    static getAllProducts() {
        let url, displayProducts, div, product, name,
            price, addToCartButton, item, indexedProduct, listOfProducts;
        url = "/products";
        listOfProducts = [];
        displayProducts = function (response) {
            for (item of response._embedded.productList) {
                product = new Product(item);
                listOfProducts.push(product);
                name = product.productName + '</br>';
                price = product.productPrice + '</br>';
                addToCartButton = '<button type="button" class="product" id="0"> Add to cart </button>';
                div = '<div class="item">' + name + price + addToCartButton + '</br> </div>';
                $("#productsButtons").prepend(div);
                $(".product#0").attr("id", product.productID);
                $(".product#" + product.productID).on("click", null, listOfProducts, function(){
                    indexedProduct = listOfProducts.find(x => x.productID === parseInt($(this).attr("id")));
                    console.log(indexedProduct.addToCartLink);
                    Request.authenticatedRequest(Request.type(3), indexedProduct.addToCartLink, function (){});
                })
            }
        }

        Request.authenticatedRequest(Request.type(1), url, displayProducts);
    }
}

class UserDetails {

    constructor(firstName, secondName, email, address) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.address = address;
    }

    static confirmDetails() {
        let url, customerDetails, json;

        url = "/customers/details";
        customerDetails = new UserDetails  ($('#firstName').val(),
                                            $('#secondName').val(),
                                            $('#email').val())
        json = JSON.stringify(customerDetails);
        console.log("Details: " + json);
        Request.authenticatedRequest(Request.type(3), url, function (){}, json);
    }

    static getDetails() {
        let url, displayDetails, firstName, lastName, eMail, address, div;

        url = "/customers/details";

        displayDetails = function (details){
            firstName = details.firstName + '</br>';
            lastName = details.secondName + '</br>';
            eMail = details.email + '</br>';
            address = JSON.stringify(details.address) + '</br>';
            div = '<div class="details">' + firstName + lastName + eMail + address + '</br> </div>';
            $(".detailsInput").prepend(div);
        }
        Request.authenticatedRequest(Request.type(1), url, displayDetails);
    }
}

class UserAddress{

    constructor(country, state, city, street, postalCode) {
        this.country = country;
        this.state = state;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
    }

    static confirmAddress(){
        let url, customerAddress, json;

        url = "/customers/addresses";
        customerAddress = new UserAddress($("#country").val(), $("#state").val(),
            $("#city").val(), $("#street").val(), $("#postalCode").val(),)
        json = JSON.stringify(customerAddress);
        console.log("Address: " + json);
        Request.authenticatedRequest(Request.type(3), url, function (){}, json);
    }

    static getAddress(){
        let url, displayAddress, country, state, city, street, postalCode, div;

        url = "/customers/addresses";

        displayAddress = function (address){
            country = address.country + '</br>';
            state = address.state + '</br>';
            city = address.city + '</br>';
            street = address.street + '</br>';
            postalCode = address.postalCode + '</br>';
            div = '<div class="address">' + country + state + city + street + postalCode + '</br> </div>';
            $(".addressInput").prepend(div);
        }
        Request.authenticatedRequest(Request.type(1), url, displayAddress);
    }
}






