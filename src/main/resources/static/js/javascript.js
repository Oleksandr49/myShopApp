
let authenticationToken;

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
    console.log("singIn");
    let url = "/authentication";
    let usernameID = 'usernameSignIn';
    let passwordID = 'passwordSignIn';
    let customer = {"username" : getDataFromHtml(usernameID),
                    "password" : getDataFromHtml(passwordID)}
    let json = JSON.stringify(customer);
    console.log("send customer");
    postRequest(url, json, setJWT);
    createElement();
}

function createElement(){
    let para = document.createElement("p");
    let node = document.createTextNode("This is new.");
    para.appendChild(node);

    let element = document.getElementById("result");
    element.appendChild(para);
}

function getProduct(){
    console.log("getProduct");
    let url = "/products/1"
    getRequest(url, getResponse);
}

function getResponse(response){
    let text = JSON.parse(response);
    let elem = document.createElement("p");
    let node = document.createTextNode(text.productName);
    elem.appendChild(node);

    let file = document.getElementById("result");
    file.appendChild(elem);
}

function getRequest(url, callback){
    let xhr = new XMLHttpRequest();
    xhr.open("GET", url);
    xhr.setRequestHeader("Content-Type", "application/json");
    if(authenticationToken !== undefined){
        xhr.setRequestHeader("Authorization", authenticationToken);
    }
    xhr.send();
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4)
            if (xhr.status === 200){
                let response = xhr.responseText;
                console.log(response);
                callback(response);
            }
    };
}


function postRequest(url, payload, callback, authenticationToken){
    let xhr = new XMLHttpRequest();
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(payload);
    console.log("request send");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4)
            if (xhr.status === 202){
                let response = xhr.responseText;
                console.log("response is " + response);
                callback(response);
            }
    };
}

function setJWT(token){
    let jwt = JSON.parse(token);
    authenticationToken = 'Bearer ' + jwt.jwt;
    console.log(authenticationToken);
}

function getDataFromHtml(elementID){
    return document.getElementById(elementID).value;
}


