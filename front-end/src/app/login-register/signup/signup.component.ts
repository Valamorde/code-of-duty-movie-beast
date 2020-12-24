import {Component, OnInit} from "@angular/core";
import {AuthService} from "../../Auth/auth.service";
import {Router} from "@angular/router";
import {HttpErrorResponse} from "@angular/common/http";
import {Message} from "primeng/components/common/api";


@Component({
  selector: "app-register",
  templateUrl: "./signup.component.html",
  styleUrls: ["./signup.component.css"]
})
export class SignupComponent implements OnInit {
  errorMessages: Message[] = [];
  registerUserData = {};

  constructor(private _auth: AuthService,
              private _router: Router) {
  }

  ngOnInit() {
  }

  registerUser() {
    this._auth.registerUser(this.registerUserData)
      .subscribe(
        (res) => {
          this._router.navigate(["/login"]);
        },
        (err) => {
          if (err instanceof HttpErrorResponse) {
            if (err.status === 409) {
              this.showError409();
            } else if (err.status === 400) {
              this.showError400();
            }
          }
        }
      );
  }

  navigate() {
    this._router.navigate(["/login"]);
  }

  showError409() {
    this.errorMessages.push({
      severity: "error",
      summary: "Email already exists.",
      detail: "Hmm, that email exists, try using another or login if it's yours."
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
