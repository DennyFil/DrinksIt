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
        redirectTo: 'app/orders',
        pathMatch: 'full'
    },
    {
        path: 'app',
        redirectTo: 'app/orders',
        pathMatch: 'full'
    },
    {
        path: 'app/login',
        component: LoginComponent
    },
    {
        path: 'app/orders',
        component: OrderComponent
    },
    {
        path: 'app/bars',
        component: BarComponent
    },
    {
        path: 'app/drinks',
        component: DrinkComponent
    },
    {
        path: 'app/users',
        component: UserComponent
    },
    {
        path: 'app/reports',
        component: ReportComponent
    },
    {
        path: 'app/qrcode',
        component: QrCodeComponent
    }
];

export const routing = RouterModule.forRoot(appRoutes);