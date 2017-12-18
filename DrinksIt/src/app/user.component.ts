import { Component, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Http, Response } from '@angular/http';

import { AuthenticationService } 	from './authentication.service';
import { RestService }           	from './restService';
import { User }           			from './models/user';

@Component({
    selector: 'drinksit-user',
    templateUrl: './users.html',
    providers: [AuthenticationService, RestService]
})
export class UserComponent {

    title = '';
    users = [];

    constructor(private _router: Router,
        private _authService: AuthenticationService,
        private _restService: RestService,
        private _http: Http) { }

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

		let user = JSON.parse(this._authService.getLoggedUser());
		this._restService.getUsers(user)
			.subscribe(
            data => this.users = data, //Bind to view
            err => console.error('There was an error: ' + err.statusText));
    }
}