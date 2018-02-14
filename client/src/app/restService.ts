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
			this.credentials = this.authService.getUserCreds();
		}
		
	getBars(): Observable<Bar[]> {
		let packetOptions = this.httpPacketService.computePacketOptions('GET', this.credentials);
		return this.http.get('bars/list', packetOptions)
		  .map((res: Response) => res.json())
		  .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
	}

	postBar(bar): Observable<Bar> {
		let packetOptions = this.httpPacketService.computePacketOptions('POST', this.credentials);
			let body = JSON.stringify(bar);
			return this.http.post('bars/post', body, packetOptions)
		  .map((res: Response) => res.json())
		  .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
	}

	getDrinks(barId): Observable<Drink[]> {

		let packetOptions = this.httpPacketService.computePacketOptions('GET', this.credentials);
		return this.http.get('drinks/list' + '?barId=' + barId, packetOptions)
		  .map((res: Response) => res.json())
		  .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
	}

	postDrink(drink): Observable<Drink> {
		let packetOptions = this.httpPacketService.computePacketOptions('POST', this.credentials);
			let body = JSON.stringify(drink);
		return this.http.post('drinks/post', body, packetOptions)
			.map((res: Response) => res.json())
			.catch((error: any) => Observable.throw(error.json().error || 'Server error'));
	}

	getOrders(): Observable<Order[]> {
		let packetOptions = this.httpPacketService.computePacketOptions('POST', this.credentials);
		return this.http.get('orders', packetOptions)
			.map((res: Response) => res.json())
			.catch((error: any) => Observable.throw(error.json().error || 'Server error'));
	}

	updateOrderStatus(order): Observable<Order> {
		let packetOptions = this.httpPacketService.computePacketOptions('POST', this.credentials);
		let body = JSON.stringify({'orderId': order.id, 'status': order.status} );
		return this.http.post('updateOrderStatus' + '?orderId=' + order.id + '&status=' + order.status, body, packetOptions)
			.map((res: Response) => res.json())
			.catch((error: any) => Observable.throw(error.json().error || 'Server error'));
	}

	getUsers(): Observable<User[]> {
		let packetOptions = this.httpPacketService.computePacketOptions('GET', this.credentials);
		return this.http.get('users/list', packetOptions)
			.map((res: Response) => res.json())
			.catch((error: any) => Observable.throw(error.json().error || 'Server error'));
	}

	postUser(user): Observable<User> {
		let packetOptions = this.httpPacketService.computePacketOptions('POST', this.credentials);
	    let body = JSON.stringify(user);
		return this.http.post('users/post', body, packetOptions)
			.map((res: Response) => res.json())
			.catch((error: any) => Observable.throw(error.json().error || 'Server error'));
	}

	getReport(dateFrom, dateTo, onErrorCbk) {

		let body = JSON.stringify({ 'endDate': dateTo, 'startDate': dateFrom });
		let packetOptions = this.httpPacketService.computePacketOptions('POST', this.credentials);
		packetOptions.responseType = ResponseContentType.ArrayBuffer;

		this.http.post('ordersReport?startDate=' + dateFrom + '&endDate=' + dateTo, body, packetOptions)
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

        let body = JSON.stringify({ 'drinkId': drinkId });
        let packetOptions = this.httpPacketService.computePacketOptions('POST', this.credentials);

        return this.http.post('qrcode?drinkId=' + drinkId, body, packetOptions)
            .map(response => response.json())
            .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
    }
}
