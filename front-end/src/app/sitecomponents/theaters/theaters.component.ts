import {Component, OnInit} from "@angular/core";
import {DataService} from "../../connection/data.service";
import {animate, query, stagger, style, transition, trigger} from "@angular/animations";


@Component({
  selector: "app-theaters",
  templateUrl: "./theaters.component.html",
  styleUrls: ["./theaters.component.css"],
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
export class TheatersComponent implements OnInit {
  theaters$: Object;

  constructor(private data: DataService) {
  }

  ngOnInit() {
    this.data.getTheaters().subscribe(
      data => this.theaters$ = data
    );


  }
}
