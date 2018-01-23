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

	user: User;
    constructor(public router: Router,
        private _httpPacketService: HttpPacketService,
        private http: Http) { }
        
    login(userCreds, successCbk, failureCbk) {

        let url = 'login';
        let body = JSON.stringify({});
        let packetOptions = this._httpPacketService.computePacketOptions('POST', userCreds);

        this.http.post(url, body, packetOptions)
            .map(response => response.json())
            .subscribe(
                data => this.loginResponseCbk(data, successCbk, failureCbk, userCreds),
                err => failureCbk()
                );
    }    
	
	loginResponseCbk(userInfo, successCbk, failureCbk, userCreds) {

		if (userInfo) {
        	localStorage.setItem("userInfo", JSON.stringify(userInfo));
        	localStorage.setItem("userCreds", JSON.stringify(userCreds));
        	if (successCbk){
        		successCbk();
        	}
        }
        else {
        	if (failureCbk) {
        		failureCbk();
        	}
        }
    }
    
	getLoggedUser() {
        return JSON.parse(localStorage.getItem("userInfo"));
    }
    
	getUserCreds() {
        return JSON.parse(localStorage.getItem("userCreds"));
    }
    
    cleanLoggedUser() {
        localStorage.removeItem("userInfo");
        localStorage.removeItem("userCreds");
    }
}