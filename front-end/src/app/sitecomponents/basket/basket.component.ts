import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Location } from "@angular/common";
import { trigger, style, transition, animate, query, stagger } from '@angular/animations';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-basket',
  templateUrl: './basket.component.html',
  styleUrls: ['./basket.component.css'],

  animations: [
    trigger('listStagger', [
      transition('* <=> *', [
        query(
          ':enter',
          [
            style({ opacity: 0, transform: 'translateY(-15px)' }),
            stagger(
              '50ms',
              animate(
                '550ms ease-out',
                style({ opacity: 1, transform: 'translateY(0px)' })
              )
            )
          ],
          { optional: true }
        ),
        query(':leave', animate('50ms', style({ opacity: 0 })), {
          optional: true
        })
      ])
    ])
  ]

})
export class BasketComponent implements OnInit {
 
  basket$ = {};
  user$ = {};

  constructor(
    private http: HttpClient,
    private location: Location
  ) { }

  ngOnInit() {
    this.getUser().subscribe((data) => {
      this.user$ = data;
      this.getBasket().subscribe((basketData) => {
        this.basket$ = basketData;
        this.rememberPage();
      });
    });
  }

  getUser() {
    return this.http.get("http://localhost:8080/api/loggedUser");
  }

  getBasket() {
    let path = "http://localhost:8080/api/users/" + this.user$["userId"] + "/basket";
    return this.http.get(path);
  }

  rememberPage() {
    localStorage.setItem('basketPage', this.location.path());
  }
}
