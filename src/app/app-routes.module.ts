import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MoviesComponent } from './sitecomponents/movies/movies.component';
import { TheatersComponent } from './sitecomponents/theaters/theaters.component';
import { BasketComponent } from './sitecomponents/basket/basket.component';
import { AuthGuard } from './Auth/auth.guard.service';
import { LoginComponent } from './login-register/login/login.component';
import { SignupComponent } from './login-register/signup/signup.component';
import { MovieandshowComponent } from './sitecomponents/movies/movieandshow/movieandshow.component';
import { SeatComponent } from './sitecomponents/movies/movieandshow/seat/seat.component';
import { UserComponent } from './login-register/user/user.component';
import { BookseatComponent } from './sitecomponents/movies/movieandshow/seat/bookseat/bookseat.component';
import { RemoveseatComponent } from "./sitecomponents/basket/removeseat/removeseat.component";
import { AdminComponent } from './login-register/admin/admin.component';
import { RoleGuardService as RoleGuard } from './Auth/role.guard.service';
import { CheckoutComponent } from "./sitecomponents/basket/checkout/checkout.component";
import { ManagecitiesComponent } from './login-register/admin/managecities/managecities.component';
import { ManagetheatersComponent } from './login-register/admin/managetheaters/managetheaters.component';
import { ManageshowsComponent } from './login-register/admin/manageshows/manageshows.component';
import { ManagebookingsComponent } from './login-register/admin/managebookings/managebookings.component';
import { ManageusersComponent } from './login-register/admin/manageusers/manageusers.component';
import { ManagemoviesComponent } from './login-register/admin/managemovies/managemovies.component';
import { ManageseatsComponent } from './login-register/admin/manageseats/manageseats.component';
import { BookingComponent } from './sitecomponents/booking/booking.component';
import { RemovebookingComponent } from './sitecomponents/booking/removebooking/removebooking.component';




const Routes: Routes = [
    { path: '', redirectTo: '/movies', pathMatch: 'full' },
    { path: 'movies', component: MoviesComponent, canActivate: [AuthGuard] },
    { path: 'theaters', component: TheatersComponent, canActivate: [AuthGuard] },
    { path: 'basket', component: BasketComponent, canActivate: [AuthGuard] },
    { path: 'login', component: LoginComponent },
    { path: 'signup', component: SignupComponent },
    { path: 'movieShow/:id', component: MovieandshowComponent, canActivate: [AuthGuard] },
    { path: 'show/:idshow', component: SeatComponent, canActivate: [AuthGuard] },
    { path: 'seat/:seatid', component: BookseatComponent, canActivate: [AuthGuard] },
    { path: "basket/:seatid", component: RemoveseatComponent, canActivate: [AuthGuard] },
    { path: "bookings", component: BookingComponent, canActivate: [AuthGuard] },
    { path: "rbooking/:bookingid", component: RemovebookingComponent, canActivate: [AuthGuard] },
    { path: 'checkout', component: CheckoutComponent, canActivate: [AuthGuard] },
    { path: 'user', component: UserComponent, canActivate: [AuthGuard] },
    {
        path: 'admin', component: AdminComponent, canActivate: [RoleGuard],
        data: { expectedRole: 'ROLE_ADMIN' }
    },
    {
        path: 'citiesmanagment', component: ManagecitiesComponent, canActivate: [RoleGuard],
        data: { expectedRole: 'ROLE_ADMIN' }
    },
    {
        path: 'showsmanagment', component: ManageshowsComponent, canActivate: [RoleGuard],
        data: { expectedRole: 'ROLE_ADMIN' }
    },
    {
        path: 'theatermanagment', component: ManagetheatersComponent, canActivate: [RoleGuard],
        data: { expectedRole: 'ROLE_ADMIN' }
    },
    {
        path: 'bookingmanagment', component: ManagebookingsComponent, canActivate: [RoleGuard],
        data: { expectedRole: 'ROLE_ADMIN' }
    },
    {
        path: 'usersmanagment', component: ManageusersComponent, canActivate: [RoleGuard],
        data: { expectedRole: 'ROLE_ADMIN' }
    },
    {
        path: 'moviesmanagment', component: ManagemoviesComponent, canActivate: [RoleGuard],
        data: { expectedRole: 'ROLE_ADMIN' }
    },
    {
        path: 'seatsmanagment', component: ManageseatsComponent, canActivate: [RoleGuard],
        data: { expectedRole: 'ROLE_ADMIN' }
    },



]
@NgModule({
    imports: [RouterModule.forRoot(Routes)],
    exports: [RouterModule]
})

export class AppRoutingModule {
}
