"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
const core_1 = require('@angular/core');
const platform_browser_1 = require('@angular/platform-browser');
const forms_1 = require('@angular/forms');
const http_1 = require('@angular/http');
const drinksit_component_1 = require('./drinksit.component');
const login_component_1 = require('./login.component');
const order_component_1 = require('./order.component');
const report_component_1 = require('./report.component');
const qrcode_component_1 = require('./qrcode.component');
const drinksit_routing_1 = require('./drinksit.routing');
let DrinksItModule = class DrinksItModule {
};
DrinksItModule = __decorate([
    core_1.NgModule({
        imports: [
            platform_browser_1.BrowserModule,
            forms_1.FormsModule,
            http_1.HttpModule,
            http_1.JsonpModule,
            drinksit_routing_1.routing
        ],
        declarations: [
            drinksit_component_1.DrinksItComponent,
            login_component_1.LoginComponent,
            order_component_1.OrderComponent,
            report_component_1.ReportComponent,
            qrcode_component_1.QrCodeComponent
        ],
        //providers: [],
        bootstrap: [drinksit_component_1.DrinksItComponent]
    })
], DrinksItModule);
exports.DrinksItModule = DrinksItModule;
