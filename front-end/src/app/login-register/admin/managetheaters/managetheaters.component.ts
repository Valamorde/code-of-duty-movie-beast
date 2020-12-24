import {Component, OnInit} from "@angular/core";
import {Theatre} from "../../../models/theatre";
import {SelectItem} from "primeng/api";
import {TheatreService} from "../../../connection/theatre.service";
import {CityService} from "../../../connection/city.service";

@Component({
  selector: "app-managetheaters",
  templateUrl: "./managetheaters.component.html",
  styleUrls: ["./managetheaters.component.css"]
})
export class ManagetheatersComponent implements OnInit {

  // resetPassword = new Theatre();
  createNewTheater = new Theatre();
  modifyThisTheatre = new Theatre();
  deleteThisTheatre = new Theatre();
  passwordThisTheatre = new Theatre();

  theatreList: SelectItem[];
  theatreListModify: SelectItem[];
  citiesList: SelectItem[];

  constructor(private theatreService: TheatreService,
              private cityService: CityService) {

    this.theatreList = [];
    this.citiesList = [];
  }

  ngOnInit() {
    this.theatreService.getTheatres().subscribe(
      data => {
        console.log(data);

        this.theatreList = data.map(x => {
          return {
            label: x.theatreName,
            value: x.theatreId
          };
        });

        this.theatreListModify = data.map(x => {
          return {
            label: x.theatreName,
            value: x
          };
        });
      }
    );

    this.cityService.getCities().subscribe(
      data => {
        console.log(data);

        this.citiesList = data.map(x => {
          return {
            label: x.cityName,
            value: x
          };
        });
      }
    );
  }

  createTheater() {
    return this.theatreService.createTheatre(this.createNewTheater)
      .subscribe((res) => {
        console.log(res);

      });
  }


  modifyTheatre() {
    console.log(this.modifyThisTheatre);
    return this.theatreService.modifyTheatre(this.modifyThisTheatre)
      .subscribe((res) => {
        console.log(res);

      });
  }

  deleteTheatre() {
    return this.theatreService.deleteTheatre(this.deleteThisTheatre)
      .subscribe((res) => {
        console.log(res);

      });
  }
}
