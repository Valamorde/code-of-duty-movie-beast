import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {User} from "../models/user";
import {Observable} from "rxjs";

@Injectable({
  providedIn: "root"
})

export class UserService {

  constructor(private http: HttpClient) {
  }

  getUsers(): Observable<User[]> {
    return this.http.get("http://localhost:8080/api/admin/users") as Observable<User[]>;
  }

  resetPassword(user: User) {
    return this.http.put("http://localhost:8080/api/admin/users/" + user.userId + "/passwordReset", {});
  }

  modifyUser(user: User) {
    return this.http.put("http://localhost:8080/api/admin/users/" + user.userId, user);
  }

  deleteUser(user: User) {
    return this.http.delete("http://localhost:8080/api/admin/users/" + user.userId);
  }

}
