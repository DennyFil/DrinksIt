"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
const core_1 = require('@angular/core');
const http_1 = require('@angular/http');
const crypto_service_1 = require('./crypto.service');
let HttpPacketService = class HttpPacketService {
    constructor(_cryptoService) {
        this._cryptoService = _cryptoService;
    }
    computePacketSignature(date, method, contentType, body, url) {
        // tmp
        date = 'null';
        let signature = method + '\n' + body + '\n' + contentType + '\n' + date + '\n' + url;
        return signature;
    }
    computeHeaders(contentType) {
        return new http_1.Headers({ 'Content-Type': contentType });
    }
    computePacketOptions(method, user, body, url) {
        let contentType = 'application/json';
        let contentHeaders = this.computeHeaders(contentType);
        let date = new Date();
        let signature = this.computePacketSignature(date, method, contentType, body, url);
        let signatureHash = this._cryptoService.computeHmacSHA256(signature, user.passwordHash);
        let authHeader = user.username + ":" + signatureHash;
        contentHeaders.append('Authorization', authHeader);
        //contentHeaders.append('Date', date);
        return new http_1.RequestOptions({ headers: contentHeaders });
    }
};
HttpPacketService = __decorate([
    core_1.Injectable(),
    core_1.Component({
        providers: [crypto_service_1.CryptoService]
    })
], HttpPacketService);
exports.HttpPacketService = HttpPacketService;
