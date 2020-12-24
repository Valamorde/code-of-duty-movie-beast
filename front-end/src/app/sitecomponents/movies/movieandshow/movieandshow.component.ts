import {Component, OnInit} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute} from "@angular/router";
import {animate, query, stagger, style, transition, trigger} from "@angular/animations";
import {show} from "../../../models/show";
import {Movie} from "../../../models/movie";


@Component({
  selector: "app-movieandshow",
  templateUrl: "./movieandshow.component.html",
  styleUrls: ["./movieandshow.component.css"],

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

export class MovieandshowComponent implements OnInit {
  shows$: show[];
  movie$: Movie[];

  constructor(private http: HttpClient, private route: ActivatedRoute,) {
  }

  ngOnInit() {
    this.getShows().subscribe(
      data => {
        this.shows$ = data as show[];
      }
    );
    // this.getSingleMovie().subscribe(
    //   moviedata => { this.movie$ = moviedata as movie[] }
    // )
  }

  // getSingleMovie() {
  //   const id = +this.route.snapshot.paramMap.get('id');
  //   return this.http.get('http://localhost:8080/api/movies/' + id)

  // }

  getShows() {
    const id = +this.route.snapshot.paramMap.get("id");
    return this.http.get("http://localhost:8080/api/movies/" + id + "/shows");
  }
}
