import { Component } from '@angular/core';
import { DialogRef, ModalComponent } from 'angular2-modal';
import { BSModalContext } from 'angular2-modal/plugins/bootstrap';

import { ErrorManager } 			from './errorManager';
import { RestService }           	from './restService';
import { Drink }           			from './models/drink';

export class DrinkEditContext extends BSModalContext {
  public drink: Drink;
  public isNew: boolean;
  public onSubmitCallback: Function;
}

@Component({
  selector: 'drink-edit',
  templateUrl: './drinkEdit.html',
  providers: [ErrorManager, RestService]
}) 
export class DrinkEdit implements ModalComponent<DrinkEditContext> {
  
  	drinkEditContext: DrinkEditContext;
	    
    constructor(private errorManager: ErrorManager,
    			private restService: RestService,
    			public dialog: DialogRef<DrinkEditContext>) {
    			
	    this.drinkEditContext = dialog.context;
	}
	
	ngOnInit() {
    }
    
	onSubmit() {
		// post form to server
		this.restService.postDrink(this.drinkEditContext.drink)
			.subscribe(
            data => {
            	this.drinkEditContext.onSubmitCallback();
            	this.dialog.close();
            },
            err => this.errorManager.displayError(err.message));
	}
	
	onCancel() {
		this.dialog.close();
	}
}