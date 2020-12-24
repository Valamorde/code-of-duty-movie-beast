import {Component, OnInit} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: "app-removebooking",
  templateUrl: "./removebooking.component.html",
  styleUrls: ["./removebooking.component.css"]
})
export class RemovebookingComponent implements OnInit {
  booking$ = {};
  user$ = {};

  constructor(
    private http: HttpClient,
    private route: ActivatedRoute,
    private _router: Router
  ) {
  }

  ngOnInit() {
    this.getUser().subscribe((userData) => {
      this.user$ = userData;
      this.getBooking().subscribe(
        (data) => {
          this.booking$ = data;
          this.removeBooking().subscribe((bookingData) => {
            this._router.navigate([localStorage.getItem("bookingsPage")]);
          });
        });
    });
  }

  getUser() {
    return this.http.get("http://localhost:8080/api/loggedUser");
  }

  getBooking() {
    const bookingId = +this.route.snapshot.paramMap.get("bookingid");
    return this.http.get("http://localhost:8080/api/users/" + this.user$["userId"] + "/bookings/" + bookingId);
  }

  removeBooking() {
    return this.http.post("http://localhost:8080/api/cancelBooking", this.booking$);
  }
}
