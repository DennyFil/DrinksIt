"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
const core_1 = require('@angular/core');
const httpPacket_service_1 = require('./httpPacket.service');
const crypto_service_1 = require('./crypto.service');
class DrinksItUser {
    constructor(username, password) {
        this.username = username;
        this.password = password;
    }
}
exports.DrinksItUser = DrinksItUser;
let AuthenticationService = class AuthenticationService {
    constructor(router, _cryptoService, _httpPacketService, http) {
        this.router = router;
        this._cryptoService = _cryptoService;
        this._httpPacketService = _httpPacketService;
        this.http = http;
        this.loginSuccessful = false;
    }
    login(user) {
        let url = 'http://drinksit:8080/DrinksItSrv/checkCreds';
        let method = 'POST';
        let body = JSON.stringify({});
        user.passwordHash = this._cryptoService.computeHash(user.password);
        let packetOptions = this._httpPacketService.computePacketOptions(method, user, body, url);
        this.http.post(url, body, packetOptions)
            .map(response => response.json())
            .subscribe(data => this.loginSuccessful = data, err => console.error('There was an error: ' + err.statusText), () => this.loginCbk(user));
    }
    loginCbk(user) {
        if (this.loginSuccessful) {
            localStorage.setItem("user", JSON.stringify(user));
            this.router.navigateByUrl('/orders');
        }
        else {
            this.router.navigateByUrl('/login');
        }
    }
    getLoggedUser() {
        return localStorage.getItem("user");
    }
    logout() {
        localStorage.removeItem("user");
        this.router.navigateByUrl('/login');
    }
};
AuthenticationService = __decorate([
    core_1.Injectable(),
    core_1.Component({
        providers: [crypto_service_1.CryptoService, httpPacket_service_1.HttpPacketService]
    })
], AuthenticationService);
exports.AuthenticationService = AuthenticationService;
