import { Component, Injectable } from '@angular/core';
//import { HmacSHA256 } from 'crypto-js/hmac-sha256';

@Injectable()
export class CryptoService {

	constructor() {	}
	computeHmacSHA256(signature, passwordHash) {
		    	var HmacSHA256 = require("crypto-js/hmac-sha256");    	var encBase64 = require('crypto-js/enc-base64');
    	return HmacSHA256(signature, passwordHash).toString(encBase64);	}		computeHash(password) {
		    	//var createHash = require('crypto-js/sha256');    	var createHash = require('crypto-js/sha512');    	var encBase64 = require('crypto-js/enc-base64');	    	var base64HashString = createHash(password).toString(encBase64);	    	return base64HashString;	}
}