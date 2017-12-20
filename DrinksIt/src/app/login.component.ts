import { Component, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService, UserCreds } from './authentication.service';

@Component({
  selector: 'drinksit-login',
  templateUrl: './login.html',
  providers: [AuthenticationService]
})
export class LoginComponent {
 
    public errorMsg = '';
	private userCreds = new UserCreds('', '');
 
    constructor(public router: Router, private _authService:AuthenticationService) {
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
				self.errorMsg = 'Failed to login';
				self.router.navigateByUrl('/login');
			});
    }
}