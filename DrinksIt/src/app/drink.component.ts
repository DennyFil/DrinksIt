import { Component, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

import { ErrorManager } 			from './errorManager';
import { AuthenticationService } 	from './authentication.service';
import { RestService }           	from './restService';
import { Bar }           			from './models/bar';

@Component({
    selector: 'drinksit-drink',
    templateUrl: './drinks.html',
    providers: [ErrorManager, AuthenticationService, RestService]
})
export class DrinkComponent {

    title = '';
    drinks = [];
    bars = [];
    selectedBar = { id: ''};

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
        this.title = 'Drink';
    }

    getDrinks() {

		this._restService.getDrinks(this._authService.getUserCreds(), this.selectedBar.id)
			.subscribe(
            	data => this.drinks = data, //Bind to view
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
            	data => this.bars = data, //Bind to view
                err => this._errorManager.displayError(err.message));
    }
}