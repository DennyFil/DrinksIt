import { Component } from '@angular/core';
import { Router }  from '@angular/router';
import { AuthenticationService } from './authentication.service';
import { HttpPacketService } from './httpPacket.service';
import { Http, Response, ResponseContentType } from '@angular/http';
import { Headers, RequestOptions } from '@angular/http';
import * as FileSaver from 'file-saver';

@Component({
    selector: 'drinksit-report',
    templateUrl: './reports.html',
    providers: [AuthenticationService, HttpPacketService]
})
export class ReportComponent {

	public errorMsg = '';
    title = '';

    constructor(private router: Router,
        private _authService: AuthenticationService,
        private _httpPacketService: HttpPacketService,
        private http: Http) { }

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
        let url = 'DrinksIt/ordersReport';
        let user = JSON.parse(this._authService.getLoggedUser());
        let body = JSON.stringify({ "endDate": dateTo, "startDate": dateFrom });        
        let packetOptions = this._httpPacketService.computePacketOptions('POST', user);        
        packetOptions.responseType = ResponseContentType.ArrayBuffer;

		this.http.post(url + '?startDate=' + dateFrom + '&endDate=' + dateTo, body, packetOptions)
            .subscribe(function(response) {
			    let file = new Blob([response.blob()], {type: 'application/pdf'});
				let fileURL = URL.createObjectURL(file);
				FileSaver.saveAs(file, response.headers.get('filename'));
			});
    }
}