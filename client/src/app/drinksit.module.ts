import { NgModule }       			from '@angular/core';
import { BrowserModule }  			from '@angular/platform-browser';
import { FormsModule }    			from '@angular/forms';
import { HttpModule, JsonpModule } 	from '@angular/http';
import { ModalModule } 				from 'angular2-modal';
import { BootstrapModalModule } 	from 'angular2-modal/plugins/bootstrap';

import { DrinksItComponent }   		from './drinksit.component';
import { ErrorManager }				from './errorManager';
import { LoginComponent }  			from './login.component';
import { OrderComponent }  			from './order.component';
import { BarComponent }  			from './bar.component';
import { DrinkComponent }  			from './drink.component';
import { UserComponent }  			from './user.component';
import { BarEdit }  				from './barEdit';
import { UserEdit }  				from './userEdit';
import { DrinkEdit }  				from './drinkEdit';
import { ConfirmModal }  			from './confirmModal';
import { ReportComponent }  		from './report.component';
import { QrCodeComponent }  		from './qrcode.component';
import { AuthGuard }				from './authGuard';
import { AuthenticationService }	from './authentication.service';
import { HttpPacketService }      	from './httpPacket.service';
import { routing }        			from './drinksit.routing';

@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        HttpModule,
        JsonpModule,
	    ModalModule.forRoot(),
	    BootstrapModalModule,
        routing
    ],
    declarations: [
        DrinksItComponent,
        ErrorManager,
        LoginComponent,
        OrderComponent,
        BarComponent,
        DrinkComponent,
        UserComponent,
        BarEdit,
        UserEdit,
        DrinkEdit,
		ConfirmModal,
        ReportComponent,
        QrCodeComponent
    ],
    providers: [AuthGuard, AuthenticationService, HttpPacketService],
    bootstrap: [DrinksItComponent],

    entryComponents: [ BarEdit, UserEdit, DrinkEdit, ConfirmModal ]
})
export class DrinksItModule {
}
