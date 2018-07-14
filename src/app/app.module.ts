import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppComponent } from './app.component';
import { TheatersComponent } from './sitecomponents/theaters/theaters.component';
import { MoviesComponent } from './sitecomponents/movies/movies.component';
import { BasketComponent } from './sitecomponents/basket/basket.component';
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
import { ManagecitiesComponent } from './login-register/admin/managecities/managecities.component';
import { ManagetheatersComponent } from './login-register/admin/managetheaters/managetheaters.component';
import { ManageshowsComponent } from './login-register/admin/manageshows/manageshows.component';
import { ManagebookingsComponent } from './login-register/admin/managebookings/managebookings.component';
import { ManageusersComponent } from './login-register/admin/manageusers/manageusers.component';
import { ManagemoviesComponent } from './login-register/admin/managemovies/managemovies.component';
import { ManageseatsComponent } from './login-register/admin/manageseats/manageseats.component';
<<<<<<< Updated upstream
import {DropdownModule} from 'primeng/dropdown';
=======
import { BookingComponent } from './sitecomponents/booking/booking.component';
import { RemovebookingComponent } from './sitecomponents/booking/removebooking/removebooking.component';
>>>>>>> Stashed changes



@NgModule({
  declarations: [
    AppComponent,
        TheatersComponent,
    MoviesComponent,
    BasketComponent,
    LoginComponent,
    SignupComponent,
    NavbarComponent,
    MovieandshowComponent,
    SeatComponent,
    UserComponent,
    BookseatComponent,
    RemoveseatComponent,
    AdminComponent,
    CheckoutComponent,
    ManagecitiesComponent,
    ManagetheatersComponent,
    ManageshowsComponent,
    ManagebookingsComponent,
    ManageusersComponent,
    ManagemoviesComponent,
    ManageseatsComponent,
    BookingComponent,
    RemovebookingComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MessagesModule, 
    GrowlModule,
    DropdownModule
  ],
  providers: [AuthService, AuthGuard, DataService, RoleGuard, {
    provide: HTTP_INTERCEPTORS,
    useClass: TokenInterceptorService,
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
