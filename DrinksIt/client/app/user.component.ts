import { Component, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Overlay, overlayConfigFactory } from 'angular2-modal';
import { Modal, BSModalContext } from 'angular2-modal/plugins/bootstrap';

import { ErrorManager } 				from './errorManager';
import { AuthenticationService } 		from './authentication.service';
import { RestService }           		from './restService';
import { UserEditContext, UserEdit }    from './userEdit';
import { User }           				from './models/user';

@Component({
    selector: 'drinksit-user',
    templateUrl: './users.html',
    providers: [ErrorManager, AuthenticationService, RestService, Modal]
})
export class UserComponent {

    title = '';
    users = [];

    constructor(private _router: Router,
    	private _errorManager: ErrorManager,
        private _authService: AuthenticationService,
        private _restService: RestService,
        public modal: Modal) { }

    ngOnInit() {
        if (this._authService.getLoggedUser()) {
            this.setTitle();
            this.getUsers();
        }
        else {
            this._router.navigateByUrl('/login');
        }
    }

    setTitle() {
        this.title = 'User';
    }

    getUsers() {

		this._restService.getUsers(this._authService.getUserCreds())
			.subscribe(
            data => this.users = data, //Bind to view
            err => this._errorManager.displayError(err.message));
    }
  
  	editUser(user) {
    	
    	// New user
    	if (user == null){
    		user = new User("", -1);
    	}

    	return this.modal.open(UserEdit,
    		overlayConfigFactory(
    		{
	    		onSubmitCallback: () => {
	               this.getUsers();
	          	},
	           	user: user
	        },
           	BSModalContext));
  	}
}