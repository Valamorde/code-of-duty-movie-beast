import {Component, OnInit} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute} from "@angular/router";
import {Location} from "@angular/common";
import {animate, query, stagger, style, transition, trigger} from "@angular/animations";

@Component({
  selector: "app-seat",
  templateUrl: "./seat.component.html",
  styleUrls: ["./seat.component.css"],

  animations: [
    trigger("listStagger", [
      transition("* <=> *", [
        query(
          ":enter",
          [
            style({opacity: 0, transform: "translateY(-15px)"}),
            stagger(
              "50ms",
              animate(
                "550ms ease-out",
                style({opacity: 1, transform: "translateY(0px)"})
              )
            )
          ],
          {optional: true}
        ),
        query(":leave", animate("50ms", style({opacity: 0})), {
          optional: true
        })
      ])
    ])
  ]

})
export class SeatComponent implements OnInit {
  seats$: Object;


  constructor(
    private http: HttpClient,
    private route: ActivatedRoute,
    private location: Location
  ) {
  }

  ngOnInit() {
    this.getSeats().subscribe(
      (data) => {
        this.seats$ = data;
        this.rememberPage();
      }
    );
  }

  getSeats() {
    const idshow = +this.route.snapshot.paramMap.get("idshow");
    return this.http.get("http://localhost:8080/api/shows/" + idshow + "/seats");
  }

  rememberPage() {
    localStorage.setItem("currentShowPage", this.location.path());
  }


}
