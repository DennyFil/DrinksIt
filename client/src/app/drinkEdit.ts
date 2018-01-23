import { Component } from '@angular/core';
import { DialogRef, ModalComponent } from 'angular2-modal';
import { BSModalContext } from 'angular2-modal/plugins/bootstrap';

import { ErrorManager } 			from './errorManager';
import { AuthenticationService } 	from './authentication.service';
import { RestService }           	from './restService';
import { HttpPacketService } 		from './httpPacket.service';
import { Drink }           			from './models/drink';

export class DrinkEditContext extends BSModalContext {
  public drink: Drink;
  public onSubmitCallback: Function;
}

@Component({
  selector: 'drink-edit',
  templateUrl: './drinkEdit.html',
  providers: [ErrorManager, AuthenticationService, HttpPacketService, RestService]
}) 
export class DrinkEdit implements ModalComponent<DrinkEditContext> {
  
  	drinkEditContext: DrinkEditContext;
	    
    constructor(private _errorManager: ErrorManager,
    			private _authService: AuthenticationService,
    			private _restService: RestService,
    			public dialog: DialogRef<DrinkEditContext>) {
    			
	    this.drinkEditContext = dialog.context;
	}
	
	ngOnInit() {
    }
    
	onSubmit() {
		// post form to server
		this._restService.postDrink(this.drinkEditContext.drink, this._authService.getUserCreds())
			.subscribe(
            data => {
            	this.drinkEditContext.onSubmitCallback();
            	this.dialog.close();
            },
            err => this._errorManager.displayError(err.message));
	}
	
	onCancel() {
		this.dialog.close();
	}
}