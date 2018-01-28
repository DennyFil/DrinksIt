import { Component } from '@angular/core';
import { DialogRef, ModalComponent } from 'angular2-modal';
import { BSModalContext } from 'angular2-modal/plugins/bootstrap';

import { ErrorManager } 			from './errorManager';
import { AuthenticationService } 	from './authentication.service';
import { RestService }           	from './restService';
import { HttpPacketService } 		from './httpPacket.service';
import { Bar }           			from './models/bar';

export class BarEditContext extends BSModalContext {
  public bar: Bar;
  public onSubmitCallback: Function;
}

@Component({
  selector: 'bar-edit',
  templateUrl: './barEdit.html',
  providers: [ErrorManager, AuthenticationService, HttpPacketService, RestService]
})
export class BarEdit implements ModalComponent<BarEditContext> {

  	barEditContext: BarEditContext;

    constructor(private _errorManager: ErrorManager,
    			private _authService: AuthenticationService,
    			private _restService: RestService,
    			public dialog: DialogRef<BarEditContext>) {

	    this.barEditContext = dialog.context;
	}

	onSubmit() {
		// post form to server
		this._restService.postBar(this.barEditContext.bar, this._authService.getUserCreds())
			.subscribe(
            data => {
            	this.barEditContext.onSubmitCallback();
            	this.dialog.close();
            },
            err => this._errorManager.displayError(err.message));
	}

	onCancel() {
		this.dialog.close();
	}
}
