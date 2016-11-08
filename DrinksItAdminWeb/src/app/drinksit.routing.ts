import { Routes, RouterModule }  from '@angular/router';

import { LoginComponent }  from './login.component';
import { OrderComponent }  from './order.component';
import { ReportComponent }  from './report.component';
import { QrCodeComponent }  from './qrcode.component';

const appRoutes: Routes = [
    {
        path: '',
        redirectTo: '/orders',
        pathMatch: 'full'
    },
    {
        path: 'login',
        component: LoginComponent
    },
    {
        path: 'orders',
        component: OrderComponent
    },
    {
        path: 'reports',
        component: ReportComponent
    },
    {
        path: 'qrcode',
        component: QrCodeComponent
    }
];

export const routing = RouterModule.forRoot(appRoutes);