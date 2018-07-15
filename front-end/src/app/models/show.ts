import {Movie} from "./movie"

export class show{
    showId: number;
    availableSeats: number;
    initialSeats: number;
    movieId: Movie["movieId"];
    showCost: number;
    showDate: Date;
}