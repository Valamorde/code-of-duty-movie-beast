import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../Auth/auth.service';
import { Router } from '@angular/router'
import { HttpErrorResponse } from '@angular/common/http';
import { MessagesModule } from 'primeng/messages';
import { MessageModule } from 'primeng/message';

@Component({
  selector: 'app-register',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  errorMessages = [];
  registerUserData = {}
  constructor(private _auth: AuthService,
    private _router: Router) { }

  ngOnInit() {
  }

  registerUser() {
    this._auth.registerUser(this.registerUserData)
      .subscribe(
        (res) => {
          this._router.navigate(['/login'])
        },
        (err) => {
          if (err instanceof HttpErrorResponse) {
            if (err.status === 401) {
              this.showError();
            }
          }
        }
      )
  }

  navigate() {
    this._router.navigate(['/login'])
  }

  showError() {
    this.errorMessages.push({ severity: 'error', summary: 'Email already exists.', detail: "Hmm, that email exists, try using another or login if it's yours." });
  }

  clearError() {
    this.errorMessages = [];
  }

}
