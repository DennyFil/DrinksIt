import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs';

import { HttpPacketService } 		from './httpPacket.service';
import { Bar }           			from './models/bar';
import { User }           			from './models/user';
import { Drink }           			from './models/drink';
import { Order }           			from './models/order';

@Injectable()
export class RestService {
   constructor(
        private _httpPacketService: HttpPacketService,
        private http: Http) { }
	
	getBars(user) : Observable<Bar[]> {
	
        let packetOptions = this._httpPacketService.computePacketOptions('GET', user);
				
        return this.http.get('DrinksIt/bars', packetOptions)
        	.map((res:Response) => res.json())
            .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
     }
     
     getDrinks(user, barId) : Observable<Drink[]> {

        let packetOptions = this._httpPacketService.computePacketOptions('GET', user);
				
        return this.http.get('DrinksIt/drinks' + '?barId=' + barId, packetOptions)
        	.map((res:Response) => res.json())
            .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
     }
     
     getOrders(user) : Observable<Order[]> {
	
        let packetOptions = this._httpPacketService.computePacketOptions('POST', user);
				
        return this.http.get('DrinksIt/orders', packetOptions)
        	.map((res:Response) => res.json())
            .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
     }
     
     updateOrderStatus(order, user) : Observable<string> {
	
        let packetOptions = this._httpPacketService.computePacketOptions('POST', user);
		let body = JSON.stringify({"orderId": order.id, "status": order.status} );
		
        return this.http.post('DrinksIt/updateOrderStatus' + '?orderId=' + order.id + '&status=' + order.status, body, packetOptions)
        	.map((res:Response) => res.json())
            .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
     }
     
     getUsers(loggedInUser) : Observable<User[]> {
	
        let packetOptions = this._httpPacketService.computePacketOptions('GET', loggedInUser);
				
        return this.http.get('DrinksIt/users', packetOptions)
        	.map((res:Response) => res.json())
            .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
     }
} 