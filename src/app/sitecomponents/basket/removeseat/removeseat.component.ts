import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-removeseat',
  templateUrl: './removeseat.component.html',
  styleUrls: ['./removeseat.component.css']
})
export class RemoveseatComponent implements OnInit {

  seat$ = {};

  constructor(
    private http: HttpClient,
    private route: ActivatedRoute,
    private _router: Router
  ) { }

  ngOnInit() {
    this.getSeat().subscribe(
      (data) => {
        this.seat$ = data;
        this.removeFromBasket().subscribe((seatData) => {
          this.seat$ = seatData;
          this._router.navigate([localStorage.getItem("basketPage")]);
        });
      });
  }

  getSeat() {
    const seatid = +this.route.snapshot.paramMap.get('seatid');
    return this.http.get('http://localhost:8080/api/seats/' + seatid);
  }

  removeFromBasket() {
    return this.http.post('http://localhost:8080/api/seatReservation/cancel', this.seat$);
  }
}
