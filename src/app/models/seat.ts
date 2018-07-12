import {user} from "./user"
import {show} from "./show"
import {booking} from "./booking"

export class seat{
    seatId: number;
    seatPaid: boolean;
    seatReserved: boolean;
    showId: show["showId"];
    userId: user["userId"];
    bookingId: booking["bookingId"];
}