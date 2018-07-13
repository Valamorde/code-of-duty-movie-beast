import { Component, OnInit } from '@angular/core';
import { DataService } from '../../connection/data.service'
import { trigger, style, transition, animate, keyframes, query, stagger } from '@angular/animations';
import { city } from '../../models/city';



@Component({
  selector: 'app-cities',
  templateUrl: './cities.component.html',
  styleUrls: ['./cities.component.css'],


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

export class CitiesComponent implements OnInit {

  cities$: city[];

  constructor(private data: DataService) { }

  ngOnInit() {
    this.data.getCities().subscribe(
      data => {
        this.cities$ = data as city[]
      }
    )
  }

}