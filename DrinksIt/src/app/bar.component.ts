import { Component, Injectable } from '@angular/core';
import { Router } from '@angular/router';

import { ErrorManager } 			from './errorManager';
import { AuthenticationService } from './authentication.service';
import { RestService }           	from './restService';

@Component({
    selector: 'drinksit-bar',
    templateUrl: './bars.html',
    providers: [ErrorManager, AuthenticationService, RestService]
})
export class BarComponent {

    title = '';
    bars = [];

    constructor(private _router: Router,
        private _errorManager: ErrorManager,
        private _authService: AuthenticationService,
        private _restService: RestService) { }

    ngOnInit() {
        if (this._authService.getLoggedUser()) {
            this.setTitle();
            this.getBars();
        }
        else {
            this._router.navigateByUrl('/login');
        }
    }

    setTitle() {
        this.title = 'Bar';
    }

    getBars() {

		this._restService.getBars(this._authService.getUserCreds())
			.subscribe(
            	data => this.bars = data, //Bind to view
                err => this._errorManager.displayError(err.message));
    }
}