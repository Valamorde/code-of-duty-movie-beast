import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-managecities',
  templateUrl: './managecities.component.html',
  styleUrls: ['./managecities.component.css']
})
export class ManagecitiesComponent implements OnInit {
  private cityUrl = "http://localhost:8080/api/cities"
  createNewCity = {}
  modifyCity = {}
  deleteCity = {}

  constructor(private http: HttpClient) { }

  ngOnInit() {
  }



  // createNewCity(city) {
  //   return this.http.post<any>(this.cityUrl, city)
  // }

  // modifyCity(city) {
  //   return this.http.post<any>(this.cityUrl, city)
  // }

  // deleteCity(city) {
  //   return this.http.post<any>(this.cityUrl, city)
  // }
}