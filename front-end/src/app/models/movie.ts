import {Theatre} from "./theatre";

export class Movie {
  movieId: number;
  movieDescription: string;
  movieDuration: number;
  movieName: string;
  movieReleaseDate: Date;
  trailerURL: string;
  theatreId: Theatre["theatreId"];
}
