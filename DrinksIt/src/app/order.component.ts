import { Component, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Http, Response } from '@angular/http';

import { AuthenticationService } from './authentication.service';
import { HttpPacketService } from './httpPacket.service';
import { CryptoService } from './crypto.service';

//import { Order } from './libs';

@Component({
})
export class OrderComponent {

        let user = JSON.parse(this._authService.getLoggedUser());
        let body = JSON.stringify({});
}