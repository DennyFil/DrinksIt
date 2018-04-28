import { Component } from '@angular/core';
import { DialogRef, ModalComponent } from 'angular2-modal';
import { BSModalContext } from 'angular2-modal/plugins/bootstrap';

import { ErrorManager } 			from './errorManager';

export class ConfirmModalContext extends BSModalContext {
  public onSubmitAction: Function;
  public onSubmitCallback: Function;
}

@Component({
  selector: 'confirm-modal',
  templateUrl: './confirmModal.html',
  providers: [ErrorManager]
}) 
export class ConfirmModal implements ModalComponent<ConfirmModalContext> {
  
  	confirmModalContext: ConfirmModalContext;
	    
    constructor(private errorManager: ErrorManager,
    			public dialog: DialogRef<ConfirmModalContext>) {
    			
	    this.confirmModalContext = dialog.context;
	}
	
	ngOnInit() {
    }
    
	onSubmit() {
		this.confirmModalContext.onSubmitAction()
			.subscribe(
            data => {
            	this.confirmModalContext.onSubmitCallback();
            	this.dialog.close();
            },
            err => this.errorManager.displayError(err.message));
	}
	
	onCancel() {
		this.dialog.close();
	}
}