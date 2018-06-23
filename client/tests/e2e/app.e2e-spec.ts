import { DrinksItPage } from './app.po';

describe('drinks-it App', function() {
  let page: DrinksItPage;

  beforeEach(() => {
    page = new DrinksItPage();
  });

  it('should display login page', () => {
    page.navigateTo();
	// expect current URL to be 'http://localhost:4200/app/login'
  });
});
