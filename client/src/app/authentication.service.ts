import { Component, Injectable }	from '@angular/core';
import { Http }						from '@angular/http';

import { HttpPacketService }		from './httpPacket.service';

export class UserCreds {
    constructor(
        public userName: string,
        public token: string) { }
}

export class UserInfo {
    constructor(
        public userName: string,
        public barName: string,
        public barId: number,
        public token: string) { }
}

@Injectable()
@Component({
    providers: [HttpPacketService]
})
export class AuthenticationService {

    constructor(private httpPacketService: HttpPacketService,
        private http: Http) { }

    login(userCreds, successCbk, failureCbk) {

        let url = 'login';
        let body = JSON.stringify(userCreds);
		let packetOptions = this.httpPacketService.computePacketOptionsNoCreds();

        this.http.post(url, body, packetOptions)
            .map(response => response.json())
            .subscribe(
                data => this.loginResponseCbk(data, successCbk, failureCbk),
                err => failureCbk(err._body)
                );
    }

	loginResponseCbk(userInfo, successCbk, failureCbk) {

		if (userInfo.token) {
        	localStorage.setItem('drinksItUserInfo', JSON.stringify(userInfo));
        	if (successCbk) {
        		successCbk();
        	}
        } else {
        	if (failureCbk) {
        		failureCbk();
        	}
        }
    }

	getUserInfo() {
		return JSON.parse(localStorage.getItem('drinksItUserInfo'));
    }
	
	isLoggedIn() {
		let userInfo = this.getUserInfo();
		return (userInfo && userInfo.token && userInfo.token.length !== 0);
	}
	
	getCredentials() {
		let userInfo = this.getUserInfo();
		return {
			userName: userInfo.userName,
			token: userInfo.token
		};
	}

    logout() {
        localStorage.removeItem('drinksItUserInfo');
    }
}
