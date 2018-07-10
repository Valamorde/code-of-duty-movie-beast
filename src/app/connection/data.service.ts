import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  constructor(private http: HttpClient) { }

  getCities() {
    return this.http.get('http://localhost:8080/api/cities')

  }

  getMovies() {
    return this.http.get('http://localhost:8080/api/movies')

  }

}