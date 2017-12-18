import { Bar }	from './bar';

export class Drink {
    constructor(
        public name: string,
        public price: number,
        public size: number,
        public bar: Bar
        ){}
}