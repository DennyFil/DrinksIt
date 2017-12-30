export class User {
    constructor(
        public userName: string,
        public isAdmin: boolean,
        public barId: number
        )
        {
        	this.barId = -1;
        }
}