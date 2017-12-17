import { Component, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Http, Response } from '@angular/http';

import { AuthenticationService } from './authentication.service';
import { HttpPacketService } from './httpPacket.service';
import { CryptoService } from './crypto.service';

@Component({
    selector: 'drinksit-drink',
    templateUrl: './drinks.html',
    providers: [AuthenticationService, CryptoService, HttpPacketService]
})
export class DrinkComponent {

    title = '';
    drinks = [];
    bars = [];
    selectedBar = {};

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
        this.title = 'Drink';
    }

    getDrinks() {

        let url = 'DrinksIt/drinks';
        let user = JSON.parse(this._authService.getLoggedUser());
        let body = JSON.stringify({ "barId": this.selectedBar.id });
        let packetOptions = this._httpPacketService.computePacketOptions('POST', user);

        this._http.post(url + '?barId=' + this.selectedBar.id, body, packetOptions)
            .map(response => response.json())
            .subscribe(
                data => this.drinks = data,
                err => console.error('There was an error: ' + err.statusText),
                () => console.log('the subscription is completed')
                );
    }
    
    onBarSelected() {
    	if (this.selectedBar) {
    		this.getDrinks();
    	}
    }
    
    getBars() {

        let url = 'DrinksIt/bars';
        let user = JSON.parse(this._authService.getLoggedUser());
        let packetOptions = this._httpPacketService.computePacketOptions('GET', user);

        this._http.get(url, packetOptions)
            .map(response => response.json())
            .subscribe(
                data => this.bars = data,
                err => console.error('There was an error: ' + err.statusText),
                () => console.log('the subscription is completed')
                );
    }
}