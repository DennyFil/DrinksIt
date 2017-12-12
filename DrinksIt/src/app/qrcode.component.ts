import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from './authentication.service';
import { HttpPacketService } from './httpPacket.service';
import { CryptoService } from './crypto.service';

import { Http, Response } from '@angular/http';

@Component({
    selector: 'drinksit-qrcode',
    templateUrl: './qrcode.html',
    providers: [AuthenticationService, CryptoService, HttpPacketService]
})
export class QrCodeComponent {

	public errorMsg = '';
    title = '';
    qrCode = null;

    constructor(private router: Router,
        private _authService: AuthenticationService,
        private _httpPacketService: HttpPacketService,
        private http: Http) { }

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
        let url = 'DrinksIt/qrcode';
        let user = JSON.parse(this._authService.getLoggedUser());
        let body = JSON.stringify({ "drinkId": drinkId });
        let packetOptions = this._httpPacketService.computePacketOptions('POST', user);

        this.http.post(url + '?drinkId=' + drinkId, body, packetOptions)
            .map(response => response.json())
            .subscribe(
                data => this.qrCode = data,
                err => this.errorMsg = 'Failed to generate QR code',
                () => console.log('the subscription is completed')
                );
    }
}