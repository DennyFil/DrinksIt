import { Injectable }								from '@angular/core';
import { Http, Response, ResponseContentType }		from '@angular/http';
import { Observable }								from 'rxjs';
import * as FileSaver								from 'file-saver';

import { AuthenticationService, UserCreds } 	    from './authentication.service';
import { HttpPacketService }						from './httpPacket.service';
import { Bar }										from './models/bar';
import { User }										from './models/user';
import { Drink }									from './models/drink';
import { Order }									from './models/order';

@Injectable()
export class RestService {
	credentials: UserCreds;
	constructor(
        private httpPacketService: HttpPacketService,
		private authService: AuthenticationService,
        private http: Http) {
			this.credentials = this.authService.getCredentials();
		}
		
	getBars(): Observable<Bar[]> {
		let packetOptions = this.httpPacketService.computePacketOptions(this.credentials);
		return this.http.get('bars/list', packetOptions)
		  .map((res: Response) => res.json())
		  .catch((error: any) => Observable.throw(error._body));
	}

	postBar(bar): Observable<Bar> {
		let packetOptions = this.httpPacketService.computePacketOptions(this.credentials);
			let body = JSON.stringify(bar);
			return this.http.post('bars/post', body, packetOptions)
		  .map((res: Response) => res.json())
		  .catch((error: any) => Observable.throw(error._body));
	}

	getDrinks(barId): Observable<Drink[]> {

		let packetOptions = this.httpPacketService.computePacketOptions(this.credentials);
		return this.http.get('drinks/list' + '?barId=' + barId, packetOptions)
		  .map((res: Response) => res.json())
		  .catch((error: any) => Observable.throw(error._body));
	}

	postDrink(drink): Observable<Drink> {
		let packetOptions = this.httpPacketService.computePacketOptions(this.credentials);
			let body = JSON.stringify(drink);
		return this.http.post('drinks/post', body, packetOptions)
			.map((res: Response) => res.json())
			.catch((error: any) => Observable.throw(error._body));
	}

	getOrders(): Observable<Order[]> {
		let packetOptions = this.httpPacketService.computePacketOptions(this.credentials);
		return this.http.get('orders', packetOptions)
			.map((res: Response) => res.json())
			.catch((error: any) => Observable.throw(error._body));
	}

	updateOrderStatus(order): Observable<Order> {
		let packetOptions = this.httpPacketService.computePacketOptions(this.credentials);
		let body = JSON.stringify(order.id);
		return this.http.post('updateOrderStatus', body, packetOptions)
			.map((res: Response) => res.json())
			.catch((error: any) => Observable.throw(error._body));
	}

	getUsers(): Observable<User[]> {
		let packetOptions = this.httpPacketService.computePacketOptions(this.credentials);
		return this.http.get('users/list', packetOptions)
			.map((res: Response) => res.json())
			.catch((error: any) => Observable.throw(error._body));
	}

	postUser(user): Observable<User> {
		let packetOptions = this.httpPacketService.computePacketOptions(this.credentials);
	    let body = JSON.stringify(user);
		return this.http.post('users/post', body, packetOptions)
			.map((res: Response) => res.json())
			.catch((error: any) => Observable.throw(error._body));
	}

	getReport(dateFrom, dateTo, onErrorCbk) {

		let body = JSON.stringify({ 'endDate': dateTo, 'startDate': dateFrom });
		let packetOptions = this.httpPacketService.computePacketOptions(this.credentials);
		packetOptions.responseType = ResponseContentType.ArrayBuffer;

		this.http.post('ordersReport', body, packetOptions)
        .subscribe(function(response) {
			let file = new Blob([response.blob()], {type: 'application/pdf'});
			let fileURL = URL.createObjectURL(file);
			FileSaver.saveAs(file, response.headers.get('filename'));
		}, function(error) {
			if (onErrorCbk) {
				onErrorCbk(error);
			}
		});
	}

	getQRCode(drinkId) {

        let body = JSON.stringify(drinkId);
        let packetOptions = this.httpPacketService.computePacketOptions(this.credentials);

        return this.http.post('qrcode', body, packetOptions)
            .map(response => response.json())
            .catch((error: any) => Observable.throw(error._body));
    }
}
