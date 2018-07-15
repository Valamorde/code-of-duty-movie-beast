import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { City } from '../models/city';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CityService {

  constructor(private http: HttpClient) { }

  getCities() : Observable<City[]> {
    return this.http.get('http://localhost:8080/api/cities') as Observable<City[]>;
  }

  createCity(city: City) {
    return this.http.post('http://localhost:8080/api/admin/cities', city)
  }

  modifyCity(city: City){
      return this.http.put('http://localhost:8080/api/admin/cities/' + city.cityId , city)
  }

  deleteCity(city: City){
      return this.http.delete('http://localhost:8080/api/admin/cities/' + city.cityId )
  }

}