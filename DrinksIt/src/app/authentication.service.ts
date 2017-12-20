import { Component, Injectable } from '@angular/core';
import { Router } from '@angular/router';
 
import { HttpPacketService } from './httpPacket.service';
import { User }           			from './models/user';

import { Http, Response } from '@angular/http';

export class UserCreds {
    constructor(
        public userName: string,
        public password: string) { }
}

@Injectable()
@Component({
    providers: [HttpPacketService]
})
export class AuthenticationService {

    constructor(public router: Router,
        private _httpPacketService: HttpPacketService,
        private http: Http) { }
        
    login(userCreds, successCbk, failureCbk) {

        let url = 'DrinksIt/login';
        let body = JSON.stringify({});
        let packetOptions = this._httpPacketService.computePacketOptions('POST', userCreds);

        this.http.post(url, body, packetOptions)
            .map(response => response.json())
            .subscribe(
                data => this.loginResponseCbk(data, successCbk, failureCbk, userCreds),
                err => failureCbk()
                );
    }    
	
	loginResponseCbk(isSuccessfull, successCbk, failureCbk, userCreds) {

		if (isSuccessfull) {
        	localStorage.setItem("userCreds", JSON.stringify(userCreds));
        	successCbk();
        }
        else {
        	failureCbk();
        }
    }
    
	getLoggedUser() {
        return JSON.parse(localStorage.getItem("userCreds"));
    }
    
    isLoggedIn() {
    	return localStorage.getItem("userCreds") !== null && localStorage.getItem("userCreds") !== undefined;
    }
    
    cleanLoggedUser() {
        localStorage.removeItem("userCreds");
    }
}