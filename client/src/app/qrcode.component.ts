import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ErrorManager } 			from './errorManager';
import { AuthenticationService } from './authentication.service';
import { RestService }           	from './restService';

@Component({
    selector: 'drinksit-qrcode',
    templateUrl: './qrcode.html',
    providers: [ErrorManager, AuthenticationService, RestService]
})
export class QrCodeComponent {

	errorMsg = '';
    title = '';
    qrCode = null;

    constructor(private router: Router,
    	private _errorManager: ErrorManager,
        private _authService: AuthenticationService,
        private _restService: RestService) { }

    ngOnInit() {
        if (this._authService.getLoggedUser()) {
            this.setTitle();
        }
        else {
            this.router.navigateByUrl('app/login');
        }
    }

    setTitle() {
        this.title = 'QrCode';
    }

    getQRCode(drinkId) {

		this.errorMsg = '';
        this.qrCode = null;
        
        var self = this;
        this._restService.getQRCode(drinkId, this._authService.getUserCreds())
        	.subscribe(function(response) {
			    this.qrCode = response;
			}, function(error) {
				self._errorManager.displayError(error);
			});
			
            /*.subscribe(
                data => this.qrCode = data,
                err => this._errorManager.displayError(err.message)
                );*/
    }
}