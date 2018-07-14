import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Seat } from '../models/seat';
import { Observable } from 'rxjs'; 

@Injectable({
  providedIn: 'root' 
})  

export class SeatsService {

  constructor(private http: HttpClient) { }

  getSeats() : Observable<Seat[]> {
    return this.http.get('http://localhost:8080/api/admin/users') as Observable <Seat[]>;
  }

  modifySeats(user: Seat){
      return this.http.put('http://localhost:8080/api/admin/users/' + user.userId , user)
  }

  deleteSeats(user: Seat){
      return this.http.delete('http://localhost:8080/api/admin/users/' + user.userId )
  }

}