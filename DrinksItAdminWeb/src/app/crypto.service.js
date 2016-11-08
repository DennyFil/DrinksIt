"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
const core_1 = require('@angular/core');
//import { HmacSHA256 } from 'crypto-js/hmac-sha256';
let CryptoService = class CryptoService {
    constructor() {
    }
    computeHmacSHA256(signature, passwordHash) {
        var HmacSHA256 = require("crypto-js/hmac-sha256");
        var encBase64 = require('crypto-js/enc-base64');
        return HmacSHA256(signature, passwordHash).toString(encBase64);
    }
    computeHash(password) {
        //var createHash = require('crypto-js/sha256');
        var createHash = require('crypto-js/sha512');
        var encBase64 = require('crypto-js/enc-base64');
        var base64HashString = createHash(password).toString(encBase64);
        return base64HashString;
    }
};
CryptoService = __decorate([
    core_1.Injectable()
], CryptoService);
exports.CryptoService = CryptoService;
