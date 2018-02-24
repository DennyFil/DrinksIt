import { Component }							from '@angular/core';
import { Router }								from '@angular/router';
import { ErrorManager }							from './errorManager';
import { AuthenticationService, UserCreds }		from './authentication.service';

@Component({
  selector: 'drinksit-login',
  templateUrl: './login.html',
  providers: [ErrorManager, AuthenticationService]
})
export class LoginComponent {

    userCreds = new UserCreds('', '');

    constructor(public router: Router,
    	private errorManager: ErrorManager,
    	private authService: AuthenticationService) {

  		if (this.authService.isLoggedIn()) {
  			this.router.navigateByUrl('app/orders');
  		}
  	}

    login() {

		// closure safe
		let self = this;
		this.authService.login(this.userCreds,
			function() {
				self.router.navigateByUrl('app/orders');
			},
			function(err) {
				if (err) {
					self.errorManager.displayError(err);
				}
				else {
					self.errorManager.displayError('Failed to login');
				}
			});
    }
}
