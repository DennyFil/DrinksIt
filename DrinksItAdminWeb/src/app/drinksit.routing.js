"use strict";
const router_1 = require('@angular/router');
const login_component_1 = require('./login.component');
const order_component_1 = require('./order.component');
const report_component_1 = require('./report.component');
const qrcode_component_1 = require('./qrcode.component');
const appRoutes = [
    {
        path: '',
        redirectTo: '/orders',
        pathMatch: 'full'
    },
    {
        path: 'login',
        component: login_component_1.LoginComponent
    },
    {
        path: 'orders',
        component: order_component_1.OrderComponent
    },
    {
        path: 'reports',
        component: report_component_1.ReportComponent
    },
    {
        path: 'qrcode',
        component: qrcode_component_1.QrCodeComponent
    }
];
exports.routing = router_1.RouterModule.forRoot(appRoutes);
