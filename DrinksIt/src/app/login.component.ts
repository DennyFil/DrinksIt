import { Component, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService, DrinksItUser } from './authentication.service';

@Component({
  selector: 'drinksit-login',
  templateUrl: './login.html',
  providers: [AuthenticationService]
})
export class LoginComponent {
 
    public errorMsg = '';
	public user = new DrinksItUser('', '');
 
    constructor(public router: Router, private _authService:AuthenticationService) {
		if(this._authService.getLoggedUser()) {
			this.router.navigateByUrl('/orders');
		}
		else {
			this.router.navigateByUrl('/login');
		}
	}
 
    login() {
		
		this._authService.login(this.user);
		
        if( !this._authService.getLoggedUser() ){
            this.errorMsg = 'Failed to login';
        }
		else {
			this.router.navigateByUrl('/orders');
		}
    }
}