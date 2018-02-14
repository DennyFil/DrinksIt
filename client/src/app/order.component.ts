import { Component, Injectable }    from '@angular/core';
import { ErrorManager } 			from './errorManager';
import { RestService }           	from './restService';
import { Order }           			from './models/order';

@Component({    selector: 'drinksit-order',    templateUrl: './orders.html',    providers: [ErrorManager, RestService]
})
export class OrderComponent {
    title = '';    orders = [];    constructor(
    	private errorManager: ErrorManager,
        private restService: RestService) { }    ngOnInit() {        this.setTitle();
		this.getOrders();    }    setTitle() {        this.title = 'Orders';    }    getOrders() {
		this.restService.getOrders()
			.subscribe(
            	data => this.orders = data, // Bind to view
                err => this.errorManager.displayError(err.message));    }    updateStatus(order) {
		this.restService.updateOrderStatus(order)
			.subscribe(
            	data => this.getOrders(), // Bind to view
                err => this.errorManager.displayError(err.message));    }
}
