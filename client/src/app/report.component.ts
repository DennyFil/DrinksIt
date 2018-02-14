import { Component }				from '@angular/core';
import { ErrorManager } 			from './errorManager';
import * as FileSaver				from 'file-saver';

import { RestService }           	from './restService';

@Component({
    selector: 'drinksit-report',
    templateUrl: './reports.html',
    providers: [ErrorManager, RestService]
})
export class ReportComponent {

    title = '';

    constructor(
    	private errorManager: ErrorManager,
        private restService: RestService) { }

    ngOnInit() {
        this.setTitle();
    }

    setTitle() {
        this.title = 'Order Report';
    }

    getReportData(dateFrom, dateTo) {

    	let self = this;
        this.restService.getReport(dateFrom, dateTo,
          function(error) {
            self.errorManager.displayError(error);
          });
    }
}
