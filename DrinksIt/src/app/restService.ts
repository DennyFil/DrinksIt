import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs';

import { AuthenticationService } from './authentication.service';
import { HttpPacketService } from './httpPacket.service';

@Injectable()
export class RestService {
   constructor(
        private _httpPacketService: HttpPacketService,
        private http: Http) { }  

	extractData(res: Response) {
		return res.json();
	}

	/*getList(module): Observable { 
		return this.http.get('DrinksIt/module').map(this.extractData); 
	}*/
} 