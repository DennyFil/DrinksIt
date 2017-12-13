import { Component, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Http, Response } from '@angular/http';

import { AuthenticationService } from './authentication.service';
import { HttpPacketService } from './httpPacket.service';

@Component({
    selector: 'drinksit-user',
    templateUrl: './users.html',
    providers: [AuthenticationService, HttpPacketService]
})
export class UserComponent {

    title = '';
    users = [];

    constructor(private _router: Router,
        private _authService: AuthenticationService,
        private _httpPacketService: HttpPacketService,
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

        let url = 'DrinksIt/users';
        let user = JSON.parse(this._authService.getLoggedUser());
        let packetOptions = this._httpPacketService.computePacketOptions('GET', user);

        this._http.get(url, packetOptions)
            .map(response => response.json())
            .subscribe(
                data => this.users = data,
                err => console.error('There was an error: ' + err.statusText),
                () => console.log('the subscription is completed')
                );
    }
}