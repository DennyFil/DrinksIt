"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
const core_1 = require('@angular/core');
const authentication_service_1 = require('./authentication.service');
let LoginComponent = class LoginComponent {
    constructor(router, _authService) {
        this.router = router;
        this._authService = _authService;
        this.errorMsg = '';
        this.user = new authentication_service_1.DrinksItUser('', '');
        if (this._authService.getLoggedUser()) {
            this.router.navigateByUrl('/orders');
        }
        else {
            this.router.navigateByUrl('/login');
        }
    }
    login() {
        this._authService.login(this.user);
        if (!this._authService.getLoggedUser()) {
            this.errorMsg = 'Failed to login';
        }
        else {
            this.router.navigateByUrl('/orders');
        }
    }
};
LoginComponent = __decorate([
    core_1.Component({
        selector: 'drinksit-login',
        templateUrl: './login.html',
        providers: [authentication_service_1.AuthenticationService]
    })
], LoginComponent);
exports.LoginComponent = LoginComponent;
