import { Component, OnInit } from '@angular/core';
import { DataService } from '../../connection/data.service'
import { Observable } from 'rxjs'; 
import { trigger,style,transition,animate,keyframes,query,stagger } from '@angular/animations';


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
  movies$: Object;
  videos = ['http://www.youtube.com/watch?v=aDshY43Ol2U','https://www.youtube.com/watch?v=kPpXFnHoC-0','https://www.youtube.com/watch?v=xjDjIWPwcPU','https://www.youtube.com/watch?v=AlrWRttLTkg','https://www.youtube.com/watch?v=89OP78l9oF0' ]

  constructor(private data: DataService) { }

  ngOnInit() {
    this.data.getMovies().subscribe(
      data => this.movies$ = data
    )
   
  //   this.videos = [
  //     new Video(1, 'http://www.youtube.com/watch?v=aDshY43Ol2U'),
  //     new Video(2, 'https://www.youtube.com/watch?v=kPpXFnHoC-0'),
  //     new Video(3, 'https://www.youtube.com/watch?v=xjDjIWPwcPU'),
  //     new Video(4, 'https://www.youtube.com/watch?v=AlrWRttLTkg'),
  //     new Video(5, 'https://www.youtube.com/watch?v=89OP78l9oF0'),
  // ]}
  

// getVideos(id){
// id==this.videos[id];
// return this.videos[id].videoCode;
// console.log("TEST");
// console.log (this.videos[id].videoCode);
}

  
}