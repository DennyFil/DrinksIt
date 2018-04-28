import { Drink }	from './drink';

export class Order {
    constructor(
		public id: number, 
    	public ts_create: Date,
    	public ts_update: Date,
        public status: string,
        public quantity: number,
        public drink: Drink
        ){}
}