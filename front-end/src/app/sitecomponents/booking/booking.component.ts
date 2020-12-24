import {Component, OnInit} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Location} from "@angular/common";
import {Observable} from "rxjs";


@Component({
  selector: "app-booking",
  templateUrl: "./booking.component.html",
  styleUrls: ["./booking.component.css"]
})
export class BookingComponent implements OnInit {

  bookings$ = [];
  user$ = {};

  constructor(private http: HttpClient,
              private location: Location
  ) {
  }

  ngOnInit() {

    this.getUser().subscribe((data) => {
      this.user$ = data;

      this.getBookings().subscribe((bookingData) => {
        this.bookings$ = bookingData;
        this.rememberPage();
      });
    });
  }

  getUser() {
    return this.http.get("http://localhost:8080/api/loggedUser");
  }

  getBookings(): Observable<any[]> {
    let path = "http://localhost:8080/api/users/" + this.user$["userId"] + "/bookings";
    return this.http.get(path) as Observable<any[]>;
  }

  rememberPage() {
    localStorage.setItem("bookingsPage", this.location.path());
  }
}



