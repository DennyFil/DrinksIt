import { Component, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Overlay, overlayConfigFactory } from 'angular2-modal';
import { Modal, BSModalContext } from 'angular2-modal/plugins/bootstrap';

import { ErrorManager } 				from './errorManager';
import { AuthenticationService } 		from './authentication.service';
import { RestService }           		from './restService';
import { BarEditContext, BarEdit }   	from './barEdit';
import { Bar }           				from './models/bar';

@Component({
    selector: 'drinksit-bar',
    templateUrl: './bars.html',
    providers: [ErrorManager, AuthenticationService, RestService, Modal]
})
export class BarComponent {

    title = '';
    bars = [];

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
            this._router.navigateByUrl('/login');
        }
    }

    setTitle() {
        this.title = 'Bar';
    }

    getBars() {

		this._restService.getBars(this._authService.getUserCreds())
			.subscribe(
            	data => this.bars = data, // Bind to view
                err => this._errorManager.displayError(err.message));
    }

    editBar(bar) {

    	// New bar
    	if (bar == null) {
    		bar = new Bar(-1, '', '', '', '');
    	}

    	return this.modal.open(BarEdit,
    		overlayConfigFactory(
    		{
	    		onSubmitCallback: () => {
	               this.getBars();
	          	},
	           	bar: bar
	        },
           	BSModalContext));
  	}
}
