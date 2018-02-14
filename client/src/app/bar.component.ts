import { Component, Injectable } from '@angular/core';
import { Overlay, overlayConfigFactory } from 'angular2-modal';
import { Modal, BSModalContext } from 'angular2-modal/plugins/bootstrap';

import { ErrorManager } 				from './errorManager';
import { RestService }           		from './restService';
import { BarEditContext, BarEdit }   	from './barEdit';
import { Bar }           				from './models/bar';

@Component({
    selector: 'drinksit-bar',
    templateUrl: './bars.html',
    providers: [ErrorManager, RestService, Modal]
})
export class BarComponent {

    title = '';
    bars = [];

    constructor(
        private errorManager: ErrorManager,
        private restService: RestService,
        public modal: Modal) { }

    ngOnInit() {
		this.setTitle();
		this.getBars();
    }

    setTitle() {
        this.title = 'Bars';
    }

    getBars() {

		this.restService.getBars()
			.subscribe(
            	data => this.bars = data, // Bind to view
                err => this.errorManager.displayError(err.message));
    }

    editBar(bar) {

    	// New bar
    	if (bar == null) {
    		bar = new Bar(-1, '', '', '', '');
    	}

    	return this.modal.open(BarEdit,
    		overlayConfigFactory(
    		{
	    		onSubmitCallback: () => {
	               this.getBars();
	          	},
	           	bar: bar
	        },
           	BSModalContext));
  	}
}
