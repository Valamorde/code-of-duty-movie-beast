import { Component, OnInit } from '@angular/core';
import { DataService } from '../../connection/data.service'
import { Observable } from 'rxjs'; 
import { trigger,style,transition,animate,keyframes,query,stagger } from '@angular/animations';
import { HttpClient } from '@angular/common/http'
import { getComponentViewDefinitionFactory } from '@angular/core/src/view';
import { NgModule } from '@angular/core';


@Component({
  selector: 'app-shows',
  templateUrl: './shows.component.html',
  styleUrls: ['./shows.component.css'],

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


export class ShowsComponent implements OnInit {
  
  constructor(private data: DataService) { }

  ngOnInit() {

}

  
}