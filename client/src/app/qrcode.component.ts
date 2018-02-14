import { Component }				from '@angular/core';
import { ErrorManager } 			from './errorManager';
import { RestService }           	from './restService';

@Component({
    selector: 'drinksit-qrcode',
    templateUrl: './qrcode.html',
    providers: [ErrorManager, RestService]
})
export class QrCodeComponent {

    title = '';
    qrCode = null;

    constructor(
    	private errorManager: ErrorManager,
        private restService: RestService) { }

    ngOnInit() {
        this.setTitle();
    }

    setTitle() {
        this.title = 'QrCode';
    }

    getQRCode(drinkId) {

        this.qrCode = null;
        
        var self = this;
        this.restService.getQRCode(drinkId)
        	.subscribe(function(response) {
			    this.qrCode = response;
			}, function(error) {
				self.errorManager.displayError(error);
			});
    }
}