import { Component }                    from '@angular/core';
import { DialogRef, ModalComponent }    from 'angular2-modal';
import { BSModalContext }               from 'angular2-modal/plugins/bootstrap';

import { ErrorManager }					from './errorManager';
import { RestService }					from './restService';
import { Bar }							from './models/bar';

export class BarEditContext extends BSModalContext {
  public bar: Bar;
  public isNew: boolean;
  public onSubmitCallback: Function;
}

@Component({
  selector: 'bar-edit',
  templateUrl: './barEdit.html',
  providers: [ErrorManager, RestService]
})
export class BarEdit implements ModalComponent<BarEditContext> {

  	barEditContext: BarEditContext;

    constructor(private errorManager: ErrorManager,
    			private restService: RestService,
    			public dialog: DialogRef<BarEditContext>) {

	    this.barEditContext = dialog.context;
	}

	onSubmit() {
		// post form to server
		this.restService.postBar(this.barEditContext.bar)
			.subscribe(
            data => {
            	this.barEditContext.onSubmitCallback();
            	this.dialog.close();
            },
            err => this.errorManager.displayError(err.message));
	}

	onCancel() {
		this.dialog.close();
	}
}
