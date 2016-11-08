"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
const core_1 = require('@angular/core');
const authentication_service_1 = require('./authentication.service');
const httpPacket_service_1 = require('./httpPacket.service');
const crypto_service_1 = require('./crypto.service');
let QrCodeComponent = class QrCodeComponent {
    constructor(router, _authService, _httpPacketService, http) {
        this.router = router;
        this._authService = _authService;
        this._httpPacketService = _httpPacketService;
        this.http = http;
        this.errorMsg = '';
        this.title = '';
        this.qrCode = null;
    }
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
        let url = 'http://drinksit:8080/DrinksItSrv/qrcode';
        let method = 'POST';
        let user = JSON.parse(this._authService.getLoggedUser());
        let userName = user.username;
        let body = JSON.stringify({ "drinkId": drinkId, "userName": userName });
        let packetOptions = this._httpPacketService.computePacketOptions(method, user, body, url);
        this.http.post(url + '?userName=' + userName + '&drinkId=' + drinkId, body, packetOptions)
            .map(response => response.json())
            .subscribe(data => this.qrCode = data, err => this.errorMsg = 'Failed to generate QR code', () => console.log('the subscription is completed'));
    }
};
QrCodeComponent = __decorate([
    core_1.Component({
        selector: 'drinksit-qrcode',
        templateUrl: './qrcode.html',
        providers: [authentication_service_1.AuthenticationService, crypto_service_1.CryptoService, httpPacket_service_1.HttpPacketService]
    })
], QrCodeComponent);
exports.QrCodeComponent = QrCodeComponent;
