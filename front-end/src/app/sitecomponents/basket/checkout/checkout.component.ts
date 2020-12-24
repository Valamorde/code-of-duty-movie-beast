import {Component, OnInit} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: "app-checkout",
  templateUrl: "./checkout.component.html",
  styleUrls: ["./checkout.component.css"]
})
export class CheckoutComponent implements OnInit {

  reservations$ = {};
  user$ = {};
  tickets$ = {};

  constructor(private http: HttpClient,
              private route: ActivatedRoute,
              private _router: Router) {
  }

  ngOnInit() {
    this.getUser().subscribe((userData) => {
      this.user$ = userData;
    });
  }

  getUser() {
    return this.http.get("http://localhost:8080/api/loggedUser");
  }

  checkoutAndPrintTickets() {
    this.postCheckoutBasket().subscribe((data) => {
      this.reservations$ = data;
      this.getPrintedTicklets().subscribe((ticketData) => {
        this.tickets$ = ticketData;
        let file = new Blob([this.tickets$], {type: "application/pdf"});
        let fileURL = URL.createObjectURL(file);
        window.open(fileURL);
      });
    });
  }

  postCheckoutBasket() {
    return this.http.post("http://localhost:8080/api/checkoutBasket", {});
  }

  getPrintedTicklets() {
    return this.http.get("http://localhost:8080/api/users/" + this.user$["userId"] + "/bookings/pdf", {responseType: "blob"});
  }
}
