import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from './authentication.service';
import { RestService }           	from './restService';

@Component({
    selector: 'drinksit-qrcode',
    templateUrl: './qrcode.html',
    providers: [AuthenticationService, RestService]
})
export class QrCodeComponent {

	public errorMsg = '';
    title = '';
    qrCode = null;

    constructor(private router: Router,
        private _authService: AuthenticationService,
        private _restService: RestService) { }

    ngOnInit() {
        if (this._authService.getLoggedUser()) {
            this.setTitle();
        }
        else {
            this.router.navigateByUrl('/login');
        }
    }

    setTitle() {
        this.title = 'QrCode';
    }

    getQRCode(drinkId) {

		this.errorMsg = '';
        this.qrCode = null;
        
        this._restService.getQRCode(drinkId, this._authService.getUserCreds())
            .subscribe(
                data => this.qrCode = data,
                err => this.errorMsg = 'Failed to generate QR code',
                () => console.log('the subscription is completed')
                );
    }
}