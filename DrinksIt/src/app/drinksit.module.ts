import { NgModule }       from '@angular/core';
import { BrowserModule }  from '@angular/platform-browser';
import { FormsModule }    from '@angular/forms';
import { HttpModule, JsonpModule } from '@angular/http';

import { DrinksItComponent }   from './drinksit.component';
import { LoginComponent }  from './login.component';
import { OrderComponent }  from './order.component';
import { BarComponent }  from './bar.component';
import { DrinkComponent }  from './drink.component';
import { UserComponent }  from './user.component';
import { ReportComponent }  from './report.component';
import { QrCodeComponent }  from './qrcode.component';
import { routing }        from './drinksit.routing';

@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        HttpModule,
        JsonpModule,
        routing
    ],
    declarations: [
        DrinksItComponent,
        LoginComponent,
        OrderComponent,
        BarComponent,
        DrinkComponent,
        UserComponent,
        ReportComponent,
        QrCodeComponent
    ],
    //providers: [],
    bootstrap: [DrinksItComponent]
})
export class DrinksItModule {
}