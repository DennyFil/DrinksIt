"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
const core_1 = require('@angular/core');
require('rxjs/add/operator/map');
const authentication_service_1 = require('./authentication.service');
const httpPacket_service_1 = require('./httpPacket.service');
const crypto_service_1 = require('./crypto.service');
let DrinksItComponent = class DrinksItComponent {
    constructor(router, _authService) {
        this.router = router;
        this._authService = _authService;
        this.title = 'Welcome to DrinksIt';
        if (this._authService.getLoggedUser()) {
            this.appInfo = { title: this.title };
            this.user = JSON.parse(this._authService.getLoggedUser());
        }
        else {
            this.router.navigateByUrl('/login');
        }
    }
};
DrinksItComponent = __decorate([
    core_1.Component({
        selector: 'drinksit',
        templateUrl: './drinksit.component.html',
        providers: [authentication_service_1.AuthenticationService, crypto_service_1.CryptoService, httpPacket_service_1.HttpPacketService]
    })
], DrinksItComponent);
exports.DrinksItComponent = DrinksItComponent;
