import {User} from "./user";
import {show} from "./show";
import {booking} from "./booking";

export class Seat {
  seatId: number;
  seatPaid: boolean;
  seatReserved: boolean;
  showId: show["showId"];
  userId: User["userId"];
  bookingId: booking["bookingId"];
}
