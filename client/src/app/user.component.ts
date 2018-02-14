import { Component, Injectable } from '@angular/core';
import { Overlay, overlayConfigFactory } from 'angular2-modal';
import { Modal, BSModalContext } from 'angular2-modal/plugins/bootstrap';

import { ErrorManager } 				from './errorManager';
import { RestService }           		from './restService';
import { UserEditContext, UserEdit }    from './userEdit';
import { User }           				from './models/user';

@Component({
    selector: 'drinksit-user',
    templateUrl: './users.html',
    providers: [ErrorManager, RestService, Modal]
})
export class UserComponent {

    title = '';
    users = [];

    constructor(
    	private errorManager: ErrorManager,
        private restService: RestService,
        public modal: Modal) { }

    ngOnInit() {
        this.setTitle();
		this.getUsers();
    }

    setTitle() {
        this.title = 'User';
    }

    getUsers() {

		this.restService.getUsers()
			.subscribe(
            data => this.users = data, //Bind to view
            err => this.errorManager.displayError(err.message));
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