import { Component, OnInit } from '@angular/core';
import { Movie } from '../../../models/movie';
import { MovieService } from '../../../connection/movie.service';

@Component({
  selector: 'app-managemovies',
  templateUrl: './managemovies.component.html',
  styleUrls: ['./managemovies.component.css']
})
export class ManagemoviesComponent implements OnInit {

  newMovie = new Movie();

  constructor(private movieService: MovieService) {

  }

  ngOnInit() {

  }


  createNewMovie() {
    return this.movieService.createMovie(this.newMovie)
      .subscribe((res) => {
        console.log(res);
      })
  }

}
