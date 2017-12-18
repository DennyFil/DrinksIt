import { Bar }	from './bar';

export class User {
    constructor(
        public userName: string,
        public isAdmin: boolean,
        public bar: Bar
        ){}
}