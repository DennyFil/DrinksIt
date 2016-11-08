import { DrinksItAdminWebPage } from './app.po';

describe('drinks-it-admin-web App', function() {
  let page: DrinksItAdminWebPage;

  beforeEach(() => {
    page = new DrinksItAdminWebPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
