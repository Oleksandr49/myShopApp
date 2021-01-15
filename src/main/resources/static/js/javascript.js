
class JSONRequest {
    constructor(type, url, callback, payload) {
        this.type = type;
        this.url = url;
        this.callback = callback;
        this.payload = payload;
        this.JWT = {value:localStorage.getItem("Authorization")};
    }

    send(){
        let callback = this.callback;
        $.ajax({
            url:this.url,
            type:this.type,
            headers: {"Authorization":this.JWT.value},
            data:this.payload,
            contentType:"application/json; charset=utf-8",
            dataType:"json",
            success: function (responseText, responseStatus){
                callback(responseText);
                console.log(responseStatus);
            }
        })
    }
}

class User{

    constructor(username, password) {
        if(username !== undefined && password!==undefined){
            this.username = username;
            this.password = password;
        }
        else {
            this.username = $('#username').val();
            this.password = $('#password').val();
        }
    }

    toString(){
        return JSON.stringify(this);
    }

    static registration(){
        let url, customer, request;

        url = "/customers";
        customer = new User();
        request = new JSONRequest("POST", url,()=>{},customer.toString());

        request.send();
    }

    static signIn() {
        let url, customer, request, authentication;

        url = "/authentication";
        customer = new User();
        authentication = function (jwt){
            localStorage.setItem("Authorization", "Bearer " + jwt.jwt);
        }

        request = new JSONRequest("POST", url,authentication,customer.toString());

        request.send();
    }
}

class UserDetails {

    constructor(userDetails) {
        if(userDetails !== undefined){
            this.firstName = userDetails.firstName;
            this.secondName = userDetails.secondName;
            this.email = userDetails.email;
            this.address = userDetails.address;
        }
        else {
            this.firstName = $('#firstName').val();
            this.secondName = $('#secondName').val();
            this.email = $('#email').val();
            this.address = new Address();
        }
    }

    toString(){
        return JSON.stringify(this);
    }

    static confirmDetails() {
        let request, url, customerDetails;

        url = "/customers/details";
        customerDetails = new UserDetails();
        request = new JSONRequest("PUT", url, ()=>{}, customerDetails.toString());

        request.send();
    }

    static getDetails() {
        let request, url, displayDetails;

        url = "/customers/details";

        displayDetails = function (details){
            details = new UserDetails(details);
            if(details.address == null){
                details.address = new Address();
            }
            details.display();
        }
        request = new JSONRequest("GET", url, displayDetails);
        request.send();
    }

    display(){
        $(".details#firstName").val(this.firstName);
        $(".details#secondName").val(this.secondName);
        $(".details#email").val(this.email);
        $(".details#country").val(this.address.country);
        $(".details#state").val(this.address.state);
        $(".details#city").val(this.address.city);
        $(".details#street").val(this.address.street);
        $(".details#postalCode").val(this.address.postalCode);
    }
}

class Address {

    constructor(address) {
        if(address !== undefined) {
            this.country = address.country;
            this.state = address.state;
            this.city = address.city;
            this.street = address.street;
            this.postalCode = address.postalCode;
        }
        else {
            this.country = $("#country").val();
            this.state = $("#state").val();
            this.city = $("#city").val();
            this.street = $("#street").val();
            this.postalCode = $("#postalCode").val();
        }
    }

    toString(){
        return JSON.stringify(this);
    }
}

class Product {

    constructor(product) {
        this.id = product.id;
        this.name = product.productName;
        this.price = product.productPrice;
        this.selfLink = product._links.self.href;
        this.addToCartLink = product._links.addToCart.href;
    }

    static getProducts() {
        let request, url, displayProducts, listItem, product;

        url = "/products";
        displayProducts = function (responseList) {
            for(listItem of responseList._embedded.productList){
                product = new Product(listItem);
                product.display();
            }
        }
        request = new JSONRequest("GET", url, displayProducts);
        console.log(JSON.stringify(request));
        request.send();
    }

    display(){

        let request, displayProduct, addButton, markup, addToCartButton, addEvent, productDiv;

        displayProduct = function(product){
            markup = "<div class='product' id="+product.id+">"+ product.name + "</br>" + product.price +"</br> </div>";
            $(".products").append(markup);

            addButton = function (product){
                addToCartButton = '<button type="button" class="addToCartButton" id='+product.id+'> Add to cart </button>';
                productDiv = ".product#" + product.id;
                $(productDiv).append(addToCartButton);

                addEvent = function (element, product){
                    $(element).on("click", null, product, function(){
                        request = new JSONRequest("PUT", product.addToCartLink, ()=>{});
                        request.send();
                    })
                }
                addEvent(productDiv, product);
            }
            addButton(product);
        }
        displayProduct(this);
    }
}

class ShoppingCart{
    constructor(cart) {
        this.totalCost = cart.totalCost;
        this.cartItems = cart.cartItems;
        this.selfLink = cart._links.self.href;
        this.orderCartLink = cart._links.OrderCart.href;
        this.emptyCartLink = cart._links.EmptyCart.href;
    }

    display(){
    let displayProducts;

    displayProducts = function (productList){

        let addTable = function (productList){
            let table, tableID, addPosition, product;
            table = "<table class='shoppingCart' id='cartTable'></table>";
            $(".shoppingCart").append(table);
            tableID = ".shoppingCart#cartTable";

            addPosition = function (table, product){
                let markup, itemId, addButtons, tablePositionId;
                itemId = product.id;
                markup = "<tr class='cartItem' id="+itemId+">" +
                    "<td> Product name: " + product.productId + "</td>" +
                    "<td> Product amount: " + product.amount +"</td>" +
                    "<td> Product cost: " + product.cost + "</td>" +
                         "</tr>";
                $(table).append(markup);
                tablePositionId = ".cartItem#" + itemId;

                addButtons = function (position, itemId){
                    let button, addEvent;
                    button =  '<button type="button" class="removeItem" id='+itemId+'> Remove </button>';
                    $(position).append(button);


                    addEvent = function (element, itemId){
                        $(element).on("click", null, itemId, function(){
                            let request, url;
                            url = "/customers/carts/" + itemId;
                            request = new JSONRequest("DELETE", url, ()=>{});
                            request.send();
                        })
                    }
                    addEvent(tablePositionId, itemId);
                }
                addButtons(tablePositionId, itemId);
            }
            for(product of productList){
                addPosition(tableID, product);
            }
        }
        addTable(productList);
    }
    displayProducts(this.cartItems);
    }

    static getCart(){
        let request, url, displayCart, cart;

        url = "/customers/carts";
        displayCart = function (response){
            cart = new ShoppingCart(response);
            cart.display();
        }
        request = new JSONRequest("GET", url, displayCart);

        request.send();
    }

    static emptyCart(){
        let url, request;

        url = "/customers/carts";
        request = new JSONRequest("DELETE", url, ()=>{});

        request.send();
    }

    static confirmCart(){
        let request, url, order, displayOrder;

        url = "/customers/carts";
        displayOrder = function (response){
            order = new Order(response);
            order.display();
        }
        request = new JSONRequest("POST", url, displayOrder);

        request.send();
    }
}

class Order{
    constructor(order) {
        this.id = order.orderId;
        this.created = order.created;
        this.totalCost = order.totalCost;
        this.state = order.orderState;
        this.items = order.orderItems;
    }

    static getOrderHistory(){
        let request, url, displayHistory, ordersList, order, customerOrder;

        url = "customers/orders";
        displayHistory = function (responseList){
            ordersList = responseList._embedded.customerOrderList;
                for(order of ordersList){
                    customerOrder = new Order(order);
                    customerOrder.display();
                }
        }
        request = new JSONRequest("GET", url, displayHistory);

        request.send();
    }

    display(){
        let addOrderDiv;

        addOrderDiv = function (order){
            let markup, divID, addButtons;

            markup = "<div class='order' id="+order.id+">"+
                "Order ID: "+order.id+"</br>"+
                "Order creation date: "+order.created +"</br>"+
                "Order total cost: "+order.totalCost + "</br>"+
                "Progress: "+order.state +"</br>"+"</div>";
            divID = "#"+order.id + ".order";

            $(".orderHistory").append(markup);

            addButtons = function (elementID, order){
                let button, addEvent;

                button = '<button type="button" class="showOrderItems" id='+order.id+'>Show Items</button>';
                $(elementID).append(button);

                addEvent = function (elementID, order){
                    $(elementID).on("click", null, order, function(){
                        let itemsTable, itemsTableID, addItems, itemsList;

                        itemsList = order.items;
                        itemsTable = "<table class='orderItems' id="+order.id+"></table>";
                        itemsTableID = "#"+ order.id + ".orderItems";
                        $(elementID).append(itemsTable);

                        addItems = function (tableId, itemsList){
                            let item;
                            for(item of itemsList){
                                let position, positionID;

                                position = "<tr class='orderItem' id="+item.id+">" +
                                    "<td> Product name: " + item.productId + "</td>" +
                                    "<td> Product amount: " + item.amount +"</td>" +
                                    "<td> Product cost: " + item.cost + "</td>" +
                                    "</tr>";
                                positionID =  "#"+item.id+".orderItem";

                                $(tableId).append(position);

                            }
                        }
                        addItems(itemsTableID, itemsList);
                    })
                }
                addEvent(elementID, order);
            }
            addButtons(divID, order);
        }
        addOrderDiv(this);
    }
}




