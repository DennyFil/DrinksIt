import { Component, Injectable } from '@angular/core';
import { Headers, RequestOptions } from '@angular/http';
import { CryptoService } from './crypto.service';

@Injectable()
@Component({
    providers: [CryptoService]
})
export class HttpPacketService {

    constructor(private _cryptoService: CryptoService) {
    }

    computeHeaders(contentType) {
        return new Headers({ 'Content-Type': contentType });
    }

    computePacketOptions(method, user) {

        let contentType = 'application/json';
        let contentHeaders = this.computeHeaders(contentType);
        
        let authHeader = user.username + ":" + user.password;
        contentHeaders.append('Authorization', authHeader);
		
        return new RequestOptions({ headers: contentHeaders });
    }
}