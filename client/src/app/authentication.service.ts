import { Component, Injectable }	from '@angular/core';

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
})
export class AuthenticationService {

    constructor() { }

	getUserInfo() {
		return JSON.parse(localStorage.getItem('drinksItUserInfo'));
    }
	
	isLoggedIn() {
		let userInfo = this.getUserInfo();
		return (userInfo && userInfo.token && userInfo.token.length !== 0);
	}
	
	getCredentials() {
		let userInfo = this.getUserInfo();
		if (userInfo) {			
			return {
				userName: userInfo.userName,
				token: userInfo.token
			};
		}
		
		return null;
	}

    logout() {
        localStorage.removeItem('drinksItUserInfo');
    }
}
