import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppComponent } from './app.component';
import { CitiesComponent } from './sitecomponents/cities/cities.component';
import { TheatersComponent } from './sitecomponents/theaters/theaters.component';
import { MoviesComponent } from './sitecomponents/movies/movies.component';
import { BasketComponent } from './sitecomponents/basket/basket.component';
import { ShowsComponent } from './sitecomponents/shows/shows.component';
import { LoginComponent } from './login-register/login/login.component';
import { SignupComponent } from './login-register/signup/signup.component';
import { NavbarComponent } from './sitecomponents/navbar/navbar.component';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routes.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AuthService } from './Auth/auth.service';
import { TokenInterceptorService } from './Auth/token-interceptor.service';
import { AuthGuard } from './Auth/auth.guard.service';
import { DataService } from './connection/data.service';
import { MovieandshowComponent } from './sitecomponents/movies/movieandshow/movieandshow.component';
import { SeatComponent } from './sitecomponents/movies/movieandshow/seat/seat.component';
import { UserComponent } from './login-register/user/user.component';
import { BookseatComponent } from './sitecomponents/movies/movieandshow/seat/bookseat/bookseat.component';
import { RemoveseatComponent } from './sitecomponents/basket/removeseat/removeseat.component';
import { AdminComponent } from './login-register/admin/admin.component';
import { RoleGuardService as RoleGuard } from './Auth/role.guard.service';
import { MessagesModule, GrowlModule } from 'primeng/primeng';
import { CheckoutComponent } from "./sitecomponents/basket/checkout/checkout.component";



@NgModule({
  declarations: [
    AppComponent,
    CitiesComponent,
    TheatersComponent,
    MoviesComponent,
    BasketComponent,
    ShowsComponent,
    LoginComponent,
    SignupComponent,
    NavbarComponent,
    MovieandshowComponent,
    SeatComponent,
    UserComponent,
    BookseatComponent,
    RemoveseatComponent,
    AdminComponent,
	  CheckoutComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MessagesModule, GrowlModule
  ],
  providers: [AuthService, AuthGuard, DataService, RoleGuard, {
    provide: HTTP_INTERCEPTORS,
    useClass: TokenInterceptorService,
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
