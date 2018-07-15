import { User } from "./user"

export class booking {
    bookingId: number;
    bookingCost: number;
    bookingDate: Date;
    userId: User["userId"];
}