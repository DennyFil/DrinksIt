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

    computePacketOptions(method, user) {

        let contentType = 'application/json';
        let contentHeaders = this.computeHeaders(contentType);
        
        let authHeader = user.userName + ":" + user.password;
        contentHeaders.append('Authorization', authHeader);
		
        return new RequestOptions({ headers: contentHeaders });
    }
}