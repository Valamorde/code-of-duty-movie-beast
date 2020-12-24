import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Theatre} from "../models/theatre";
import {Observable} from "rxjs";

@Injectable({
  providedIn: "root"
})

export class TheatreService {

  constructor(private http: HttpClient) {
  }

  getTheatres(): Observable<Theatre[]> {
    return this.http.get("http://localhost:8080/api/theatres") as Observable<Theatre[]>;
  }

  createTheatre(theatre: Theatre) {
    return this.http.post("http://localhost:8080/api/admin/theatres/", theatre);
  }

  modifyTheatre(theatre: Theatre) {
    return this.http.put("http://localhost:8080/api/admin/theatres/" + theatre.theatreId, theatre);
  }

  deleteTheatre(theatre: Theatre) {
    return this.http.delete("http://localhost:8080/api/admin/theatres/" + theatre.theatreId);
  }

}
