
function sendRequest(){
    var xhr = new XMLHttpRequest();
    var url = "/customers";
    var username = document.getElementById('username').value;
    var password = document.getElementById('password').value;
    var customer = {};
    customer.username = username;
    customer.password = password;
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var json = JSON.parse(xhr.response);
        }
    };
    xhr.send(JSON.stringify(customer));
}

function checkData(){
    let username = document.getElementById('username').value;
    let password = document.getElementById('password').value;
    var customer = {};
    customer.username = username;
    customer.password = password;
    alert(JSON.stringify(customer));
}