var authenticationToken = localStorage.getItem("Authorization");

function registration(){
    let url = "/customers";
    let usernameID = 'username';
    let passwordID = 'password';
    let customer = {"username" : getDataFromHtml(usernameID),
        "password" : getDataFromHtml(passwordID)}
    let json = JSON.stringify(customer);
    postRequest(url, json);
}

function signIn() {
    let url = "/authentication";
    let usernameID = 'usernameSignIn';
    let passwordID = 'passwordSignIn';
    let customer = {"username" : getDataFromHtml(usernameID),
                    "password" : getDataFromHtml(passwordID)}
    let json = JSON.stringify(customer);
    postRequest(url, json, setJWT);
}

function setJWT(token){
    let jwt = JSON.parse(token);
    let authenticationToken = 'Bearer ' + jwt.jwt;
    localStorage.setItem("Authorization", authenticationToken);
}

function getProduct(){
    let url = "/products/1";
    let displayElementId = "product";
    getRequest(url, displayElementId, getResponse);
}

function getAllProducts(){
    let url = "/products";
    let displayElementId = "allProducts";
    getRequest(url, displayElementId, getResponse);
}

function getResponse(displayElementID, response){
    document.getElementById(displayElementID).innerHTML = response;
}


function confirmDetails(){
    let url = "/customers/details";
    let firstName = 'firstName';
    let lastName = 'secondName';
    let email = 'eMail';
    let customerDetails = {"firstName" : getDataFromHtml(firstName),
        "secondName" : getDataFromHtml(lastName), "email" : getDataFromHtml(email)}
    let json = JSON.stringify(customerDetails);
    console.log("Details: " + json);
    putRequest(url, json, setJWT);
}

function getDetails(){
    let url = "/customers/details";
    let displayElement = "details";
    getRequest(url, displayElement, getResponse);
}

function getRequest(url, displayElementId, callback){
    let xhr = new XMLHttpRequest();
    xhr.open("GET", url);
    xhr.setRequestHeader("Content-Type", "application/json");
    if(authenticationToken !== undefined){
        console.log("Setting jwt = Authorization " + authenticationToken);
        xhr.setRequestHeader("Authorization", authenticationToken);
    }
    xhr.send();
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4)
            if (xhr.status === 200){
                let response = xhr.responseText;
                console.log(response);
                callback(displayElementId, response);
            }
    };
}

function putRequest(url, payload, callback){
    let xhr = new XMLHttpRequest();
    xhr.open("PUT", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    if(authenticationToken !== undefined){
        console.log("Setting jwt = Authorization " + authenticationToken);
        xhr.setRequestHeader("Authorization", authenticationToken);
    }
    xhr.send(payload);
    console.log("put request send");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4)
            if (xhr.status === 202){
                let response = xhr.responseText;
                console.log("response is " + response);
                callback(response);
            }
    };
}

function postRequest(url, payload, callback, authenticationToken){
    let xhr = new XMLHttpRequest();
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(payload);
    console.log("post request send");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4)
            if (xhr.status === 202){
                let response = xhr.responseText;
                console.log("response is " + response);
                callback(response);
            }
    };
}

function getDataFromHtml(elementID){
    return document.getElementById(elementID).value;
}


