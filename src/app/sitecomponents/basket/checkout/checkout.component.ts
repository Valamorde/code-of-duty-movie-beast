import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router'

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {

  reservations$ = {}
  user$ = {};
  tickets$ = {}

  constructor( private http: HttpClient,
    private route: ActivatedRoute,
    private _router: Router) 
    { }

  ngOnInit() {
    this.checkoutBasket().subscribe(
      data => this.reservations$ = data
    )
    
    this.getUser().subscribe((userData) => {
      this.user$ = userData;
      this.printTicklets().subscribe((ticketData) => {
        this.tickets$ = ticketData;
      });
    });
  }

  getUser() {
    return this.http.get("http://localhost:8080/api/loggedUser");
  }



    checkoutBasket() {
      return this.http.post('http://localhost:8080/api/checkoutBasket', this.reservations$)

      
    }

    printTicklets() {
      return this.http.get('http://localhost:8080/api/users/'+ this.user$["userId"] + '/bookings/pdf')
    }



}
