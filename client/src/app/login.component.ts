import { Component }							from '@angular/core';
import { Router }								from '@angular/router';
import { ErrorManager }							from './errorManager';
import { AuthenticationService, UserCreds }		from './authentication.service';
import { RestService }           				from './restService';

@Component({
  selector: 'drinksit-login',
  templateUrl: './login.html',
  providers: [ErrorManager, AuthenticationService, RestService]
})
export class LoginComponent {

    userCreds = new UserCreds('', '');

    constructor(public router: Router,
    	private errorManager: ErrorManager,
    	private authService: AuthenticationService,
		private restService: RestService) {

  		if (this.authService.isLoggedIn()) {
  			this.router.navigateByUrl('app/orders');
  		}
  	}
	
	loginSuccessCbk(){
		this.router.navigateByUrl('app/orders');
	}
	
	loginFailureCbk(err = null){
		if (err) {
			this.errorManager.displayError(err);
		}
		else {
			this.errorManager.displayError('Failed to login');
		}
	}

	loginResponseCbk(userInfo) {

		if (userInfo.token) {
        	localStorage.setItem('drinksItUserInfo', JSON.stringify(userInfo));
        	this.loginSuccessCbk();
        } else {
        	this.loginFailureCbk();
        }
    }

    login() {

		// closure safe
		let self = this;
		this.restService.login(this.userCreds)
			.subscribe(
                data => self.loginResponseCbk(data),
                err => self.loginFailureCbk(err)
                );
    }
}
