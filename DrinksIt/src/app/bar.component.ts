import { Component, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Http, Response } from '@angular/http';

import { AuthenticationService } from './authentication.service';
import { HttpPacketService } from './httpPacket.service';

@Component({
    selector: 'drinksit-bar',
    templateUrl: './bars.html',
    providers: [AuthenticationService, HttpPacketService]
})
export class BarComponent {

    title = '';
    bars = [];

    constructor(private _router: Router,
        private _authService: AuthenticationService,
        private _httpPacketService: HttpPacketService,
        private _http: Http) { }

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

        let url = 'DrinksIt/bars';
        let user = JSON.parse(this._authService.getLoggedUser());
        let body = JSON.stringify({});
        let packetOptions = this._httpPacketService.computePacketOptions('GET', user);

        this._http.post(url, null, packetOptions)
            .map(response => response.json())
            .subscribe(
                data => this.bars = data,
                err => console.error('There was an error: ' + err.statusText),
                () => console.log('the subscription is completed')
                );
    }
}