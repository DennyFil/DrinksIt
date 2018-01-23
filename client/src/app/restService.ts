import { Injectable } from '@angular/core';
import { Http, Response, ResponseContentType } from '@angular/http';
import { Observable } from 'rxjs';
import * as FileSaver from 'file-saver';

import { HttpPacketService } 		from './httpPacket.service';
import { Bar }           			  from './models/bar';
import { User }           			from './models/user';
import { Drink }           			from './models/drink';
import { Order }           			from './models/order';

@Injectable()
export class RestService {
   constructor(
        private _httpPacketService: HttpPacketService,
        private http: Http) { }

  getBars(user): Observable<Bar[]> {
    let packetOptions = this._httpPacketService.computePacketOptions('GET', user);
    return this.http.get('bars', packetOptions)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  postBar(bar, userCreds): Observable<Bar> {
    let packetOptions = this._httpPacketService.computePacketOptions('POST', userCreds);
		let body = JSON.stringify(bar);
		return this.http.post('postBar', body, packetOptions)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  getDrinks(user, barId): Observable<Drink[]> {

    let packetOptions = this._httpPacketService.computePacketOptions('GET', user);
    return this.http.get('drinks' + '?barId=' + barId, packetOptions)
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
   }

  postDrink(drink, userCreds): Observable<Drink> {
    let packetOptions = this._httpPacketService.computePacketOptions('POST', userCreds);
		let body = JSON.stringify(drink);
    return this.http.post('postDrink', body, packetOptions)
    	.map((res: Response) => res.json())
        .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

   getOrders(user): Observable<Order[]> {
      let packetOptions = this._httpPacketService.computePacketOptions('POST', user);
      return this.http.get('orders', packetOptions)
      	.map((res: Response) => res.json())
          .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
   }

   updateOrderStatus(order, user): Observable<Order> {
      let packetOptions = this._httpPacketService.computePacketOptions('POST', user);
	    let body = JSON.stringify({'orderId': order.id, 'status': order.status} );
      return this.http.post('updateOrderStatus' + '?orderId=' + order.id + '&status=' + order.status, body, packetOptions)
      	.map((res: Response) => res.json())
          .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
   }

   getUsers(loggedInUser): Observable<User[]> {
      let packetOptions = this._httpPacketService.computePacketOptions('GET', loggedInUser);
      return this.http.get('users', packetOptions)
      	.map((res: Response) => res.json())
          .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
   }

   postUser(user, userCreds): Observable<User> {
      let packetOptions = this._httpPacketService.computePacketOptions('POST', userCreds);
	    let body = JSON.stringify(user);
      return this.http.post('postUser', body, packetOptions)
      	.map((res: Response) => res.json())
          .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
   }

   getReport(dateFrom, dateTo, user, onErrorCbk) {

    let body = JSON.stringify({ 'endDate': dateTo, 'startDate': dateFrom });
    let packetOptions = this._httpPacketService.computePacketOptions('POST', user);
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

	getQRCode(drinkId, user) {

        let body = JSON.stringify({ 'drinkId': drinkId });
        let packetOptions = this._httpPacketService.computePacketOptions('POST', user);

        return this.http.post('qrcode?drinkId=' + drinkId, body, packetOptions)
            .map(response => response.json())
            .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
    }
}
