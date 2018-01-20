import { Routes, RouterModule }  from '@angular/router';

import { LoginComponent }  from './login.component';
import { OrderComponent }  from './order.component';
import { BarComponent }  from './bar.component';
import { DrinkComponent }  from './drink.component';
import { UserComponent }  from './user.component';
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
        path: 'bars',
        component: BarComponent
    },
    {
        path: 'drinks',
        component: DrinkComponent
    },
    {
        path: 'users',
        component: UserComponent
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