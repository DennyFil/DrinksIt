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

    computePacketSignature(date, method, contentType, body, url) {
	
        // tmp
        date = 'null';
        let signature = method + '\n' + body + '\n' + contentType + '\n' + date + '\n' + url;

        return signature;
    }

    computeHeaders(contentType) {
        return new Headers({ 'Content-Type': contentType });
    }

    computePacketOptions(method, user, body, url) {

        let contentType = 'application/json';
        let contentHeaders = this.computeHeaders(contentType);

        let date = new Date();

        //let signature = this.computePacketSignature(date, method, contentType, body, url);
        //let signatureHash = this._cryptoService.computeHmacSHA256(signature, user.passwordHash);
        //let authHeader = user.username + ":" + signatureHash;
        let authHeader = user.username + ":" + user.password;

        contentHeaders.append('Authorization', authHeader);
        //contentHeaders.append('Date', date);
		
        return new RequestOptions({ headers: contentHeaders });
    }
}