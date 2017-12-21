import { Component, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from './authentication.service';
import { RestService }           	from './restService';
import { Order }           			from './models/order';

@Component({    selector: 'drinksit-order',    templateUrl: './orders.html',    providers: [AuthenticationService, RestService]
})
export class OrderComponent {
    title = '';    orders = [];    constructor(private _router: Router,        private _authService: AuthenticationService,
        private _restService: RestService) { }    ngOnInit() {        if (this._authService.getLoggedUser()) {            this.setTitle();            this.getOrders();        }        else {            this._router.navigateByUrl('/login');        }    }    setTitle() {        this.title = 'Order';    }    getOrders() {
		this._restService.getOrders(this._authService.getUserCreds())
			.subscribe(
            	data => this.orders = data, //Bind to view
                err => console.error('There was an error: ' + err.statusText));    }    updateStatus(order) {
		this._restService.updateOrderStatus(order, this._authService.getUserCreds())
			.subscribe(
            	data => this.getOrders(), //Bind to view
                err => console.error('There was an error: ' + err.statusText));    }
}