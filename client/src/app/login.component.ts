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
    	private _errorManager: ErrorManager,
    	private _authService: AuthenticationService) {

  		if (this._authService.getLoggedUser()) {
  			this.router.navigateByUrl('app/orders');
  		}
  	}

    login() {

		// closure safe
		let self = this;
		this._authService.login(this.userCreds,
			function() {
				self.router.navigateByUrl('app/orders');
			},
			function() {
				self._errorManager.displayError('Failed to login');
			});
    }
}
