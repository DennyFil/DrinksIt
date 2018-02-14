import { Routes, RouterModule }		from '@angular/router';

import { LoginComponent }			from './login.component';
import { OrderComponent }			from './order.component';
import { BarComponent }				from './bar.component';
import { DrinkComponent }			from './drink.component';
import { UserComponent }			from './user.component';
import { ReportComponent }			from './report.component';
import { QrCodeComponent }			from './qrcode.component';
import { AuthGuard }				from './authGuard';

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
        component: OrderComponent,
		canActivate: [AuthGuard] // if not authenticated user is redirected to login page
    },
    {
        path: 'app/bars',
        component: BarComponent,
		canActivate: [AuthGuard]
    },
    {
        path: 'app/drinks',
        component: DrinkComponent,
		canActivate: [AuthGuard]
    },
    {
        path: 'app/users',
        component: UserComponent,
		canActivate: [AuthGuard]
    },
    {
        path: 'app/reports',
        component: ReportComponent,
		canActivate: [AuthGuard]
    },
    {
        path: 'app/qrcode',
        component: QrCodeComponent,
		canActivate: [AuthGuard]
    }
];

export const routing = RouterModule.forRoot(appRoutes);