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
  api = 'api/';
	constructor(
        private httpPacketService: HttpPacketService,
		private authService: AuthenticationService,
        private http: Http) {
			this.credentials = this.authService.getCredentials();
		}
	
	login(userCreds): Observable<any> {

        let body = JSON.stringify(userCreds);
		let packetOptions = this.httpPacketService.computePacketOptionsNoCreds();

        return this.http.post(this.api + 'login', body, packetOptions)
			.map((res: Response) => res.json())
			.catch((error: any) => Observable.throw(error._body));
    }
	
	deleteElement(moduleP, id) {
		let packetOptions = this.httpPacketService.computePacketOptions(this.credentials);
		let body = JSON.stringify(id);
		return this.http.post(this.api + moduleP + '/delete', body, packetOptions)
		  .map((res: Response) => res.json())
		  .catch((error: any) => Observable.throw(error._body));
	}
		
	getBars(): Observable<Bar[]> {
		let packetOptions = this.httpPacketService.computePacketOptions(this.credentials);
		return this.http.get(this.api + 'bars/list', packetOptions)
		  .map((res: Response) => res.json())
		  .catch((error: any) => Observable.throw(error._body));
	}

	postBar(bar): Observable<Bar> {
		let packetOptions = this.httpPacketService.computePacketOptions(this.credentials);
			let body = JSON.stringify(bar);
			return this.http.post(this.api + 'bars/post', body, packetOptions)
		  .map((res: Response) => res.json())
		  .catch((error: any) => Observable.throw(error._body));
	}

	getDrinks(barId): Observable<Drink[]> {

		let packetOptions = this.httpPacketService.computePacketOptions(this.credentials);
		return this.http.get(this.api + 'drinks/list' + '?barId=' + barId, packetOptions)
		  .map((res: Response) => res.json())
		  .catch((error: any) => Observable.throw(error._body));
	}

	postDrink(drink): Observable<Drink> {
		let packetOptions = this.httpPacketService.computePacketOptions(this.credentials);
		let body = JSON.stringify(drink);
		return this.http.post(this.api + 'drinks/post', body, packetOptions)
			.map((res: Response) => res.json())
			.catch((error: any) => Observable.throw(error._body));
	}

	getOrders(): Observable<Order[]> {
		let packetOptions = this.httpPacketService.computePacketOptions(this.credentials);
		return this.http.get(this.api + 'orders', packetOptions)
			.map((res: Response) => res.json())
			.catch((error: any) => Observable.throw(error._body));
	}

	updateOrderStatus(order): Observable<Order> {
		let packetOptions = this.httpPacketService.computePacketOptions(this.credentials);
		let body = JSON.stringify(order.id);
		return this.http.post(this.api + 'updateOrderStatus', body, packetOptions)
			.map((res: Response) => res.json())
			.catch((error: any) => Observable.throw(error._body));
	}

	getUsers(): Observable<User[]> {
		let packetOptions = this.httpPacketService.computePacketOptions(this.credentials);
		return this.http.get(this.api + 'users/list', packetOptions)
			.map((res: Response) => res.json())
			.catch((error: any) => Observable.throw(error._body));
	}

	postUser(user): Observable<User> {
		let packetOptions = this.httpPacketService.computePacketOptions(this.credentials);
	    let body = JSON.stringify(user);
		return this.http.post(this.api + 'users/post', body, packetOptions)
			.map((res: Response) => res.json())
			.catch((error: any) => Observable.throw(error._body));
	}

	getReport(dateFrom, dateTo, onErrorCbk) {

		let packetOptions = this.httpPacketService.computePacketOptions(this.credentials);
		packetOptions.responseType = ResponseContentType.ArrayBuffer;

		this.http.post(this.api + 'ordersReport?startDate=' + dateFrom + '&endDate=' + dateTo, null, packetOptions)
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

        return this.http.post(this.api + 'qrcode', body, packetOptions)
            .map(response => response.json())
            .catch((error: any) => Observable.throw(error._body));
    }
}
