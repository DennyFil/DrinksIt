import { Component, Injectable } from '@angular/core';
import { Router } from '@angular/router';

import { AuthenticationService } from './authentication.service';
import { RestService }           	from './restService';

@Component({
    selector: 'drinksit-bar',
    templateUrl: './bars.html',
    providers: [AuthenticationService, RestService]
})
export class BarComponent {

    title = '';
    bars = [];

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
        this.title = 'Bar';
    }

    getBars() {

        let user = JSON.parse(this._authService.getLoggedUser());
		this._restService.getBars(user)
			.subscribe(
            	data => this.bars = data, //Bind to view
                err => console.error('There was an error: ' + err.statusText));
    }
}