import { Component, Injectable }            from '@angular/core';
import { Router }                           from '@angular/router';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { Overlay, overlayConfigFactory }    from 'angular2-modal';
import { Modal, BSModalContext }            from 'angular2-modal/plugins/bootstrap';

import { ErrorManager } 			              from './errorManager';
import { AuthenticationService } 	          from './authentication.service';
import { RestService }           	          from './restService';
import { Bar }           			              from './models/bar';
import { DrinkEditContext, DrinkEdit }      from './drinkEdit';
import { Drink }           			            from './models/drink';

@Component({
    selector: 'drinksit-drink',
    templateUrl: './drinks.html',
    providers: [ErrorManager, AuthenticationService, RestService, Modal]
})
export class DrinkComponent {

    title = '';
    drinks = [];
    bars = [];
    selectedBar = { id: ''};

    constructor(private _router: Router,
        private _errorManager: ErrorManager,
        private _authService: AuthenticationService,
        private _restService: RestService,
        public modal: Modal) { }

    ngOnInit() {
        if (this._authService.getLoggedUser()) {
            this.setTitle();
            this.getBars();
        } else {
            this._router.navigateByUrl('app/login');
        }
    }

    setTitle() {
        this.title = 'Drink';
    }

    getDrinks() {

		this._restService.getDrinks(this._authService.getUserCreds(), this.selectedBar.id)
			.subscribe(
            	data => this.drinks = data, // Bind to view
                err => this._errorManager.displayError(err.message));
    }

    onBarSelected() {
    	if (this.selectedBar) {
    		this.getDrinks();
    	}
    }

    getBars() {

		this._restService.getBars(this._authService.getUserCreds())
			.subscribe(
            	data => this.bars = data, // Bind to view
                err => this._errorManager.displayError(err.message));
    }

    editDrink(drink) {

    	// New drink
    	if (drink == null) {
    		drink = new Drink('', 0, 0, 0 );
    	}

		drink.barId = this.selectedBar.id;

    return this.modal.open(DrinkEdit,
    		overlayConfigFactory(
    		{
	    		onSubmitCallback: () => {
	               this.getDrinks();
	          	},
	           	drink: drink
	        },
           	BSModalContext));
  	}
}
