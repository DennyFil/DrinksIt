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

    constructor(public router: Router,
        private _cryptoService: CryptoService,
        private _httpPacketService: HttpPacketService,
        private http: Http) { }

    loginSuccessful = false;
    login(user) {

        let url = 'http://drinksit:8080/DrinksItSrv/checkCreds';

        let method = 'POST';

        let body = JSON.stringify({});

        user.passwordHash = this._cryptoService.computeHash(user.password);
        let packetOptions = this._httpPacketService.computePacketOptions(method, user, body, url);

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