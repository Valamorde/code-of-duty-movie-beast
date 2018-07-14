import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CityService } from '../../../connection/city.service';
import { City } from '../../../models/city';
import { SelectItem } from 'primeng/api'; 

@Component({
  selector: 'app-managecities',
  templateUrl: './managecities.component.html',
  styleUrls: ['./managecities.component.css']
})
export class ManagecitiesComponent implements OnInit {
  
  newCity = new City();
  modifyThisCity = new City();
  deleteThisCity = new City();

  citiesList: SelectItem[]

  constructor(private cityService: CityService) {

    this.citiesList = [];

  }

  ngOnInit() {
    this.cityService.getCities().subscribe(
      data => {
        console.log(data);
        
        this.citiesList = data.map(x => {
          return { 
            label: x.cityName, 
            value: x.cityId 
          }
        });
      }
    )
  }


  createCity() {
    return this.cityService.createCity(this.newCity)
      .subscribe((res) => {
        console.log(res);

      })
  }


  modifyCity() {
    console.log(this.modifyThisCity);
    return this.cityService.modifyCity(this.modifyThisCity)
      .subscribe((res) => {
        console.log(res);

      })
  }
  deleteCity() {
    return this.cityService.deleteCity(this.deleteThisCity)
      .subscribe((res) => {
        console.log(res);

      })
  }

}