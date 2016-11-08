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
let ReportComponent = class ReportComponent {
    constructor(router, _authService, _httpPacketService, http) {
        this.router = router;
        this._authService = _authService;
        this._httpPacketService = _httpPacketService;
        this.http = http;
        this.errorMsg = '';
        this.title = '';
        this.orders = [];
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
        this.title = 'Order Report';
    }
    generateReport(dateFrom, dateTo) {
        // Create pdf and open in new tab
    }
    getReportData(dateFrom, dateTo) {
        this.errorMsg = '';
        let url = 'http://drinksit:8080/DrinksItSrv/dateFilteredOrders';
        let user = JSON.parse(this._authService.getLoggedUser());
        let userName = user.username;
        let body = JSON.stringify({ "endDate": dateTo, "userName": userName, "startDate": dateFrom });
        let method = 'POST';
        let packetOptions = this._httpPacketService.computePacketOptions(method, user, body, url);
        this.http.post(url + '?userName=' + userName + '&startDate=' + dateFrom + '&endDate=' + dateTo, body, packetOptions)
            .map(response => response.json())
            .subscribe(data => this.orders = data, err => this.errorMsg = 'Failed to generate report', () => console.log('the subscription is completed') //this.generateReport()
         //this.generateReport()
        );
    }
};
ReportComponent = __decorate([
    core_1.Component({
        selector: 'drinksit-report',
        templateUrl: './reports.html',
        providers: [authentication_service_1.AuthenticationService, httpPacket_service_1.HttpPacketService]
    })
], ReportComponent);
exports.ReportComponent = ReportComponent;
