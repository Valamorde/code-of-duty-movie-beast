import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


import {
    MatButtonModule,
    MatMenuModule,
    MatToolbarModule,
    MatIconModule,
    MatCardModule,
    MatFormFieldModule,
    MatProgressSpinnerModule ,
    MatInputModule
  } from '@angular/material';
  
  @NgModule({
    imports: [
      MatButtonModule,
      MatMenuModule,
      MatToolbarModule,
      MatIconModule,
      MatCardModule,
      MatFormFieldModule,
      MatProgressSpinnerModule ,
      MatInputModule
    ],
    exports: [
      MatButtonModule,
      MatMenuModule,
      MatToolbarModule,
      MatIconModule,
      MatCardModule,
      MatFormFieldModule,
      MatProgressSpinnerModule,
      MatInputModule 
    ]
  })
export class MaterialModule { }