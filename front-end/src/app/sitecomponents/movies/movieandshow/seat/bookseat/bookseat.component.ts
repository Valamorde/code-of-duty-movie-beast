import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { trigger, style, transition, animate, query, stagger } from '@angular/animations';

@Component({
  selector: 'app-bookseat',
  templateUrl: './bookseat.component.html',
  styleUrls: ['./bookseat.component.css']
})
export class BookseatComponent implements OnInit {

  seat$ = {};
  bookedSeat$ = {};

  constructor(
    private http: HttpClient,
    private route: ActivatedRoute,
    private _router: Router
  ) { }



  ngOnInit() {
    this.getSeat().subscribe(
      (data) => {
        this.seat$ = data;
        this.addToBasket().subscribe((seatData) => {
          this.bookedSeat$ = seatData;
          this._router.navigate([localStorage.getItem("currentShowPage")]);
        });
      });
  }

  getSeat() {
    const seatid = +this.route.snapshot.paramMap.get('seatid');
    return this.http.get('http://localhost:8080/api/seats/' + seatid)
  }

  addToBasket() {
    return this.http.post('http://localhost:8080/api/seatReservation/reserve', this.seat$)
  }
}
