import { Component, Injectable } from '@angular/core';
import { Headers, RequestOptions } from '@angular/http';

@Injectable()
@Component({
    providers: []
})
export class HttpPacketService {

    constructor() {
    }

    computeHeaders(contentType) {
        return new Headers({ 'Content-Type': contentType });
    }
	
	computePacketOptionsNoCreds() {
		
		let contentType = 'application/json';
        let contentHeaders = this.computeHeaders(contentType);
		
		return new RequestOptions({ headers: contentHeaders });
	}

    computePacketOptions(credentials) {

        let contentType = 'application/json';
        let contentHeaders = this.computeHeaders(contentType);

        let authHeader = credentials.userName + ':' + credentials.token;
        contentHeaders.append('Authorization', authHeader);

        return new RequestOptions({ headers: contentHeaders });
    }
}
