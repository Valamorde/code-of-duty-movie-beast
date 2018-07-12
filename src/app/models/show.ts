import {movie} from "./movie"

export class show{
    showId: number;
    availableSeats: number;
    initialSeats: number;
    movieId: movie["movieId"];
    showCost: number;
    showDate: Date;
}