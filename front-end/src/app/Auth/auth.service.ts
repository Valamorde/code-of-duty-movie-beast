import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";

@Injectable()
export class AuthService {

  private _registerUrl = "http://localhost:8080/api/register";
  private _loginUrl = "http://localhost:8080/api/auth";
  private _movieUrl = "http://localhost:8080/api/movies";

  constructor(private http: HttpClient,
              private _router: Router) {
  }

  registerUser(user) {
    return this.http.post<any>(this._registerUrl, user);
  }

  loginUser(user) {
    return this.http.post<any>(this._loginUrl, user);
  }

  getToken() {
    return localStorage.getItem("token");
  }

  loggedIn() {
    return !!localStorage.getItem("token");
  }

  logoutUser() {
    this.postLogout().subscribe((res) => {
      localStorage.removeItem("token");
      this._router.navigate(["/login"]);
    });
  }

  postLogout() {
    return this.http.get<any>("http://localhost:8080/api/logoutUser");
  }

}
