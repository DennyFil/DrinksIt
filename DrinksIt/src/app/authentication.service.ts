import { Component, Injectable } from '@angular/core';
import { Router } from '@angular/router';
 
import { HttpPacketService } from './httpPacket.service';
import { CryptoService } from './crypto.service';

import { Http, Response } from '@angular/http';

export class DrinksItUser {
    constructor(
        public username: string,
        public password: string) { }
}

@Injectable()
@Component({
    providers: [CryptoService, HttpPacketService]
})
export class AuthenticationService {

	loginSuccessful: boolean = false;
    constructor(public router: Router,
        private _cryptoService: CryptoService,
        private _httpPacketService: HttpPacketService,
        private http: Http) { }
        
    login(user) {

        let url = 'DrinksIt/login';
        let body = JSON.stringify({});
        let packetOptions = this._httpPacketService.computePacketOptions('POST', user);

        this.http.post(url, body, packetOptions)
            .map(response => response.json())
            .subscribe(
                data => this.loginSuccessful = data,
                err => console.error('There was an error: ' + err.statusText),
                () => this.loginCbk(user)
                );
    }    
	
	loginCbk(user) {

        if (this.loginSuccessful) {

            localStorage.setItem("user", JSON.stringify(user));
            this.router.navigateByUrl('/orders');
        }
        else {
            this.router.navigateByUrl('/login');
        }
    }
    
	getLoggedUser() {
        return localStorage.getItem("user");
    }
    
    isLoggedIn() {
    	return localStorage.getItem("user") !== null && localStorage.getItem("user") !== undefined;
    }
    
    logout() {
        localStorage.removeItem("user");
        this.router.navigateByUrl('/login');
    }
}