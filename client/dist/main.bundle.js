webpackJsonp(["main"],{

/***/ "../../../../../$$_lazy_route_resource lazy recursive":
/***/ (function(module, exports) {

function webpackEmptyAsyncContext(req) {
	// Here Promise.resolve().then() is used instead of new Promise() to prevent
	// uncatched exception popping up in devtools
	return Promise.resolve().then(function() {
		throw new Error("Cannot find module '" + req + "'.");
	});
}
webpackEmptyAsyncContext.keys = function() { return []; };
webpackEmptyAsyncContext.resolve = webpackEmptyAsyncContext;
module.exports = webpackEmptyAsyncContext;
webpackEmptyAsyncContext.id = "../../../../../$$_lazy_route_resource lazy recursive";

/***/ }),

/***/ "../../../../../app/authentication.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "b", function() { return UserCreds; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AuthenticationService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("../../../router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__httpPacket_service__ = __webpack_require__("../../../../../app/httpPacket.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__angular_http__ = __webpack_require__("../../../http/esm5/http.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var UserCreds = (function () {
    function UserCreds(userName, password) {
        this.userName = userName;
        this.password = password;
    }
    return UserCreds;
}());

var AuthenticationService = (function () {
    function AuthenticationService(router, _httpPacketService, http) {
        this.router = router;
        this._httpPacketService = _httpPacketService;
        this.http = http;
    }
    AuthenticationService.prototype.login = function (userCreds, successCbk, failureCbk) {
        var _this = this;
        var url = 'DrinksIt/login';
        var body = JSON.stringify({});
        var packetOptions = this._httpPacketService.computePacketOptions('POST', userCreds);
        this.http.post(url, body, packetOptions)
            .map(function (response) { return response.json(); })
            .subscribe(function (data) { return _this.loginResponseCbk(data, successCbk, failureCbk, userCreds); }, function (err) { return failureCbk(); });
    };
    AuthenticationService.prototype.loginResponseCbk = function (userInfo, successCbk, failureCbk, userCreds) {
        if (userInfo) {
            localStorage.setItem("userInfo", JSON.stringify(userInfo));
            localStorage.setItem("userCreds", JSON.stringify(userCreds));
            if (successCbk) {
                successCbk();
            }
        }
        else {
            if (failureCbk) {
                failureCbk();
            }
        }
    };
    AuthenticationService.prototype.getLoggedUser = function () {
        return JSON.parse(localStorage.getItem("userInfo"));
    };
    AuthenticationService.prototype.getUserCreds = function () {
        return JSON.parse(localStorage.getItem("userCreds"));
    };
    AuthenticationService.prototype.cleanLoggedUser = function () {
        localStorage.removeItem("userInfo");
        localStorage.removeItem("userCreds");
    };
    AuthenticationService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["A" /* Injectable */])(),
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            providers: [__WEBPACK_IMPORTED_MODULE_2__httpPacket_service__["a" /* HttpPacketService */]]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* Router */],
            __WEBPACK_IMPORTED_MODULE_2__httpPacket_service__["a" /* HttpPacketService */],
            __WEBPACK_IMPORTED_MODULE_3__angular_http__["b" /* Http */]])
    ], AuthenticationService);
    return AuthenticationService;
}());



/***/ }),

/***/ "../../../../../app/bar.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return BarComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("../../../router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_angular2_modal__ = __webpack_require__("../../../../angular2-modal/bundle/angular2-modal.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_angular2_modal_plugins_bootstrap__ = __webpack_require__("../../../../angular2-modal/plugins/bootstrap/bundle/angular2-modal-bootstrap.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__errorManager__ = __webpack_require__("../../../../../app/errorManager.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__authentication_service__ = __webpack_require__("../../../../../app/authentication.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__restService__ = __webpack_require__("../../../../../app/restService.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__barEdit__ = __webpack_require__("../../../../../app/barEdit.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__models_bar__ = __webpack_require__("../../../../../app/models/bar.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};









var BarComponent = (function () {
    function BarComponent(_router, _errorManager, _authService, _restService, modal) {
        this._router = _router;
        this._errorManager = _errorManager;
        this._authService = _authService;
        this._restService = _restService;
        this.modal = modal;
        this.title = '';
        this.bars = [];
    }
    BarComponent.prototype.ngOnInit = function () {
        if (this._authService.getLoggedUser()) {
            this.setTitle();
            this.getBars();
        }
        else {
            this._router.navigateByUrl('/login');
        }
    };
    BarComponent.prototype.setTitle = function () {
        this.title = 'Bar';
    };
    BarComponent.prototype.getBars = function () {
        var _this = this;
        this._restService.getBars(this._authService.getUserCreds())
            .subscribe(function (data) { return _this.bars = data; }, //Bind to view
        function (//Bind to view
            err) { return _this._errorManager.displayError(err.message); });
    };
    BarComponent.prototype.editBar = function (bar) {
        var _this = this;
        // New bar
        if (bar == null) {
            bar = new __WEBPACK_IMPORTED_MODULE_8__models_bar__["a" /* Bar */](-1, "", "", "", "");
        }
        return this.modal.open(__WEBPACK_IMPORTED_MODULE_7__barEdit__["a" /* BarEdit */], Object(__WEBPACK_IMPORTED_MODULE_2_angular2_modal__["l" /* overlayConfigFactory */])({
            onSubmitCallback: function () {
                _this.getBars();
            },
            bar: bar
        }, __WEBPACK_IMPORTED_MODULE_3_angular2_modal_plugins_bootstrap__["a" /* BSModalContext */]));
    };
    BarComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'drinksit-bar',
            template: __webpack_require__("../../../../../app/bars.html"),
            providers: [__WEBPACK_IMPORTED_MODULE_4__errorManager__["a" /* ErrorManager */], __WEBPACK_IMPORTED_MODULE_5__authentication_service__["a" /* AuthenticationService */], __WEBPACK_IMPORTED_MODULE_6__restService__["a" /* RestService */], __WEBPACK_IMPORTED_MODULE_3_angular2_modal_plugins_bootstrap__["c" /* Modal */]]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* Router */],
            __WEBPACK_IMPORTED_MODULE_4__errorManager__["a" /* ErrorManager */],
            __WEBPACK_IMPORTED_MODULE_5__authentication_service__["a" /* AuthenticationService */],
            __WEBPACK_IMPORTED_MODULE_6__restService__["a" /* RestService */],
            __WEBPACK_IMPORTED_MODULE_3_angular2_modal_plugins_bootstrap__["c" /* Modal */]])
    ], BarComponent);
    return BarComponent;
}());



/***/ }),

/***/ "../../../../../app/barEdit.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"container-fluid\">\r\n\t<h2>Create bar</h2>\r\n\t<div class=\"row\">\r\n\t\t<div class=\"col-sm-12 alert alert-danger\"\r\n\t\t\t[hidden]=\"!this._errorManager.errorMsg\">\r\n\t\t\t{{this._errorManager.errorMsg}}</div>\r\n\t</div>\r\n\t<form #form=\"ngForm\" (ngSubmit)=\"onSubmit()\">\r\n\t\t<div class=\"row\">\r\n\t\t\t<div class=\"col-sm-4\">\r\n\t\t\t\t<label for=\"name\">Name</label>\r\n\t\t\t</div>\r\n\t\t\t<div class=\"col-sm-8\">\r\n\t\t\t\t<div class=\"form-group\">\r\n\t\t\t\t\t<input type=\"text\" class=\"form-control\" id=\"name\"\r\n\t\t\t\t\t\tplaceholder=\"Name\" name=\"barName\"\r\n\t\t\t\t\t\t[(ngModel)]=\"barEditContext.bar.name\" required>\r\n\t\t\t\t</div>\r\n\t\t\t</div>\r\n\t\t</div>\r\n\t\t<div class=\"row\">\r\n\t\t\t<div class=\"col-sm-4\">\r\n\t\t\t\t<label for=\"address\">Address</label>\r\n\t\t\t</div>\r\n\t\t\t<div class=\"col-sm-8\">\r\n\t\t\t\t<div class=\"form-group\">\r\n\t\t\t\t\t<input type=\"text\" class=\"form-control\" id=\"address\"\r\n\t\t\t\t\t\tplaceholder=\"Address\" name=\"address\"\r\n\t\t\t\t\t\t[(ngModel)]=\"barEditContext.bar.address\" required>\r\n\t\t\t\t</div>\r\n\t\t\t</div>\r\n\t\t</div>\r\n\t\t<div class=\"row\">\r\n\t\t\t<div class=\"col-sm-4\">\r\n\t\t\t\t<label for=\"city\">City</label>\r\n\t\t\t</div>\r\n\t\t\t<div class=\"col-sm-8\">\r\n\t\t\t\t<div class=\"form-group\">\r\n\t\t\t\t\t<input type=\"text\" class=\"form-control\" id=\"city\"\r\n\t\t\t\t\t\tplaceholder=\"City\" name=\"city\"\r\n\t\t\t\t\t\t[(ngModel)]=\"barEditContext.bar.city\" required>\r\n\t\t\t\t</div>\r\n\t\t\t</div>\r\n\t\t</div>\r\n\t\t<div class=\"row\">\r\n\t\t\t<div class=\"col-sm-4\">\r\n\t\t\t\t<label for=\"country\">Country</label>\r\n\t\t\t</div>\r\n\t\t\t<div class=\"col-sm-8\">\r\n\t\t\t\t<div class=\"form-group\">\r\n\t\t\t\t\t<input type=\"text\" class=\"form-control\" id=\"country\"\r\n\t\t\t\t\t\tplaceholder=\"Country\" name=\"country\"\r\n\t\t\t\t\t\t[(ngModel)]=\"barEditContext.bar.country\" required>\r\n\t\t\t\t</div>\r\n\t\t\t</div>\r\n\t\t</div>\r\n\t\t<div class=\"form-group\">\r\n\t\t\t<button type=\"submit\" [disabled]=\"!form.valid\" class=\"btn btn-default\">Submit</button>\r\n\t\t\t<button type=\"button\" class=\"btn btn-default\" (click)=\"onCancel()\">Cancel</button>\r\n\t\t</div>\r\n\t</form>\r\n</div>"

/***/ }),

/***/ "../../../../../app/barEdit.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* unused harmony export BarEditContext */
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return BarEdit; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_angular2_modal__ = __webpack_require__("../../../../angular2-modal/bundle/angular2-modal.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_angular2_modal_plugins_bootstrap__ = __webpack_require__("../../../../angular2-modal/plugins/bootstrap/bundle/angular2-modal-bootstrap.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__errorManager__ = __webpack_require__("../../../../../app/errorManager.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__authentication_service__ = __webpack_require__("../../../../../app/authentication.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__restService__ = __webpack_require__("../../../../../app/restService.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__httpPacket_service__ = __webpack_require__("../../../../../app/httpPacket.service.ts");
var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};







var BarEditContext = (function (_super) {
    __extends(BarEditContext, _super);
    function BarEditContext() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    return BarEditContext;
}(__WEBPACK_IMPORTED_MODULE_2_angular2_modal_plugins_bootstrap__["a" /* BSModalContext */]));

var BarEdit = (function () {
    function BarEdit(_errorManager, _authService, _restService, dialog) {
        this._errorManager = _errorManager;
        this._authService = _authService;
        this._restService = _restService;
        this.dialog = dialog;
        this.barEditContext = dialog.context;
    }
    BarEdit.prototype.onSubmit = function () {
        var _this = this;
        // post form to server
        this._restService.postBar(this.barEditContext.bar, this._authService.getUserCreds())
            .subscribe(function (data) {
            _this.barEditContext.onSubmitCallback();
            _this.dialog.close();
        }, function (err) { return _this._errorManager.displayError(err.message); });
    };
    BarEdit.prototype.onCancel = function () {
        this.dialog.close();
    };
    BarEdit = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'bar-edit',
            template: __webpack_require__("../../../../../app/barEdit.html"),
            providers: [__WEBPACK_IMPORTED_MODULE_3__errorManager__["a" /* ErrorManager */], __WEBPACK_IMPORTED_MODULE_4__authentication_service__["a" /* AuthenticationService */], __WEBPACK_IMPORTED_MODULE_6__httpPacket_service__["a" /* HttpPacketService */], __WEBPACK_IMPORTED_MODULE_5__restService__["a" /* RestService */]]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_3__errorManager__["a" /* ErrorManager */],
            __WEBPACK_IMPORTED_MODULE_4__authentication_service__["a" /* AuthenticationService */],
            __WEBPACK_IMPORTED_MODULE_5__restService__["a" /* RestService */],
            __WEBPACK_IMPORTED_MODULE_1_angular2_modal__["c" /* DialogRef */]])
    ], BarEdit);
    return BarEdit;
}());



/***/ }),

/***/ "../../../../../app/bars.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"container\">\r\n\t<div class=\"row\">\r\n\t\t<div class=\"col-lg-12 alert alert-danger\"\r\n\t\t\t[hidden]=\"!this._errorManager.errorMsg\">\r\n\t\t\t{{this._errorManager.errorMsg}}</div>\r\n\t</div>\r\n\t<button type=\"button\" class=\"btn btn-info\" (click)=\"editBar()\">New</button>\r\n\t\r\n\t<table class=\"table table-striped\">\r\n\t\t<thead class=\"drinksit-text\">\r\n\t\t\t<tr>\r\n\t\t\t\t<th>ID</th>\r\n\t\t\t\t<th>Name</th>\r\n\t\t\t\t<th>Address</th>\r\n\t\t\t\t<th>City</th>\r\n\t\t\t\t<th>Country</th>\r\n\t\t\t</tr>\r\n\t\t</thead>\r\n\t\t<tbody>\r\n\t\t\t<tr *ngFor=\"let bar of bars\">\r\n\t\t\t\t<td>{{bar.id}}</td>\r\n\t\t\t\t<td>{{bar.name}}</td>\r\n\t\t\t\t<td>{{bar.address}}</td>\r\n\t\t\t\t<td>{{bar.city}}</td>\r\n\t\t\t\t<td>{{bar.country}}</td>\r\n\t\t\t</tr>\r\n\t\t</tbody>\r\n\t</table>\r\n</div>"

/***/ }),

/***/ "../../../../../app/drink.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return DrinkComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("../../../router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_map__ = __webpack_require__("../../../../rxjs/_esm5/add/operator/map.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_rxjs_add_operator_catch__ = __webpack_require__("../../../../rxjs/_esm5/add/operator/catch.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_angular2_modal__ = __webpack_require__("../../../../angular2-modal/bundle/angular2-modal.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_angular2_modal_plugins_bootstrap__ = __webpack_require__("../../../../angular2-modal/plugins/bootstrap/bundle/angular2-modal-bootstrap.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__errorManager__ = __webpack_require__("../../../../../app/errorManager.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__authentication_service__ = __webpack_require__("../../../../../app/authentication.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__restService__ = __webpack_require__("../../../../../app/restService.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__drinkEdit__ = __webpack_require__("../../../../../app/drinkEdit.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__models_drink__ = __webpack_require__("../../../../../app/models/drink.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};











var DrinkComponent = (function () {
    function DrinkComponent(_router, _errorManager, _authService, _restService, modal) {
        this._router = _router;
        this._errorManager = _errorManager;
        this._authService = _authService;
        this._restService = _restService;
        this.modal = modal;
        this.title = '';
        this.drinks = [];
        this.bars = [];
        this.selectedBar = { id: '' };
    }
    DrinkComponent.prototype.ngOnInit = function () {
        if (this._authService.getLoggedUser()) {
            this.setTitle();
            this.getBars();
        }
        else {
            this._router.navigateByUrl('/login');
        }
    };
    DrinkComponent.prototype.setTitle = function () {
        this.title = 'Drink';
    };
    DrinkComponent.prototype.getDrinks = function () {
        var _this = this;
        this._restService.getDrinks(this._authService.getUserCreds(), this.selectedBar.id)
            .subscribe(function (data) { return _this.drinks = data; }, //Bind to view
        function (//Bind to view
            err) { return _this._errorManager.displayError(err.message); });
    };
    DrinkComponent.prototype.onBarSelected = function () {
        if (this.selectedBar) {
            this.getDrinks();
        }
    };
    DrinkComponent.prototype.getBars = function () {
        var _this = this;
        this._restService.getBars(this._authService.getUserCreds())
            .subscribe(function (data) { return _this.bars = data; }, //Bind to view
        function (//Bind to view
            err) { return _this._errorManager.displayError(err.message); });
    };
    DrinkComponent.prototype.editDrink = function (drink) {
        var _this = this;
        // New drink
        if (drink == null) {
            drink = new __WEBPACK_IMPORTED_MODULE_10__models_drink__["a" /* Drink */]("", 0, 0, 0);
        }
        drink.barId = this.selectedBar.id;
        return this.modal.open(__WEBPACK_IMPORTED_MODULE_9__drinkEdit__["a" /* DrinkEdit */], Object(__WEBPACK_IMPORTED_MODULE_4_angular2_modal__["l" /* overlayConfigFactory */])({
            onSubmitCallback: function () {
                _this.getDrinks();
            },
            drink: drink
        }, __WEBPACK_IMPORTED_MODULE_5_angular2_modal_plugins_bootstrap__["a" /* BSModalContext */]));
    };
    DrinkComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'drinksit-drink',
            template: __webpack_require__("../../../../../app/drinks.html"),
            providers: [__WEBPACK_IMPORTED_MODULE_6__errorManager__["a" /* ErrorManager */], __WEBPACK_IMPORTED_MODULE_7__authentication_service__["a" /* AuthenticationService */], __WEBPACK_IMPORTED_MODULE_8__restService__["a" /* RestService */], __WEBPACK_IMPORTED_MODULE_5_angular2_modal_plugins_bootstrap__["c" /* Modal */]]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* Router */],
            __WEBPACK_IMPORTED_MODULE_6__errorManager__["a" /* ErrorManager */],
            __WEBPACK_IMPORTED_MODULE_7__authentication_service__["a" /* AuthenticationService */],
            __WEBPACK_IMPORTED_MODULE_8__restService__["a" /* RestService */],
            __WEBPACK_IMPORTED_MODULE_5_angular2_modal_plugins_bootstrap__["c" /* Modal */]])
    ], DrinkComponent);
    return DrinkComponent;
}());



/***/ }),

/***/ "../../../../../app/drinkEdit.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"container-fluid\">\r\n\t<h2>Create drink</h2>\r\n\t<div class=\"row\">\r\n\t\t<div class=\"col-sm-12 alert alert-danger\"\r\n\t\t\t[hidden]=\"!this._errorManager.errorMsg\">\r\n\t\t\t{{this._errorManager.errorMsg}}</div>\r\n\t</div>\r\n\t<form #form=\"ngForm\" (ngSubmit)=\"onSubmit()\">\r\n\t\t<div class=\"row\">\r\n\t\t\t<div class=\"col-sm-4\">\r\n\t\t\t\t<label for=\"name\">Name</label>\r\n\t\t\t</div>\r\n\t\t\t<div class=\"col-sm-8\">\r\n\t\t\t\t<div class=\"form-group\">\r\n\t\t\t\t\t<input type=\"text\" class=\"form-control\" id=\"name\"\r\n\t\t\t\t\t\tplaceholder=\"Name\" name=\"name\"\r\n\t\t\t\t\t\t[(ngModel)]=\"drinkEditContext.drink.name\" required>\r\n\t\t\t\t</div>\r\n\t\t\t</div>\r\n\t\t</div>\r\n\t\t<div class=\"row\">\r\n\t\t\t<div class=\"col-sm-4\">\r\n\t\t\t\t<label for=\"price\">Price</label>\r\n\t\t\t</div>\r\n\t\t\t<div class=\"col-sm-8\">\r\n\t\t\t\t<div class=\"form-group\">\r\n\t\t\t\t\t<input type=\"number\" class=\"form-control\" id=\"price\"\r\n\t\t\t\t\t\tplaceholder=\"Price\" name=\"price\"\r\n\t\t\t\t\t\t[(ngModel)]=\"drinkEditContext.drink.price\" step=\"0.01\" required>\r\n\t\t\t\t</div>\r\n\t\t\t</div>\r\n\t\t</div>\r\n\t\t<div class=\"row\">\r\n\t\t\t<div class=\"col-sm-4\">\r\n\t\t\t\t<label for=\"price\">Size</label>\r\n\t\t\t</div>\r\n\t\t\t<div class=\"col-sm-8\">\r\n\t\t\t\t<div class=\"form-group\">\r\n\t\t\t\t\t<input type=\"number\" class=\"form-control\" id=\"size\"\r\n\t\t\t\t\t\tplaceholder=\"Size\" name=\"size\"\r\n\t\t\t\t\t\t[(ngModel)]=\"drinkEditContext.drink.size\" step=\"0.01\" required>\r\n\t\t\t\t</div>\r\n\t\t\t</div>\r\n\t\t</div>\r\n\t\t<div class=\"row\">\r\n\t\t\t<div class=\"col-sm-4\">\r\n\t\t\t\t<label for=\"bar\">Bar</label>\r\n\t\t\t</div>\r\n\t\t\t<div class=\"col-sm-8\">\r\n\t\t\t\t<div class=\"form-group\">\r\n\t\t\t\t\t{{drinkEditContext.drink.barId}}\r\n\t\t\t\t</div>\r\n\t\t\t</div>\r\n\t\t</div>\r\n\t\t<div class=\"form-group\">\r\n\t\t\t<button type=\"submit\" [disabled]=\"!form.valid\" class=\"btn btn-default\">Submit</button>\r\n\t\t\t<button type=\"button\" class=\"btn btn-default\" (click)=\"onCancel()\">Cancel</button>\r\n\t\t</div>\r\n\t</form>\r\n</div>"

/***/ }),

/***/ "../../../../../app/drinkEdit.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* unused harmony export DrinkEditContext */
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return DrinkEdit; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_angular2_modal__ = __webpack_require__("../../../../angular2-modal/bundle/angular2-modal.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_angular2_modal_plugins_bootstrap__ = __webpack_require__("../../../../angular2-modal/plugins/bootstrap/bundle/angular2-modal-bootstrap.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__errorManager__ = __webpack_require__("../../../../../app/errorManager.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__authentication_service__ = __webpack_require__("../../../../../app/authentication.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__restService__ = __webpack_require__("../../../../../app/restService.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__httpPacket_service__ = __webpack_require__("../../../../../app/httpPacket.service.ts");
var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};







var DrinkEditContext = (function (_super) {
    __extends(DrinkEditContext, _super);
    function DrinkEditContext() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    return DrinkEditContext;
}(__WEBPACK_IMPORTED_MODULE_2_angular2_modal_plugins_bootstrap__["a" /* BSModalContext */]));

var DrinkEdit = (function () {
    function DrinkEdit(_errorManager, _authService, _restService, dialog) {
        this._errorManager = _errorManager;
        this._authService = _authService;
        this._restService = _restService;
        this.dialog = dialog;
        this.drinkEditContext = dialog.context;
    }
    DrinkEdit.prototype.ngOnInit = function () {
    };
    DrinkEdit.prototype.onSubmit = function () {
        var _this = this;
        // post form to server
        this._restService.postDrink(this.drinkEditContext.drink, this._authService.getUserCreds())
            .subscribe(function (data) {
            _this.drinkEditContext.onSubmitCallback();
            _this.dialog.close();
        }, function (err) { return _this._errorManager.displayError(err.message); });
    };
    DrinkEdit.prototype.onCancel = function () {
        this.dialog.close();
    };
    DrinkEdit = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'drink-edit',
            template: __webpack_require__("../../../../../app/drinkEdit.html"),
            providers: [__WEBPACK_IMPORTED_MODULE_3__errorManager__["a" /* ErrorManager */], __WEBPACK_IMPORTED_MODULE_4__authentication_service__["a" /* AuthenticationService */], __WEBPACK_IMPORTED_MODULE_6__httpPacket_service__["a" /* HttpPacketService */], __WEBPACK_IMPORTED_MODULE_5__restService__["a" /* RestService */]]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_3__errorManager__["a" /* ErrorManager */],
            __WEBPACK_IMPORTED_MODULE_4__authentication_service__["a" /* AuthenticationService */],
            __WEBPACK_IMPORTED_MODULE_5__restService__["a" /* RestService */],
            __WEBPACK_IMPORTED_MODULE_1_angular2_modal__["c" /* DialogRef */]])
    ], DrinkEdit);
    return DrinkEdit;
}());



/***/ }),

/***/ "../../../../../app/drinks.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"container\">\r\n\t<div class=\"row\">\r\n\t\t<div class=\"col-lg-12 alert alert-danger\"\r\n\t\t\t[hidden]=\"!this._errorManager.errorMsg\">\r\n\t\t\t{{this._errorManager.errorMsg}}</div>\r\n\t</div>\r\n\t\r\n\t<!-- Bar selector -->\t\r\n\t<div class=\"row\">\r\n\t\t<h3>Select bar</h3>\r\n\t</div>\r\n\t<div class=\"row\">\r\n\t\t<div class=\"col-sm-4\">\r\n\t\t\t<select [(ngModel)]=\"selectedBar\" (ngModelChange)=\"onBarSelected()\">\r\n\t\t\t\t<option *ngFor=\"let bar of bars\" [ngValue]=\"bar\">{{bar.name}}</option>\r\n\t\t\t</select>\r\n\t\t</div>\r\n\t\t<div class=\"col-sm-8\" [hidden]=\"!selectedBar.id\">\r\n\t\t\t<button type=\"button\" class=\"btn btn-info\" (click)=\"editDrink()\">New</button>\r\n\t\t</div>\r\n\t</div>\r\n\r\n\t<!-- Drink displayer -->\r\n\t<div class=\"row\">\r\n\t\t<table class=\"table table-striped\">\r\n\t\t\t<thead class=\"drinksit-text\">\r\n\t\t\t\t<tr>\r\n\t\t\t\t\t<th>ID</th>\r\n\t\t\t\t\t<th>Name</th>\r\n\t\t\t\t\t<th>Price</th>\r\n\t\t\t\t\t<th>Size</th>\r\n\t\t\t\t</tr>\r\n\t\t\t</thead>\r\n\t\t\t<tbody>\r\n\t\t\t\t<tr *ngFor=\"let drink of drinks\">\r\n\t\t\t\t\t<td>{{drink.id}}</td>\r\n\t\t\t\t\t<td>{{drink.name}}</td>\r\n\t\t\t\t\t<td>{{drink.price}}</td>\r\n\t\t\t\t\t<td>{{drink.size}}</td>\r\n\t\t\t\t</tr>\r\n\t\t\t</tbody>\r\n\t\t</table>\r\n\t</div>\r\n</div>"

/***/ }),

/***/ "../../../../../app/drinksit.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"drinksit-text h1 container-fluid\">\r\n\tDrinksIt\r\n</div>\r\n\r\n<div *ngIf=\"this._authService.getLoggedUser()\" class=\"row top-buffer\">\r\n\t<div class=\"btn-group btn-group-justified\" role=\"group\">\r\n\t  <div class=\"btn-group\" role=\"group\">\r\n\t    <button type=\"button\" routerLink=\"/orders\" routerLinkActive=\"active\" class=\"btn btn-default\">Orders</button>\r\n\t  </div>\r\n\t  <div *ngIf=\"user.barId===0\" class=\"btn-group\" role=\"group\">\r\n\t    <button type=\"button\" routerLink=\"/bars\" routerLinkActive=\"active\" class=\"btn btn-default\">Bars</button>\r\n\t  </div>\r\n\t  <div class=\"btn-group\" role=\"group\">\r\n\t    <button type=\"button\" routerLink=\"/drinks\" routerLinkActive=\"active\" class=\"btn btn-default\">Drinks</button>\r\n\t  </div>\r\n\t  <div *ngIf=\"user.barId===0\" class=\"btn-group\" role=\"group\">\r\n\t    <button type=\"button\" routerLink=\"/users\" routerLinkActive=\"active\" class=\"btn btn-default\">Users</button>\r\n\t  </div>\r\n\t  <div class=\"btn-group\" role=\"group\">\r\n\t    <button type=\"button\" routerLink=\"/reports\" routerLinkActive=\"active\" class=\"btn btn-default\">Reports</button>\r\n\t  </div>\r\n\t  <div class=\"btn-group\" role=\"group\">\r\n\t    <button type=\"button\" routerLink=\"/qrcode\" routerLinkActive=\"active\" class=\"btn btn-default\">QR code</button>\r\n\t  </div>\r\n\t</div>\r\n</div>\r\n\r\n<div class=\"row top-buffer\">\r\n\t<router-outlet></router-outlet>\r\n</div>\r\n\r\n<div *ngIf=\"this._authService.getLoggedUser()\" class=\"pull-right\">\r\n\t<button type=\"submit\" class=\"btn btn-default navbar-btn\" (click)=\"this.logout()\">Logout</button>\r\n    <p *ngIf=\"user\" class=\"navbar-text\">{{ user.userName }}</p>\r\n</div>"

/***/ }),

/***/ "../../../../../app/drinksit.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return DrinksItComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("../../../router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_map__ = __webpack_require__("../../../../rxjs/_esm5/add/operator/map.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__authentication_service__ = __webpack_require__("../../../../../app/authentication.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__httpPacket_service__ = __webpack_require__("../../../../../app/httpPacket.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var DrinksItComponent = (function () {
    function DrinksItComponent(router, _authService) {
        this.router = router;
        this._authService = _authService;
    }
    DrinksItComponent.prototype.ngOnInit = function () {
        var _this = this;
        var loggedInUser = this._authService.getLoggedUser();
        if (loggedInUser) {
            this.user = loggedInUser;
        }
        else {
            this.router.navigateByUrl('/login');
        }
        this.router.events
            .subscribe(function (event) {
            _this.user = _this._authService.getLoggedUser();
        });
    };
    DrinksItComponent.prototype.logout = function () {
        this._authService.cleanLoggedUser();
        this.router.navigateByUrl('/login');
    };
    DrinksItComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'drinksit',
            template: __webpack_require__("../../../../../app/drinksit.component.html"),
            providers: [__WEBPACK_IMPORTED_MODULE_3__authentication_service__["a" /* AuthenticationService */], __WEBPACK_IMPORTED_MODULE_4__httpPacket_service__["a" /* HttpPacketService */]]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* Router */], __WEBPACK_IMPORTED_MODULE_3__authentication_service__["a" /* AuthenticationService */]])
    ], DrinksItComponent);
    return DrinksItComponent;
}());



/***/ }),

/***/ "../../../../../app/drinksit.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return DrinksItModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_platform_browser__ = __webpack_require__("../../../platform-browser/esm5/platform-browser.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_forms__ = __webpack_require__("../../../forms/esm5/forms.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__angular_http__ = __webpack_require__("../../../http/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_angular2_modal__ = __webpack_require__("../../../../angular2-modal/bundle/angular2-modal.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_angular2_modal_plugins_bootstrap__ = __webpack_require__("../../../../angular2-modal/plugins/bootstrap/bundle/angular2-modal-bootstrap.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__drinksit_component__ = __webpack_require__("../../../../../app/drinksit.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__errorManager__ = __webpack_require__("../../../../../app/errorManager.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__login_component__ = __webpack_require__("../../../../../app/login.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__order_component__ = __webpack_require__("../../../../../app/order.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__bar_component__ = __webpack_require__("../../../../../app/bar.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11__drink_component__ = __webpack_require__("../../../../../app/drink.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12__user_component__ = __webpack_require__("../../../../../app/user.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_13__barEdit__ = __webpack_require__("../../../../../app/barEdit.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_14__userEdit__ = __webpack_require__("../../../../../app/userEdit.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_15__drinkEdit__ = __webpack_require__("../../../../../app/drinkEdit.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_16__report_component__ = __webpack_require__("../../../../../app/report.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_17__qrcode_component__ = __webpack_require__("../../../../../app/qrcode.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_18__drinksit_routing__ = __webpack_require__("../../../../../app/drinksit.routing.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};



















var DrinksItModule = (function () {
    function DrinksItModule() {
    }
    DrinksItModule = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["I" /* NgModule */])({
            imports: [
                __WEBPACK_IMPORTED_MODULE_1__angular_platform_browser__["a" /* BrowserModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_forms__["a" /* FormsModule */],
                __WEBPACK_IMPORTED_MODULE_3__angular_http__["c" /* HttpModule */],
                __WEBPACK_IMPORTED_MODULE_3__angular_http__["d" /* JsonpModule */],
                __WEBPACK_IMPORTED_MODULE_4_angular2_modal__["e" /* ModalModule */].forRoot(),
                __WEBPACK_IMPORTED_MODULE_5_angular2_modal_plugins_bootstrap__["b" /* BootstrapModalModule */],
                __WEBPACK_IMPORTED_MODULE_18__drinksit_routing__["a" /* routing */]
            ],
            declarations: [
                __WEBPACK_IMPORTED_MODULE_6__drinksit_component__["a" /* DrinksItComponent */],
                __WEBPACK_IMPORTED_MODULE_7__errorManager__["a" /* ErrorManager */],
                __WEBPACK_IMPORTED_MODULE_8__login_component__["a" /* LoginComponent */],
                __WEBPACK_IMPORTED_MODULE_9__order_component__["a" /* OrderComponent */],
                __WEBPACK_IMPORTED_MODULE_10__bar_component__["a" /* BarComponent */],
                __WEBPACK_IMPORTED_MODULE_11__drink_component__["a" /* DrinkComponent */],
                __WEBPACK_IMPORTED_MODULE_12__user_component__["a" /* UserComponent */],
                __WEBPACK_IMPORTED_MODULE_13__barEdit__["a" /* BarEdit */],
                __WEBPACK_IMPORTED_MODULE_14__userEdit__["a" /* UserEdit */],
                __WEBPACK_IMPORTED_MODULE_15__drinkEdit__["a" /* DrinkEdit */],
                __WEBPACK_IMPORTED_MODULE_16__report_component__["a" /* ReportComponent */],
                __WEBPACK_IMPORTED_MODULE_17__qrcode_component__["a" /* QrCodeComponent */]
            ],
            //providers: [],
            bootstrap: [__WEBPACK_IMPORTED_MODULE_6__drinksit_component__["a" /* DrinksItComponent */]],
            entryComponents: [__WEBPACK_IMPORTED_MODULE_13__barEdit__["a" /* BarEdit */], __WEBPACK_IMPORTED_MODULE_14__userEdit__["a" /* UserEdit */], __WEBPACK_IMPORTED_MODULE_15__drinkEdit__["a" /* DrinkEdit */]]
        })
    ], DrinksItModule);
    return DrinksItModule;
}());



/***/ }),

/***/ "../../../../../app/drinksit.routing.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return routing; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_router__ = __webpack_require__("../../../router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__login_component__ = __webpack_require__("../../../../../app/login.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__order_component__ = __webpack_require__("../../../../../app/order.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__bar_component__ = __webpack_require__("../../../../../app/bar.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__drink_component__ = __webpack_require__("../../../../../app/drink.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__user_component__ = __webpack_require__("../../../../../app/user.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__report_component__ = __webpack_require__("../../../../../app/report.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__qrcode_component__ = __webpack_require__("../../../../../app/qrcode.component.ts");








var appRoutes = [
    {
        path: '',
        redirectTo: '/orders',
        pathMatch: 'full'
    },
    {
        path: 'login',
        component: __WEBPACK_IMPORTED_MODULE_1__login_component__["a" /* LoginComponent */]
    },
    {
        path: 'orders',
        component: __WEBPACK_IMPORTED_MODULE_2__order_component__["a" /* OrderComponent */]
    },
    {
        path: 'bars',
        component: __WEBPACK_IMPORTED_MODULE_3__bar_component__["a" /* BarComponent */]
    },
    {
        path: 'drinks',
        component: __WEBPACK_IMPORTED_MODULE_4__drink_component__["a" /* DrinkComponent */]
    },
    {
        path: 'users',
        component: __WEBPACK_IMPORTED_MODULE_5__user_component__["a" /* UserComponent */]
    },
    {
        path: 'reports',
        component: __WEBPACK_IMPORTED_MODULE_6__report_component__["a" /* ReportComponent */]
    },
    {
        path: 'qrcode',
        component: __WEBPACK_IMPORTED_MODULE_7__qrcode_component__["a" /* QrCodeComponent */]
    }
];
var routing = __WEBPACK_IMPORTED_MODULE_0__angular_router__["b" /* RouterModule */].forRoot(appRoutes);


/***/ }),

/***/ "../../../../../app/errorManager.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ErrorManager; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var ErrorManager = (function () {
    function ErrorManager() {
        this.errorMsg = '';
    }
    ErrorManager.prototype.displayError = function (error) {
        this.errorMsg = error;
    };
    ErrorManager = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["A" /* Injectable */])(),
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            providers: [],
            template: ""
        }),
        __metadata("design:paramtypes", [])
    ], ErrorManager);
    return ErrorManager;
}());



/***/ }),

/***/ "../../../../../app/httpPacket.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return HttpPacketService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_http__ = __webpack_require__("../../../http/esm5/http.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var HttpPacketService = (function () {
    function HttpPacketService() {
    }
    HttpPacketService.prototype.computeHeaders = function (contentType) {
        return new __WEBPACK_IMPORTED_MODULE_1__angular_http__["a" /* Headers */]({ 'Content-Type': contentType });
    };
    HttpPacketService.prototype.computePacketOptions = function (method, user) {
        var contentType = 'application/json';
        var contentHeaders = this.computeHeaders(contentType);
        var authHeader = user.userName + ":" + user.password;
        contentHeaders.append('Authorization', authHeader);
        return new __WEBPACK_IMPORTED_MODULE_1__angular_http__["e" /* RequestOptions */]({ headers: contentHeaders });
    };
    HttpPacketService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["A" /* Injectable */])(),
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            providers: []
        }),
        __metadata("design:paramtypes", [])
    ], HttpPacketService);
    return HttpPacketService;
}());



/***/ }),

/***/ "../../../../../app/login.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return LoginComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("../../../router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__errorManager__ = __webpack_require__("../../../../../app/errorManager.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__authentication_service__ = __webpack_require__("../../../../../app/authentication.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var LoginComponent = (function () {
    function LoginComponent(router, _errorManager, _authService) {
        this.router = router;
        this._errorManager = _errorManager;
        this._authService = _authService;
        this.userCreds = new __WEBPACK_IMPORTED_MODULE_3__authentication_service__["b" /* UserCreds */]('', '');
        if (this._authService.getLoggedUser()) {
            this.router.navigateByUrl('/orders');
        }
        else {
            this.router.navigateByUrl('/login');
        }
    }
    LoginComponent.prototype.login = function () {
        // closure safe
        var self = this;
        this._authService.login(this.userCreds, function () {
            self.router.navigateByUrl('/orders');
        }, function () {
            self._errorManager.displayError('Failed to login');
            self.router.navigateByUrl('/login');
        });
    };
    LoginComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'drinksit-login',
            template: __webpack_require__("../../../../../app/login.html"),
            providers: [__WEBPACK_IMPORTED_MODULE_2__errorManager__["a" /* ErrorManager */], __WEBPACK_IMPORTED_MODULE_3__authentication_service__["a" /* AuthenticationService */]]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* Router */],
            __WEBPACK_IMPORTED_MODULE_2__errorManager__["a" /* ErrorManager */],
            __WEBPACK_IMPORTED_MODULE_3__authentication_service__["a" /* AuthenticationService */]])
    ], LoginComponent);
    return LoginComponent;
}());



/***/ }),

/***/ "../../../../../app/login.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"container\">\r\n\t<div class=\"row\">\r\n\t\t<div class=\"col-md-6 col-md-offset-3\">\r\n\t\t\t<div class=\"panel-body\">\r\n\t\t\t\t<div class=\"row\">\r\n\t\t\t\t\t<div class=\"col-lg-12 alert alert-danger\"\r\n\t\t\t\t\t\t[hidden]=\"!this._errorManager.errorMsg\">\r\n\t\t\t\t\t\t{{this._errorManager.errorMsg}}</div>\r\n\t\t\t\t</div>\r\n\t\t\t\t<div class=\"row\">\r\n\t\t\t\t\t<div class=\"col-lg-12\">\r\n\t\t\t\t\t\t<form (ngSubmit)=\"login()\">\r\n\t\t\t\t\t\t\t<div class=\"form-group\">\r\n\t\t\t\t\t\t\t\t<input type=\"text\" name=\"username\" id=\"username\"\r\n\t\t\t\t\t\t\t\t\tclass=\"form-control\" placeholder=\"Username\"\r\n\t\t\t\t\t\t\t\t\t[(ngModel)]=\"userCreds.userName\">\r\n\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t<div class=\"form-group\">\r\n\t\t\t\t\t\t\t\t<input type=\"password\" name=\"password\" id=\"password\"\r\n\t\t\t\t\t\t\t\t\tclass=\"form-control\" placeholder=\"Password\"\r\n\t\t\t\t\t\t\t\t\t[(ngModel)]=\"userCreds.password\">\r\n\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t<div class=\"form-group\">\r\n\t\t\t\t\t\t\t\t<div class=\"row\">\r\n\t\t\t\t\t\t\t\t\t<div class=\"col-sm-6 col-sm-offset-3\">\r\n\t\t\t\t\t\t\t\t\t\t<button type=\"submit\" class=\"form-control btn btn-default\">Login</button>\r\n\t\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t</form>\r\n\t\t\t\t\t</div>\r\n\t\t\t\t</div>\r\n\t\t\t</div>\r\n\t\t</div>\r\n\t</div>\r\n</div>"

/***/ }),

/***/ "../../../../../app/models/bar.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return Bar; });
var Bar = (function () {
    function Bar(id, address, city, country, name) {
        this.id = id;
        this.address = address;
        this.city = city;
        this.country = country;
        this.name = name;
    }
    return Bar;
}());



/***/ }),

/***/ "../../../../../app/models/drink.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return Drink; });
var Drink = (function () {
    function Drink(name, price, size, barId) {
        this.name = name;
        this.price = price;
        this.size = size;
        this.barId = barId;
    }
    return Drink;
}());



/***/ }),

/***/ "../../../../../app/models/user.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return User; });
var User = (function () {
    function User(userName, barId) {
        this.userName = userName;
        this.barId = barId;
        this.barId = -1;
    }
    return User;
}());



/***/ }),

/***/ "../../../../../app/order.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return OrderComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("../../../router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__errorManager__ = __webpack_require__("../../../../../app/errorManager.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__authentication_service__ = __webpack_require__("../../../../../app/authentication.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__restService__ = __webpack_require__("../../../../../app/restService.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var OrderComponent = (function () {
    function OrderComponent(_router, _errorManager, _authService, _restService) {
        this._router = _router;
        this._errorManager = _errorManager;
        this._authService = _authService;
        this._restService = _restService;
        this.title = '';
        this.orders = [];
    }
    OrderComponent.prototype.ngOnInit = function () {
        if (this._authService.getLoggedUser()) {
            this.setTitle();
            this.getOrders();
        }
        else {
            this._router.navigateByUrl('/login');
        }
    };
    OrderComponent.prototype.setTitle = function () {
        this.title = 'Order';
    };
    OrderComponent.prototype.getOrders = function () {
        var _this = this;
        this._restService.getOrders(this._authService.getUserCreds())
            .subscribe(function (data) { return _this.orders = data; }, //Bind to view
        function (//Bind to view
            err) { return _this._errorManager.displayError(err.message); });
    };
    OrderComponent.prototype.updateStatus = function (order) {
        var _this = this;
        this._restService.updateOrderStatus(order, this._authService.getUserCreds())
            .subscribe(function (data) { return _this.getOrders(); }, //Bind to view
        function (//Bind to view
            err) { return _this._errorManager.displayError(err.message); });
    };
    OrderComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'drinksit-order',
            template: __webpack_require__("../../../../../app/orders.html"),
            providers: [__WEBPACK_IMPORTED_MODULE_2__errorManager__["a" /* ErrorManager */], __WEBPACK_IMPORTED_MODULE_3__authentication_service__["a" /* AuthenticationService */], __WEBPACK_IMPORTED_MODULE_4__restService__["a" /* RestService */]]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* Router */],
            __WEBPACK_IMPORTED_MODULE_2__errorManager__["a" /* ErrorManager */],
            __WEBPACK_IMPORTED_MODULE_3__authentication_service__["a" /* AuthenticationService */],
            __WEBPACK_IMPORTED_MODULE_4__restService__["a" /* RestService */]])
    ], OrderComponent);
    return OrderComponent;
}());



/***/ }),

/***/ "../../../../../app/orders.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"container\">\r\n\t<div class=\"row\">\r\n\t\t<div class=\"col-lg-12 alert alert-danger\"\r\n\t\t\t[hidden]=\"!this._errorManager.errorMsg\">\r\n\t\t\t{{this._errorManager.errorMsg}}</div>\r\n\t</div>\r\n\t<table class=\"table table-striped\">\r\n\t\t<thead class=\"drinksit-text\">\r\n\t\t\t<tr>\r\n\t\t\t\t<th>Creation Time</th>\r\n\t\t\t\t<th>Update Time</th>\r\n\t\t\t\t<th>Order Id</th>\r\n\t\t\t\t<th>Drink Id</th>\r\n\t\t\t\t<th>Drink Name</th>\r\n\t\t\t\t<th>Size</th>\r\n\t\t\t\t<th>Quantity</th>\r\n\t\t\t\t<th>Status</th>\r\n\t\t\t</tr>\r\n\t\t</thead>\r\n\t\t<tbody>\r\n\t\t\t<tr *ngFor=\"let order of orders\">\r\n\t\t\t\t<td>{{order.creationTS | date: 'dd/MM/yyyy'}}</td>\r\n\t\t\t\t<td>{{order.updateTS | date: 'dd/MM/yyyy'}}</td>\r\n\t\t\t\t<td>{{order.id}}</td>\r\n\t\t\t\t<td>{{order.drink.id}}</td>\r\n\t\t\t\t<td>{{order.drink.name}}</td>\r\n\t\t\t\t<td>{{order.drink.size}}</td>\r\n\t\t\t\t<td>{{order.quantity}}</td>\r\n\t\t\t\t<td>\r\n\t\t\t\t\t<button class=\"btn btn-default\" (click)=\"updateStatus(order)\">{{order.status}}</button>\r\n\t\t\t\t</td>\r\n\t\t\t</tr>\r\n\t\t</tbody>\r\n\t</table>\r\n</div>"

/***/ }),

/***/ "../../../../../app/qrcode.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return QrCodeComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("../../../router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__errorManager__ = __webpack_require__("../../../../../app/errorManager.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__authentication_service__ = __webpack_require__("../../../../../app/authentication.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__restService__ = __webpack_require__("../../../../../app/restService.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var QrCodeComponent = (function () {
    function QrCodeComponent(router, _errorManager, _authService, _restService) {
        this.router = router;
        this._errorManager = _errorManager;
        this._authService = _authService;
        this._restService = _restService;
        this.errorMsg = '';
        this.title = '';
        this.qrCode = null;
    }
    QrCodeComponent.prototype.ngOnInit = function () {
        if (this._authService.getLoggedUser()) {
            this.setTitle();
        }
        else {
            this.router.navigateByUrl('/login');
        }
    };
    QrCodeComponent.prototype.setTitle = function () {
        this.title = 'QrCode';
    };
    QrCodeComponent.prototype.getQRCode = function (drinkId) {
        this.errorMsg = '';
        this.qrCode = null;
        var self = this;
        this._restService.getQRCode(drinkId, this._authService.getUserCreds())
            .subscribe(function (response) {
            this.qrCode = response;
        }, function (error) {
            self._errorManager.displayError(error);
        });
        /*.subscribe(
            data => this.qrCode = data,
            err => this._errorManager.displayError(err.message)
            );*/
    };
    QrCodeComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'drinksit-qrcode',
            template: __webpack_require__("../../../../../app/qrcode.html"),
            providers: [__WEBPACK_IMPORTED_MODULE_2__errorManager__["a" /* ErrorManager */], __WEBPACK_IMPORTED_MODULE_3__authentication_service__["a" /* AuthenticationService */], __WEBPACK_IMPORTED_MODULE_4__restService__["a" /* RestService */]]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* Router */],
            __WEBPACK_IMPORTED_MODULE_2__errorManager__["a" /* ErrorManager */],
            __WEBPACK_IMPORTED_MODULE_3__authentication_service__["a" /* AuthenticationService */],
            __WEBPACK_IMPORTED_MODULE_4__restService__["a" /* RestService */]])
    ], QrCodeComponent);
    return QrCodeComponent;
}());



/***/ }),

/***/ "../../../../../app/qrcode.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"container\">\r\n\t<div class=\"row\">\r\n\t\t<div class=\"col-lg-12 alert alert-danger\"\r\n\t\t\t[hidden]=\"!this._errorManager.errorMsg\">\r\n\t\t\t{{this._errorManager.errorMsg}}</div>\r\n\t</div>\r\n\t<div class=\"row\">\r\n\t\t<div class=\"col-md-6 col-md-offset-3\">\r\n\t\t\t<div class=\"panel\">\r\n\t\t\t\t<div class=\"panel-body\">\r\n\t\t\t\t\t<div class=\"row\">\r\n\t\t\t\t\t\t<div class=\"col-lg-12\">\r\n\t\t\t\t\t\t\t<form role=\"form\" (ngSubmit)=\"getQRCode(drinkId)\">\r\n\t\t\t\t\t\t\t\t<div class=\"form-group\">\r\n\t\t\t\t\t\t\t\t\t<label for=\"drinkId\" class=\"control-label\">Drink ID:</label> <input\r\n\t\t\t\t\t\t\t\t\t\ttype=\"text\" class=\"form-control\" id=\"drinkId\" name=\"drinkId\"\r\n\t\t\t\t\t\t\t\t\t\t[(ngModel)]=\"drinkId\" />\r\n\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t\t<div class=\"form-group\">\r\n\t\t\t\t\t\t\t\t\t<div class=\"row\">\r\n\t\t\t\t\t\t\t\t\t\t<div class=\"col-sm-6 col-sm-offset-3\">\r\n\t\t\t\t\t\t\t\t\t\t\t<button type=\"submit\" class=\"form-control btn btn-default\">Generate\r\n\t\t\t\t\t\t\t\t\t\t\t\tQR Code</button>\r\n\t\t\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t</form>\r\n\t\t\t\t\t\t</div>\r\n\t\t\t\t\t</div>\r\n\t\t\t\t\t<div class=\"row\">\r\n\t\t\t\t\t\t<div class=\"col-lg-12\">\r\n\t\t\t\t\t\t\t<div *ngIf=\"qrCode\">\r\n\t\t\t\t\t\t\t\t<div *ngIf=\"qrCode.image\">\r\n\t\t\t\t\t\t\t\t\t<img src=\"data:image/png;base64,{{qrCode.image}}\" />\r\n\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t\t<div *ngIf=\"qrCode.content\">Content: {{qrCode.content}}</div>\r\n\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t</div>\r\n\t\t\t\t\t</div>\r\n\t\t\t\t</div>\r\n\t\t\t</div>\r\n\t\t</div>\r\n\t</div>\r\n</div>\r\n\r\n"

/***/ }),

/***/ "../../../../../app/report.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ReportComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("../../../router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__errorManager__ = __webpack_require__("../../../../../app/errorManager.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__authentication_service__ = __webpack_require__("../../../../../app/authentication.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__restService__ = __webpack_require__("../../../../../app/restService.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var ReportComponent = (function () {
    function ReportComponent(router, _errorManager, _authService, _restService) {
        this.router = router;
        this._errorManager = _errorManager;
        this._authService = _authService;
        this._restService = _restService;
        this.errorMsg = '';
        this.title = '';
    }
    ReportComponent.prototype.ngOnInit = function () {
        if (this._authService.getLoggedUser()) {
            this.setTitle();
        }
        else {
            this.router.navigateByUrl('/login');
        }
    };
    ReportComponent.prototype.setTitle = function () {
        this.title = 'Order Report';
    };
    ReportComponent.prototype.getReportData = function (dateFrom, dateTo) {
        this.errorMsg = '';
        var self = this;
        this._restService.getReport(dateFrom, dateTo, this._authService.getUserCreds(), function (error) { self._errorManager.displayError(error); });
    };
    ReportComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'drinksit-report',
            template: __webpack_require__("../../../../../app/reports.html"),
            providers: [__WEBPACK_IMPORTED_MODULE_2__errorManager__["a" /* ErrorManager */], __WEBPACK_IMPORTED_MODULE_3__authentication_service__["a" /* AuthenticationService */], __WEBPACK_IMPORTED_MODULE_4__restService__["a" /* RestService */]]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* Router */],
            __WEBPACK_IMPORTED_MODULE_2__errorManager__["a" /* ErrorManager */],
            __WEBPACK_IMPORTED_MODULE_3__authentication_service__["a" /* AuthenticationService */],
            __WEBPACK_IMPORTED_MODULE_4__restService__["a" /* RestService */]])
    ], ReportComponent);
    return ReportComponent;
}());



/***/ }),

/***/ "../../../../../app/reports.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"container\">\r\n\t<div class=\"row\">\r\n\t\t<div class=\"col-lg-12 alert alert-danger\"\r\n\t\t\t[hidden]=\"!this._errorManager.errorMsg\">\r\n\t\t\t{{this._errorManager.errorMsg}}</div>\r\n\t</div>\r\n   \t<div class=\"row\">\r\n\t\t<div class=\"col-md-6 col-md-offset-3\">\r\n\t\t\t<div class=\"panel\">\r\n\t\t\t\t<div class=\"panel-body\">\r\n\t\t\t\t\t<div class=\"row\">\r\n\t\t\t\t\t\t<div class=\"col-lg-12\">\r\n\t\t\t\t\t\t\t<form role=\"form\" (ngSubmit)=\"getReportData(dateFrom, dateTo)\">\r\n\t\t\t\t\t\t\t\t<div class=\"form-group\">\r\n\t\t\t\t\t\t\t\t\t<label for=\"dateFrom\" class=\"control-label\">Date from:</label>\r\n\t\t\t\t\t\t\t\t\t<input type=\"date\" class=\"form-control\" id=\"dateFrom\" name=\"dateFrom\" [(ngModel)]=\"dateFrom\"/>\r\n\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t\t<div class=\"form-group\">\r\n\t\t\t\t\t\t\t\t\t<label for=\"dateTo\" class=\"control-label\">Date to:</label>\r\n\t\t\t\t\t\t\t\t\t<input type=\"date\" class=\"form-control\" id=\"dateTo\" name=\"dateTo\" [(ngModel)]=\"dateTo\"/>\r\n\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t\t<div class=\"form-group\">\r\n\t\t\t\t\t\t\t\t\t<div class=\"row\">\r\n\t\t\t\t\t\t\t\t\t\t<div class=\"col-sm-6 col-sm-offset-3\">\r\n\t\t\t\t\t\t\t\t\t\t\t<button type=\"submit\" class=\"form-control btn btn-default\">Generate report</button>\r\n\t\t\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t</form>\r\n\t\t\t\t\t\t</div>\r\n\t\t\t\t\t</div>\r\n\t\t\t\t</div>\r\n\t\t\t</div>\r\n\t\t</div>\r\n\t</div>\r\n</div>\r\n"

/***/ }),

/***/ "../../../../../app/restService.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return RestService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_http__ = __webpack_require__("../../../http/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs__ = __webpack_require__("../../../../rxjs/Rx.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2_rxjs__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_file_saver__ = __webpack_require__("../../../../file-saver/FileSaver.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_file_saver___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_3_file_saver__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__httpPacket_service__ = __webpack_require__("../../../../../app/httpPacket.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var RestService = (function () {
    function RestService(_httpPacketService, http) {
        this._httpPacketService = _httpPacketService;
        this.http = http;
    }
    RestService.prototype.getBars = function (user) {
        var packetOptions = this._httpPacketService.computePacketOptions('GET', user);
        return this.http.get('DrinksIt/bars', packetOptions)
            .map(function (res) { return res.json(); })
            .catch(function (error) { return __WEBPACK_IMPORTED_MODULE_2_rxjs__["Observable"].throw(error.json().error || 'Server error'); });
    };
    RestService.prototype.postBar = function (bar, userCreds) {
        var packetOptions = this._httpPacketService.computePacketOptions('POST', userCreds);
        var body = JSON.stringify(bar);
        return this.http.post('DrinksIt/postBar', body, packetOptions)
            .map(function (res) { return res.json(); })
            .catch(function (error) { return __WEBPACK_IMPORTED_MODULE_2_rxjs__["Observable"].throw(error.json().error || 'Server error'); });
    };
    RestService.prototype.getDrinks = function (user, barId) {
        var packetOptions = this._httpPacketService.computePacketOptions('GET', user);
        return this.http.get('DrinksIt/drinks' + '?barId=' + barId, packetOptions)
            .map(function (res) { return res.json(); })
            .catch(function (error) { return __WEBPACK_IMPORTED_MODULE_2_rxjs__["Observable"].throw(error.json().error || 'Server error'); });
    };
    RestService.prototype.postDrink = function (drink, userCreds) {
        var packetOptions = this._httpPacketService.computePacketOptions('POST', userCreds);
        var body = JSON.stringify(drink);
        return this.http.post('DrinksIt/postDrink', body, packetOptions)
            .map(function (res) { return res.json(); })
            .catch(function (error) { return __WEBPACK_IMPORTED_MODULE_2_rxjs__["Observable"].throw(error.json().error || 'Server error'); });
    };
    RestService.prototype.getOrders = function (user) {
        var packetOptions = this._httpPacketService.computePacketOptions('POST', user);
        return this.http.get('DrinksIt/orders', packetOptions)
            .map(function (res) { return res.json(); })
            .catch(function (error) { return __WEBPACK_IMPORTED_MODULE_2_rxjs__["Observable"].throw(error.json().error || 'Server error'); });
    };
    RestService.prototype.updateOrderStatus = function (order, user) {
        var packetOptions = this._httpPacketService.computePacketOptions('POST', user);
        var body = JSON.stringify({ "orderId": order.id, "status": order.status });
        return this.http.post('DrinksIt/updateOrderStatus' + '?orderId=' + order.id + '&status=' + order.status, body, packetOptions)
            .map(function (res) { return res.json(); })
            .catch(function (error) { return __WEBPACK_IMPORTED_MODULE_2_rxjs__["Observable"].throw(error.json().error || 'Server error'); });
    };
    RestService.prototype.getUsers = function (loggedInUser) {
        var packetOptions = this._httpPacketService.computePacketOptions('GET', loggedInUser);
        return this.http.get('DrinksIt/users', packetOptions)
            .map(function (res) { return res.json(); })
            .catch(function (error) { return __WEBPACK_IMPORTED_MODULE_2_rxjs__["Observable"].throw(error.json().error || 'Server error'); });
    };
    RestService.prototype.postUser = function (user, userCreds) {
        var packetOptions = this._httpPacketService.computePacketOptions('POST', userCreds);
        var body = JSON.stringify(user);
        return this.http.post('DrinksIt/postUser', body, packetOptions)
            .map(function (res) { return res.json(); })
            .catch(function (error) { return __WEBPACK_IMPORTED_MODULE_2_rxjs__["Observable"].throw(error.json().error || 'Server error'); });
    };
    RestService.prototype.getReport = function (dateFrom, dateTo, user, onErrorCbk) {
        var body = JSON.stringify({ "endDate": dateTo, "startDate": dateFrom });
        var packetOptions = this._httpPacketService.computePacketOptions('POST', user);
        packetOptions.responseType = __WEBPACK_IMPORTED_MODULE_1__angular_http__["f" /* ResponseContentType */].ArrayBuffer;
        this.http.post('DrinksIt/ordersReport?startDate=' + dateFrom + '&endDate=' + dateTo, body, packetOptions)
            .subscribe(function (response) {
            var file = new Blob([response.blob()], { type: 'application/pdf' });
            var fileURL = URL.createObjectURL(file);
            __WEBPACK_IMPORTED_MODULE_3_file_saver__["saveAs"](file, response.headers.get('filename'));
        }, function (error) {
            if (onErrorCbk) {
                onErrorCbk(error);
            }
        });
    };
    RestService.prototype.getQRCode = function (drinkId, user) {
        var body = JSON.stringify({ "drinkId": drinkId });
        var packetOptions = this._httpPacketService.computePacketOptions('POST', user);
        return this.http.post('DrinksIt/qrcode?drinkId=' + drinkId, body, packetOptions)
            .map(function (response) { return response.json(); })
            .catch(function (error) { return __WEBPACK_IMPORTED_MODULE_2_rxjs__["Observable"].throw(error.json().error || 'Server error'); });
    };
    RestService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["A" /* Injectable */])(),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_4__httpPacket_service__["a" /* HttpPacketService */],
            __WEBPACK_IMPORTED_MODULE_1__angular_http__["b" /* Http */]])
    ], RestService);
    return RestService;
}());



/***/ }),

/***/ "../../../../../app/user.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return UserComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("../../../router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_angular2_modal__ = __webpack_require__("../../../../angular2-modal/bundle/angular2-modal.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_angular2_modal_plugins_bootstrap__ = __webpack_require__("../../../../angular2-modal/plugins/bootstrap/bundle/angular2-modal-bootstrap.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__errorManager__ = __webpack_require__("../../../../../app/errorManager.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__authentication_service__ = __webpack_require__("../../../../../app/authentication.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__restService__ = __webpack_require__("../../../../../app/restService.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__userEdit__ = __webpack_require__("../../../../../app/userEdit.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__models_user__ = __webpack_require__("../../../../../app/models/user.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};









var UserComponent = (function () {
    function UserComponent(_router, _errorManager, _authService, _restService, modal) {
        this._router = _router;
        this._errorManager = _errorManager;
        this._authService = _authService;
        this._restService = _restService;
        this.modal = modal;
        this.title = '';
        this.users = [];
    }
    UserComponent.prototype.ngOnInit = function () {
        if (this._authService.getLoggedUser()) {
            this.setTitle();
            this.getUsers();
        }
        else {
            this._router.navigateByUrl('/login');
        }
    };
    UserComponent.prototype.setTitle = function () {
        this.title = 'User';
    };
    UserComponent.prototype.getUsers = function () {
        var _this = this;
        this._restService.getUsers(this._authService.getUserCreds())
            .subscribe(function (data) { return _this.users = data; }, //Bind to view
        function (//Bind to view
            err) { return _this._errorManager.displayError(err.message); });
    };
    UserComponent.prototype.editUser = function (user) {
        var _this = this;
        // New user
        if (user == null) {
            user = new __WEBPACK_IMPORTED_MODULE_8__models_user__["a" /* User */]("", -1);
        }
        return this.modal.open(__WEBPACK_IMPORTED_MODULE_7__userEdit__["a" /* UserEdit */], Object(__WEBPACK_IMPORTED_MODULE_2_angular2_modal__["l" /* overlayConfigFactory */])({
            onSubmitCallback: function () {
                _this.getUsers();
            },
            user: user
        }, __WEBPACK_IMPORTED_MODULE_3_angular2_modal_plugins_bootstrap__["a" /* BSModalContext */]));
    };
    UserComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'drinksit-user',
            template: __webpack_require__("../../../../../app/users.html"),
            providers: [__WEBPACK_IMPORTED_MODULE_4__errorManager__["a" /* ErrorManager */], __WEBPACK_IMPORTED_MODULE_5__authentication_service__["a" /* AuthenticationService */], __WEBPACK_IMPORTED_MODULE_6__restService__["a" /* RestService */], __WEBPACK_IMPORTED_MODULE_3_angular2_modal_plugins_bootstrap__["c" /* Modal */]]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* Router */],
            __WEBPACK_IMPORTED_MODULE_4__errorManager__["a" /* ErrorManager */],
            __WEBPACK_IMPORTED_MODULE_5__authentication_service__["a" /* AuthenticationService */],
            __WEBPACK_IMPORTED_MODULE_6__restService__["a" /* RestService */],
            __WEBPACK_IMPORTED_MODULE_3_angular2_modal_plugins_bootstrap__["c" /* Modal */]])
    ], UserComponent);
    return UserComponent;
}());



/***/ }),

/***/ "../../../../../app/userEdit.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"container-fluid\">\r\n\t<h2>Create user</h2>\r\n\t<div class=\"row\">\r\n\t\t<div class=\"col-sm-12 alert alert-danger\"\r\n\t\t\t[hidden]=\"!this._errorManager.errorMsg\">\r\n\t\t\t{{this._errorManager.errorMsg}}</div>\r\n\t</div>\r\n\t<form #form=\"ngForm\" (ngSubmit)=\"onSubmit()\">\r\n\t\t<div class=\"row\">\r\n\t\t\t<div class=\"col-sm-4\">\r\n\t\t\t\t<label for=\"username\">Username</label>\r\n\t\t\t</div>\r\n\t\t\t<div class=\"col-sm-8\">\r\n\t\t\t\t<div class=\"form-group\">\r\n\t\t\t\t\t<input type=\"text\" class=\"form-control\" id=\"name\"\r\n\t\t\t\t\t\tplaceholder=\"Username\" name=\"userName\"\r\n\t\t\t\t\t\t[(ngModel)]=\"userEditContext.user.userName\" required>\r\n\t\t\t\t</div>\r\n\t\t\t</div>\r\n\t\t</div>\r\n\t\t<div class=\"row\">\r\n\t\t\t<div class=\"col-sm-4\">\r\n\t\t\t\t<label for=\"password\">Password</label>\r\n\t\t\t</div>\r\n\t\t\t<div class=\"col-sm-8\">\r\n\t\t\t\t<div class=\"form-group\">\r\n\t\t\t\t\t<input type=\"password\" class=\"form-control\" id=\"password\"\r\n\t\t\t\t\t\tplaceholder=\"Password\" name=\"password\"\r\n\t\t\t\t\t\t[(ngModel)]=\"userEditContext.user.password\" required>\r\n\t\t\t\t</div>\r\n\t\t\t</div>\r\n\t\t</div>\r\n\t\t<div class=\"row\">\r\n\t\t\t<div class=\"col-sm-4\">\r\n\t\t\t\t<label for=\"bar\">Bar</label>\r\n\t\t\t</div>\r\n\t\t\t<div class=\"col-sm-8\">\r\n\t\t\t\t<div class=\"form-group\">\r\n\t\t\t\t\t<select [(ngModel)]=\"userEditContext.user.barId\" name=\"bar\">\r\n\t\t\t\t\t\t<option *ngFor=\"let bar of bars\" [ngValue]=\"bar.id\">{{bar.name}}</option>\r\n\t\t\t\t\t</select>\r\n\t\t\t\t</div>\r\n\t\t\t</div>\r\n\t\t</div>\r\n\t\t<div class=\"form-group\">\r\n\t\t\t<button type=\"submit\" [disabled]=\"!form.valid\" class=\"btn btn-default\">Submit</button>\r\n\t\t\t<button type=\"button\" class=\"btn btn-default\" (click)=\"onCancel()\">Cancel</button>\r\n\t\t</div>\r\n\t</form>\r\n</div>"

/***/ }),

/***/ "../../../../../app/userEdit.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* unused harmony export UserEditContext */
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return UserEdit; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_angular2_modal__ = __webpack_require__("../../../../angular2-modal/bundle/angular2-modal.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_angular2_modal_plugins_bootstrap__ = __webpack_require__("../../../../angular2-modal/plugins/bootstrap/bundle/angular2-modal-bootstrap.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__errorManager__ = __webpack_require__("../../../../../app/errorManager.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__authentication_service__ = __webpack_require__("../../../../../app/authentication.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__restService__ = __webpack_require__("../../../../../app/restService.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__httpPacket_service__ = __webpack_require__("../../../../../app/httpPacket.service.ts");
var __extends = (this && this.__extends) || (function () {
    var extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};







var UserEditContext = (function (_super) {
    __extends(UserEditContext, _super);
    function UserEditContext() {
        return _super !== null && _super.apply(this, arguments) || this;
    }
    return UserEditContext;
}(__WEBPACK_IMPORTED_MODULE_2_angular2_modal_plugins_bootstrap__["a" /* BSModalContext */]));

var UserEdit = (function () {
    function UserEdit(_errorManager, _authService, _restService, dialog) {
        this._errorManager = _errorManager;
        this._authService = _authService;
        this._restService = _restService;
        this.dialog = dialog;
        this.bars = [];
        this.userEditContext = dialog.context;
    }
    UserEdit.prototype.ngOnInit = function () {
        this.getBars();
    };
    UserEdit.prototype.getBars = function () {
        var _this = this;
        this._restService.getBars(this._authService.getUserCreds())
            .subscribe(function (data) { return _this.bars = data; }, //Bind to view
        function (//Bind to view
            err) { return _this._errorManager.displayError(err.message); });
    };
    UserEdit.prototype.onSubmit = function () {
        var _this = this;
        // post form to server
        this._restService.postUser(this.userEditContext.user, this._authService.getUserCreds())
            .subscribe(function (data) {
            _this.userEditContext.onSubmitCallback();
            _this.dialog.close();
        }, function (err) { return _this._errorManager.displayError(err.message); });
    };
    UserEdit.prototype.onCancel = function () {
        this.dialog.close();
    };
    UserEdit = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'user-edit',
            template: __webpack_require__("../../../../../app/userEdit.html"),
            providers: [__WEBPACK_IMPORTED_MODULE_3__errorManager__["a" /* ErrorManager */], __WEBPACK_IMPORTED_MODULE_4__authentication_service__["a" /* AuthenticationService */], __WEBPACK_IMPORTED_MODULE_6__httpPacket_service__["a" /* HttpPacketService */], __WEBPACK_IMPORTED_MODULE_5__restService__["a" /* RestService */]]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_3__errorManager__["a" /* ErrorManager */],
            __WEBPACK_IMPORTED_MODULE_4__authentication_service__["a" /* AuthenticationService */],
            __WEBPACK_IMPORTED_MODULE_5__restService__["a" /* RestService */],
            __WEBPACK_IMPORTED_MODULE_1_angular2_modal__["c" /* DialogRef */]])
    ], UserEdit);
    return UserEdit;
}());



/***/ }),

/***/ "../../../../../app/users.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"container\">\r\n\t<div class=\"row\">\r\n\t\t<div class=\"col-lg-12 alert alert-danger\"\r\n\t\t\t[hidden]=\"!this._errorManager.errorMsg\">\r\n\t\t\t{{this._errorManager.errorMsg}}</div>\r\n\t</div>\r\n\t<button type=\"button\" class=\"btn btn-info\" (click)=\"editUser()\">New</button>\r\n\t\r\n\t<table class=\"table table-striped\">\r\n\t\t<thead class=\"drinksit-text\">\r\n\t\t\t<tr>\r\n\t\t\t\t<th>UserName</th>\r\n\t\t\t\t<th>Bar</th>\r\n\t\t\t</tr>\r\n\t\t</thead>\r\n\t\t<tbody>\r\n\t\t\t<tr *ngFor=\"let user of users\">\r\n\t\t\t\t<td>{{user.userName}}</td>\r\n\t\t\t\t<td>{{user.barName}}</td>\r\n\t\t\t</tr>\r\n\t\t</tbody>\r\n\t</table>\r\n</div>"

/***/ }),

/***/ "../../../../../environments/environment.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return environment; });
// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `angular-cli.json`.
var environment = {
    production: false
};


/***/ }),

/***/ "../../../../../main.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__polyfills_ts__ = __webpack_require__("../../../../../polyfills.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_platform_browser_dynamic__ = __webpack_require__("../../../platform-browser-dynamic/esm5/platform-browser-dynamic.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__environments_environment__ = __webpack_require__("../../../../../environments/environment.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__app_drinksit_module__ = __webpack_require__("../../../../../app/drinksit.module.ts");





if (__WEBPACK_IMPORTED_MODULE_3__environments_environment__["a" /* environment */].production) {
    Object(__WEBPACK_IMPORTED_MODULE_2__angular_core__["_15" /* enableProdMode */])();
}
Object(__WEBPACK_IMPORTED_MODULE_1__angular_platform_browser_dynamic__["a" /* platformBrowserDynamic */])().bootstrapModule(__WEBPACK_IMPORTED_MODULE_4__app_drinksit_module__["a" /* DrinksItModule */]);


/***/ }),

/***/ "../../../../../polyfills.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_core_js_es6_symbol__ = __webpack_require__("../../../../core-js/es6/symbol.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_core_js_es6_symbol___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_core_js_es6_symbol__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_core_js_es6_object__ = __webpack_require__("../../../../core-js/es6/object.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_core_js_es6_object___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1_core_js_es6_object__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_core_js_es6_function__ = __webpack_require__("../../../../core-js/es6/function.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_core_js_es6_function___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2_core_js_es6_function__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_core_js_es6_parse_int__ = __webpack_require__("../../../../core-js/es6/parse-int.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_core_js_es6_parse_int___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_3_core_js_es6_parse_int__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_core_js_es6_parse_float__ = __webpack_require__("../../../../core-js/es6/parse-float.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_core_js_es6_parse_float___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_4_core_js_es6_parse_float__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_core_js_es6_number__ = __webpack_require__("../../../../core-js/es6/number.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_core_js_es6_number___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_5_core_js_es6_number__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_core_js_es6_math__ = __webpack_require__("../../../../core-js/es6/math.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_core_js_es6_math___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_6_core_js_es6_math__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7_core_js_es6_string__ = __webpack_require__("../../../../core-js/es6/string.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7_core_js_es6_string___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_7_core_js_es6_string__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8_core_js_es6_date__ = __webpack_require__("../../../../core-js/es6/date.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8_core_js_es6_date___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_8_core_js_es6_date__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9_core_js_es6_array__ = __webpack_require__("../../../../core-js/es6/array.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9_core_js_es6_array___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_9_core_js_es6_array__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10_core_js_es6_regexp__ = __webpack_require__("../../../../core-js/es6/regexp.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10_core_js_es6_regexp___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_10_core_js_es6_regexp__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11_core_js_es6_map__ = __webpack_require__("../../../../core-js/es6/map.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11_core_js_es6_map___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_11_core_js_es6_map__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12_core_js_es6_set__ = __webpack_require__("../../../../core-js/es6/set.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12_core_js_es6_set___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_12_core_js_es6_set__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_13_core_js_es6_reflect__ = __webpack_require__("../../../../core-js/es6/reflect.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_13_core_js_es6_reflect___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_13_core_js_es6_reflect__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_14_core_js_es7_reflect__ = __webpack_require__("../../../../core-js/es7/reflect.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_14_core_js_es7_reflect___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_14_core_js_es7_reflect__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_15_zone_js_dist_zone__ = __webpack_require__("../../../../zone.js/dist/zone.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_15_zone_js_dist_zone___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_15_zone_js_dist_zone__);
// This file includes polyfills needed by Angular 2 and is loaded before
// the app. You can add your own extra polyfills to this file.


















/***/ }),

/***/ 0:
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__("../../../../../main.ts");


/***/ })

},[0]);
//# sourceMappingURL=main.bundle.js.map