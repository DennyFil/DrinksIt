import { DrinksItPage } from './app.po';

describe('drinks-it App', function() {
  let page: DrinksItPage;

  beforeEach(() => {
    page = new DrinksItPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
