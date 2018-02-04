import { Component, Injectable }    from '@angular/core';
import { Router }                   from '@angular/router';
import { ErrorManager } 			      from './errorManager';
import { AuthenticationService } 	  from './authentication.service';
import { RestService }           	  from './restService';
import { Order }           			    from './models/order';

@Component({
})
export class OrderComponent {

    	private _errorManager: ErrorManager,
        private _restService: RestService) { }
		this._restService.getOrders(this._authService.getUserCreds())
			.subscribe(
            	data => this.orders = data, // Bind to view
                err => this._errorManager.displayError(err.message));
		this._restService.updateOrderStatus(order, this._authService.getUserCreds())
			.subscribe(
            	data => this.getOrders(), // Bind to view
                err => this._errorManager.displayError(err.message));
}