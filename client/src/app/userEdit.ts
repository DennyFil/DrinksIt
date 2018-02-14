import { Component } from '@angular/core';
import { DialogRef, ModalComponent } from 'angular2-modal';
import { BSModalContext } from 'angular2-modal/plugins/bootstrap';

import { ErrorManager } 			from './errorManager';
import { RestService }           	from './restService';
import { User }           			from './models/user';

export class UserEditContext extends BSModalContext {
  public user: User;
  public onSubmitCallback: Function;
}

@Component({
  selector: 'user-edit',
  templateUrl: './userEdit.html',
  providers: [ErrorManager, RestService]
}) 
export class UserEdit implements ModalComponent<UserEditContext> {
  
  	userEditContext: UserEditContext;
  	bars = [];
	    
    constructor(private errorManager: ErrorManager,
    			private restService: RestService,
    			public dialog: DialogRef<UserEditContext>) {
    			
	    this.userEditContext = dialog.context;
	}
	
	ngOnInit() {
    	this.getBars();
    }

	getBars() {
		
		this.restService.getBars()
			.subscribe(
            	data => this.bars = data, //Bind to view
                err => this.errorManager.displayError(err.message));
    }
    
	onSubmit() {
		// post form to server
		this.restService.postUser(this.userEditContext.user)
			.subscribe(
            data => {
            	this.userEditContext.onSubmitCallback();
            	this.dialog.close();
            },
            err => this.errorManager.displayError(err.message));
	}
	
	onCancel() {
		this.dialog.close();
	}
}