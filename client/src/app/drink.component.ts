import { Component, Injectable }            from '@angular/core';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { Overlay, overlayConfigFactory }    	from 'angular2-modal';
import { Modal, BSModalContext }            	from 'angular2-modal/plugins/bootstrap';

import { ErrorManager } 			        	from './errorManager';
import { RestService }           	        	from './restService';
import { Bar }           			        	from './models/bar';
import { DrinkEditContext, DrinkEdit }      	from './drinkEdit';
import { ConfirmModalContext, ConfirmModal }	from './confirmModal';
import { Drink }           			        	from './models/drink';

@Component({
    selector: 'drinksit-drink',
    templateUrl: './drinks.html',
    providers: [ErrorManager, RestService, Modal]
})
export class DrinkComponent {

    title = '';
    drinks = [];
    bars = [];
    selectedBar = { id: ''};

    constructor(
        private errorManager: ErrorManager,
        private restService: RestService,
        public modal: Modal) { }

    ngOnInit() {
        this.setTitle();
		this.getBars();
    }

    setTitle() {
        this.title = 'Drinks';
    }

    getDrinks() {

		this.restService.getDrinks(this.selectedBar.id)
			.subscribe(
            	data => this.drinks = data, // Bind to view
                err => this.errorManager.displayError(err));
    }

    onBarSelected() {
    	if (this.selectedBar) {
    		this.getDrinks();
    	}
    }

    getBars() {

		this.restService.getBars()
			.subscribe(
            	data => {
					this.bars = data; // Bind to view
					if (this.bars.length == 1) {
						this.selectedBar = this.bars[0];
						this.getDrinks();
					}
				},
                err => this.errorManager.displayError(err));
    }
	
	deleteDrink(drink) {
		
		return this.modal.open(ConfirmModal,
    		overlayConfigFactory(
    		{
				onSubmitAction: () => {
					return this.restService.deleteElement('drinks', drink.id);
	          	},
	    		onSubmitCallback: () => {
	               this.getDrinks();
	          	}
	        },
           	BSModalContext));
	}

    editDrink(drink) {

    	// New drink
		let isNew = drink == null;
    	if (isNew) {
    		drink = new Drink(0,'', 0, 0, 0 );
    	}

		drink.barId = this.selectedBar.id;

		return this.modal.open(DrinkEdit,
    		overlayConfigFactory(
    		{
	    		onSubmitCallback: () => {
	               this.getDrinks();
	          	},
	           	drink: drink,
				isNew: isNew
	        },
           	BSModalContext));
  	}
}
