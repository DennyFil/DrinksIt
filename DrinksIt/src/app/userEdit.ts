import { Component } from '@angular/core';
import { DialogRef, ModalComponent } from 'angular2-modal';
import { BSModalContext } from 'angular2-modal/plugins/bootstrap';

import { AuthenticationService } 	from './authentication.service';
import { RestService }           	from './restService';
import { HttpPacketService } from './httpPacket.service';
import { User }           				from './models/user';

export class UserEditContext extends BSModalContext {
  public user: User;
  public onSubmitCallback: Function;
}

@Component({
  selector: 'user-edit',
  templateUrl: './userEdit.html',
  providers: [AuthenticationService, HttpPacketService, RestService]
}) 
export class UserEdit implements ModalComponent<UserEditContext> {
  
  	userEditContext: UserEditContext;
  	bars = [];
	    
    constructor(private _authService: AuthenticationService, private _restService: RestService, public dialog: DialogRef<UserEditContext>) {
	    this.userEditContext = dialog.context;
	}
	
	ngOnInit() {
    	this.getBars();
    }

	getBars() {
		
		this._restService.getBars(this._authService.getUserCreds())
			.subscribe(
            	data => this.bars = data, //Bind to view
                err => console.error('There was an error: ' + err.statusText));
    }
    
	onSubmit() {
		// post form to server
		this._restService.postUser(this.userEditContext.user, this._authService.getUserCreds())
			.subscribe(
            data => {
            	this.userEditContext.onSubmitCallback();
            	this.dialog.close();
            },
            err => console.error('Error saving user' + err.statusText));
	}
	
	onCancel() {
		this.dialog.close();
	}
}