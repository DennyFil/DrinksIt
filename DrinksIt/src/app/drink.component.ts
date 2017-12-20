import { Component, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

import { AuthenticationService } 	from './authentication.service';
import { RestService }           	from './restService';
import { Bar }           			from './models/bar';

@Component({
    selector: 'drinksit-drink',
    templateUrl: './drinks.html',
    providers: [AuthenticationService, RestService]
})
export class DrinkComponent {

    title = '';
    drinks = [];
    bars = [];
    selectedBar = { id: ''};

    constructor(private _router: Router,
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

		let user = JSON.parse(this._authService.getLoggedUser());
		this._restService.getDrinks(user, this.selectedBar.id)
			.subscribe(
            	data => this.drinks = data, //Bind to view
                err => console.error('There was an error: ' + err.statusText));
    }
    
    onBarSelected() {
    	if (this.selectedBar) {
    		this.getDrinks();
    	}
    }
    
    getBars() {
		
		let user = JSON.parse(this._authService.getLoggedUser());
		this._restService.getBars(user)
			.subscribe(
            	data => this.bars = data, //Bind to view
                err => console.error('There was an error: ' + err.statusText));
    }
}