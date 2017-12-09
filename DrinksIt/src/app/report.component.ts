import { Component } from '@angular/core';
import { Router }  from '@angular/router';
import { AuthenticationService } from './authentication.service';
import { HttpPacketService } from './httpPacket.service';
import { Http, Response, ResponseContentType } from '@angular/http';
import { Headers, RequestOptions } from '@angular/http';

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
        let url = 'DrinksIt/orderReport';

        let user = JSON.parse(this._authService.getLoggedUser());
        let userName = user.username;

        let body = JSON.stringify({ "endDate": dateTo, "userName": userName, "startDate": dateFrom });

        let method = 'POST';

        let packetOptions = this._httpPacketService.computePacketOptions(method, user, body, url);
        
        packetOptions.responseType = ResponseContentType.ArrayBuffer;

		this.http.post(url + '?userName=' + userName + '&startDate=' + dateFrom + '&endDate=' + dateTo, body, packetOptions)
            .subscribe(function(response) {
			    let file = new Blob([response], {type: 'application/pdf'});
				let fileURL = URL.createObjectURL(file);
				window.open(fileURL);
			})
            /*.subscribe( 
            	data => {				        
	                	let file = new Blob([data], {type: 'application/pdf'});
						let fileURL = URL.createObjectURL(file);
						window.open(fileURL);						
				},
                err => this.errorMsg = 'Failed to generate report',
                () => console.log('the subscription is completed')
                )*/;
    }
}