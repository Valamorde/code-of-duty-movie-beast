
import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../Auth/auth.service';
import { Router } from '@angular/router'
import { HttpErrorResponse } from '@angular/common/http';
import { MessagesModule } from 'primeng/messages';
import { MessageModule } from 'primeng/message';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {

  loginUserData = {};
  errorMessages = [];

  constructor(
    private _auth: AuthService,
    private _router: Router
  ) { }

  ngOnInit() {
  }

  loginUser() {
    this._auth.loginUser(this.loginUserData)
      .subscribe(
        (res) => {
          localStorage.setItem('token', res.token)
          this._router.navigate(['/movies'])
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
    this._router.navigate(['/signup'])
  }

  showError() {
    this.errorMessages.push({ severity: 'error', summary: 'Bad Credentials', detail: 'Woops! Wrong username/password. Check for typos and give it another try!' });
  }

  clearError() {
    this.errorMessages = [];
  }

}