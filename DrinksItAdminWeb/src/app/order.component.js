"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
const core_1 = require('@angular/core');
const authentication_service_1 = require('./authentication.service');
const httpPacket_service_1 = require('./httpPacket.service');
const crypto_service_1 = require('./crypto.service');
//import { Order } from './libs';
let OrderComponent = class OrderComponent {
    constructor(_router, _authService, _httpPacketService, _http) {
        this._router = _router;
        this._authService = _authService;
        this._httpPacketService = _httpPacketService;
        this._http = _http;
        this.title = '';
        this.orders = [];
    }
    ngOnInit() {
        if (this._authService.getLoggedUser()) {
            this.setTitle();
            this.getOrders();
        }
        else {
            this._router.navigateByUrl('/login');
        }
    }
    setTitle() {
        this.title = 'Order';
    }
    getOrders() {
        let url = 'http://drinksit:8080/DrinksItSrv/orders';
        let method = 'POST';
        let user = JSON.parse(this._authService.getLoggedUser());
        let userName = user.username;
        let body = JSON.stringify({ "userName": userName });
        let packetOptions = this._httpPacketService.computePacketOptions(method, user, body, url);
        this._http.post(url + '?userName=' + userName, body, packetOptions)
            .map(response => response.json())
            .subscribe(data => this.orders = data, err => console.error('There was an error: ' + err.statusText), () => console.log('the subscription is completed'));
    }
    updateStatus(order) {
        let url = 'http://drinksit:8080/DrinksItSrv/updateOrderStatus';
        let user = JSON.parse(this._authService.getLoggedUser());
        let userName = user.username;
        let body = JSON.stringify({ "orderId": order.order_id, "status": order.status });
        let method = 'POST';
        let packetOptions = this._httpPacketService.computePacketOptions(method, user, body, url);
        this._http.post(url + '?orderId=' + order.order_id + '&status=' + order.status, body, packetOptions)
            .subscribe(data => data, err => console.error('There was an error: ' + err.statusText), () => console.log('the subscription is completed'));
    }
};
OrderComponent = __decorate([
    core_1.Component({
        selector: 'drinksit-order',
        templateUrl: './orders.html',
        providers: [authentication_service_1.AuthenticationService, crypto_service_1.CryptoService, httpPacket_service_1.HttpPacketService]
    })
], OrderComponent);
exports.OrderComponent = OrderComponent;
