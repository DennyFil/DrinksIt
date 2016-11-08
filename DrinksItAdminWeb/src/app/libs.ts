
export class Bar {
  id;
  name: String;
  country: String;
  city: String;
  address: String;
}

export class Drink {
  //id: integer;
  bar: Bar;
  name: String;
  price: String;
  size: String;
}

export class Order {
  //id: integer;
  drink: Drink;
  ts_create: String;
  ts_update: String;
  //quantity: integer;
  orderStatus: String;
}