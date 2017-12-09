import { Component, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Http, Response } from '@angular/http';

import { AuthenticationService } from './authentication.service';
import { HttpPacketService } from './httpPacket.service';
import { CryptoService } from './crypto.service';

//import { Order } from './libs';

@Component({    selector: 'drinksit-order',    templateUrl: './orders.html',    providers: [AuthenticationService, CryptoService, HttpPacketService]
})
export class OrderComponent {
    title = '';    orders = [];    constructor(private _router: Router,        private _authService: AuthenticationService,        private _httpPacketService: HttpPacketService,        private _http: Http) { }    ngOnInit() {        if (this._authService.getLoggedUser()) {            this.setTitle();            this.getOrders();        }        else {            this._router.navigateByUrl('/login');        }    }    setTitle() {        this.title = 'Order';    }    getOrders() {        let url = 'DrinksIt/orders';        let method = 'POST';        let user = JSON.parse(this._authService.getLoggedUser());        let userName = user.username;        let body = JSON.stringify({ "userName": userName });        let packetOptions = this._httpPacketService.computePacketOptions(method, user, body, url);        this._http.post(url + '?userName=' + userName, body, packetOptions)            .map(response => response.json())            .subscribe(                data => this.orders = data,                err => console.error('There was an error: ' + err.statusText),                () => console.log('the subscription is completed')                );    }    updateStatus(order) {        let url = 'DrinksIt/updateOrderStatus';        let user = JSON.parse(this._authService.getLoggedUser());        let userName = user.username;        let body = JSON.stringify({"orderId": order.order_id, "status": order.status} );        let method = 'POST';        let packetOptions = this._httpPacketService.computePacketOptions(method, user, body, 'updateOrderStatus');        this._http.post(url + '?orderId=' + order.order_id + '&status=' + order.status, body, packetOptions)            .subscribe(                data => data,                err => console.error('There was an error: ' + err.statusText),                () => console.log('the subscription is completed')                );    }
}