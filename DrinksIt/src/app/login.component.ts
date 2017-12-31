import { Component, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { ErrorManager } 					from './errorManager';
import { AuthenticationService, UserCreds } from './authentication.service';

@Component({
  selector: 'drinksit-login',
  templateUrl: './login.html',
  providers: [ErrorManager, AuthenticationService]
})
export class LoginComponent {
 
    private userCreds = new UserCreds('', '');
 
    constructor(public router: Router,
    	private _errorManager: ErrorManager,
    	private _authService:AuthenticationService) {
    	
		if(this._authService.getLoggedUser()) {
			this.router.navigateByUrl('/orders');
		}
		else {
			this.router.navigateByUrl('/login');
		}
	}
 
    login() {
		
		// closure safe
		let self = this;
		this._authService.login(this.userCreds, 
			function() {
				self.router.navigateByUrl('/orders');
			},
			function() {
				self._errorManager.displayError('Failed to login');
				self.router.navigateByUrl('/login');
			});
    }
}