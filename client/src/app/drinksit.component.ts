import { Component } from '@angular/core';
import { Router } from '@angular/router';
import {Observable} from 'rxjs/Rx';
import 'rxjs/add/operator/map';

import { AuthenticationService } 	from './authentication.service';
import { HttpPacketService } 		from './httpPacket.service';
import { User }           			from './models/user';

@Component({
    selector: 'drinksit',
    templateUrl: './drinksit.component.html',
	providers: [AuthenticationService, HttpPacketService]
})
export class DrinksItComponent {
	
	user: User;
	
	constructor(private router: Router, private authService:AuthenticationService) {		
	}
	
	ngOnInit() {
        let loggedInUser = this.authService.getLoggedUser();
		if(loggedInUser) {
			this.user = loggedInUser;
		}
		else {
			this.router.navigateByUrl('app/login');
		}
		
		this.router.events
	    .subscribe((event) => {
	    	this.user = this.authService.getLoggedUser();
	    });
    }
    
    logout() {
    	this.authService.logout();
        this.router.navigateByUrl('app/login');
    }
}