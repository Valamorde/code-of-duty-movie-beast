import {Theatre} from "./theatre"

export class Movie{
movieId: number;
movieDescription: string;
movieDuration: number;
movieName: string;
movieRelease: Date;
trailerURL: string;
theatreId: Theatre["theatreId"];
}