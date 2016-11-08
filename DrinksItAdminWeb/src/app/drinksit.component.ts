import { Component } from '@angular/core';
import { Router } from '@angular/router';
import {Observable} from 'rxjs/Rx';
import 'rxjs/add/operator/map';

import { AuthenticationService, DrinksItUser } from './authentication.service';
import { HttpPacketService } from './httpPacket.service';
import { CryptoService } from './crypto.service';
import { DrinksItInfo } from './drinksitinfo';

@Component({
    selector: 'drinksit',
    templateUrl: './drinksit.component.html',
	providers: [AuthenticationService, CryptoService, HttpPacketService]
})
export class DrinksItComponent {
	
	title = 'Welcome to DrinksIt';
	appInfo: DrinksItInfo;
	user: DrinksItUser;
	
	constructor(private router: Router, private _authService:AuthenticationService) {
		if(this._authService.getLoggedUser()) {
			this.appInfo = {title: this.title};
			this.user = JSON.parse(this._authService.getLoggedUser());
		}
		else {
			this.router.navigateByUrl('/login');
		}
	}
}