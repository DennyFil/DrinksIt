import { Component } 						from '@angular/core';
import { Router } 							from '@angular/router';
import {Observable} 						from 'rxjs/Rx';
import 'rxjs/add/operator/map';

import { AuthenticationService, UserInfo } 	from './authentication.service';
import { HttpPacketService } 				from './httpPacket.service';

@Component({
    selector: 'drinksit',
    templateUrl: './drinksit.component.html',
	providers: [AuthenticationService, HttpPacketService]
})
export class DrinksItComponent {
	
	userInfo: UserInfo;
	
	constructor(private router: Router, private authService:AuthenticationService) {		
	}
	
	ngOnInit() {
        let userInfo = this.authService.getUserInfo();
		if(userInfo) {
			this.userInfo = userInfo;
		}
		else {
			this.router.navigateByUrl('app/login');
		}
		
		this.router.events
	    .subscribe((event) => {
	    	this.userInfo = this.authService.getUserInfo();
	    });
    }
    
    logout() {
    	this.authService.logout();
        this.router.navigateByUrl('app/login');
    }
}