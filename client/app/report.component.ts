import { Component } from '@angular/core';
import { Router }  from '@angular/router';
import { ErrorManager } 			from './errorManager';
import { AuthenticationService } from './authentication.service';
import * as FileSaver from 'file-saver';

import { RestService }           	from './restService';

@Component({
    selector: 'drinksit-report',
    templateUrl: './reports.html',
    providers: [ErrorManager, AuthenticationService, RestService]
})
export class ReportComponent {

	public errorMsg = '';
    title = '';

    constructor(private router: Router,
    	private _errorManager: ErrorManager,
        private _authService: AuthenticationService,
        private _restService: RestService) { }

    ngOnInit() {
        if (this._authService.getLoggedUser()) {
            this.setTitle();
        }
        else {
            this.router.navigateByUrl('/login');
        }
    }

    setTitle() {
        this.title = 'Order Report';
    }

    getReportData(dateFrom, dateTo) {
    
    	this.errorMsg = '';
    	var self = this;
        this._restService.getReport(dateFrom, dateTo, this._authService.getUserCreds(), function(error) { self._errorManager.displayError(error); });
    }
}