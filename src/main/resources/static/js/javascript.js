
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

    constructor() {
    }

    static getProduct() {
        let url, displayProduct, name, price, div;

        url = "/products/1";
        displayProduct = function (product) {
            name = product.productName + '</br>';
            price = product.productPrice + '</br>';
            div = '<div class="product">' + name + price + '</br> </div>';
            $("#productsButtons").prepend(div);
        }

        Request.authenticatedRequest(Request.type(1), url, displayProduct);
    }

    static getAllProducts() {
        let url, displayProducts, div, product, name, price;
        url = "/products";

        displayProducts = function (products) {
            for (product of products._embedded.productList) {
                name = product.productName + '</br>';
                price = product.productPrice + '</br>';
                div = '<div class="product">' + name + price + '</br> </div>';
                $("#productsButtons").prepend(div);
            }
        }

        Request.authenticatedRequest(Request.type(1), url, displayProducts);
    }
}

class UserDetails {

    constructor(firstName, secondName, email) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
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






