import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Movie} from "../models/movie";

@Injectable({
  providedIn: "root"
})
export class MovieService {

  constructor(private http: HttpClient) {
  }


  createMovie(movie: Movie) {
    return this.http.post("http://localhost:8080/api/admin/movies/generateMovie", movie);
  }

}
