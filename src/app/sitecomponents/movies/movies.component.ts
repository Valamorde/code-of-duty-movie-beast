import { Component, OnInit } from '@angular/core';
import { DataService } from '../../connection/data.service'
import { trigger, style, transition, animate, query, stagger } from '@angular/animations';
import { Movie } from '../../models/movie';

@Component({
  selector: 'app-movies',
  templateUrl: './movies.component.html',
  styleUrls: ['./movies.component.css'],

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

export class MoviesComponent implements OnInit {
  movies$: Movie[];

  constructor(private data: DataService) { }
  ngOnInit() {
    this.data.getMovies().subscribe(
      data => {
        this.movies$ = data as Movie[];
      }
    )


    // getVideos(id) {
    //   var vid = Number(id);
    // console.log(this.videos[vid].videoCode);
    // console.log("TEST");
    // return this.videos[vid].videoCode

  }
}