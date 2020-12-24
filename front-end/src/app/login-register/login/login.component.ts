import {Component, OnInit} from "@angular/core";
import {AuthService} from "../../Auth/auth.service";
import {Router} from "@angular/router";
import {HttpErrorResponse} from "@angular/common/http";
import {Message} from "primeng/components/common/api";


@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.css"],
})
export class LoginComponent implements OnInit {

  loginUserData = {};
  errorMessages: Message[] = [];

  constructor(
    private _auth: AuthService,
    private _router: Router
  ) {
  }

  ngOnInit() {
  }

  loginUser() {
    this._auth.loginUser(this.loginUserData)
      .subscribe(
        (res) => {
          localStorage.setItem("token", res.token);
          this._router.navigate(["/movies"]);
        },
        (err) => {
          if (err instanceof HttpErrorResponse) {
            if (err.status === 401) {
              this.showError401();
            } else if (err.status === 400) {
              this.showError400();
            }
          }
        }
      );
  }

  navigate() {
    this._router.navigate(["/signup"]);
  }

  showError401() {
    this.errorMessages.push({
      severity: "error",
      summary: "Bad Credentials",
      detail: "Woops! Wrong username/password. Check for typos and give it another try!"
    });
  }

  showError400() {
    this.errorMessages.push({
      severity: "error",
      summary: "Bad Request",
      detail: "Woops! Something went wrong. Do you speak Gibberish? Our servers only speak JSON. Check your details and try again."
    });
  }

  clearError() {
    this.errorMessages = [];
  }

}
