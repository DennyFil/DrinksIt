"use strict";
require('./polyfills.ts');
const platform_browser_dynamic_1 = require('@angular/platform-browser-dynamic');
const core_1 = require('@angular/core');
const environment_1 = require('./environments/environment');
const drinksit_module_1 = require('./app/drinksit.module');
if (environment_1.environment.production) {
    core_1.enableProdMode();
}
platform_browser_dynamic_1.platformBrowserDynamic().bootstrapModule(drinksit_module_1.DrinksItModule);
