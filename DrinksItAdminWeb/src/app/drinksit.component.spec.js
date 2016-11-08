/* tslint:disable:no-unused-variable */
"use strict";
const testing_1 = require('@angular/core/testing');
const app_component_1 = require('./app.component');
describe('App: DrinksItAdminWeb', () => {
    beforeEach(() => {
        testing_1.TestBed.configureTestingModule({
            declarations: [
                app_component_1.AppComponent
            ],
        });
    });
    it('should create the app', testing_1.async(() => {
        let fixture = testing_1.TestBed.createComponent(app_component_1.AppComponent);
        let app = fixture.debugElement.componentInstance;
        expect(app).toBeTruthy();
    }));
    it(`should have as title 'app works!'`, testing_1.async(() => {
        let fixture = testing_1.TestBed.createComponent(app_component_1.AppComponent);
        let app = fixture.debugElement.componentInstance;
        expect(app.title).toEqual('app works!');
    }));
    it('should render title in a h1 tag', testing_1.async(() => {
        let fixture = testing_1.TestBed.createComponent(app_component_1.AppComponent);
        fixture.detectChanges();
        let compiled = fixture.debugElement.nativeElement;
        expect(compiled.querySelector('h1').textContent).toContain('app works!');
    }));
});
