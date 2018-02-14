import { Component, Injectable } from '@angular/core';

@Injectable()
@Component({
    providers: [],
    template: ''
})
export class ErrorManager {

	errorMsg = '';

    constructor() {
    }

    // Display error message during 10 seconds
    displayError(error) {
    	this.errorMsg = error;
		setTimeout(() => {
		  this.errorMsg = '';
		}, 10000);
    }
}
