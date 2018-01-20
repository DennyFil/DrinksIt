import { Component, Injectable } from '@angular/core';

@Injectable()
@Component({
    providers: [],
    template: ``
})
export class ErrorManager {

	errorMsg = '';
    
    constructor() {
    }
    
    displayError(error) {
    	this.errorMsg = error;
    }
}