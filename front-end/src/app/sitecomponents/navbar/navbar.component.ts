import {Component, OnInit} from "@angular/core";
import {AuthService} from "../../Auth/auth.service";
import {Router} from "@angular/router";


@Component({
  selector: "app-navbar",
  templateUrl: "./navbar.component.html",
  styleUrls: ["./navbar.component.css"]
})
export class NavbarComponent implements OnInit {

  constructor(private _authService: AuthService, private _router: Router) {
  }

  ngOnInit() {
  }

}
