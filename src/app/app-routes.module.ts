import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MoviesComponent } from './sitecomponents/movies/movies.component';
import { CitiesComponent } from './sitecomponents/cities/cities.component';
import { ShowsComponent } from './sitecomponents/shows/shows.component';
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


const Routes: Routes = [
    { path: '', redirectTo: '/movies', pathMatch: 'full' },
    { path: 'movies', component: MoviesComponent, canActivate: [AuthGuard] },
    { path: 'cities', component: CitiesComponent, canActivate: [AuthGuard] },
    { path: 'shows', component: ShowsComponent, canActivate: [AuthGuard] },
    { path: 'theaters', component: TheatersComponent, canActivate: [AuthGuard] },
    { path: 'basket', component: BasketComponent, canActivate: [AuthGuard] },
    { path: 'login', component: LoginComponent },
    { path: 'signup', component: SignupComponent },
    { path: 'movieShow/:id', component: MovieandshowComponent, canActivate: [AuthGuard] },
    { path: 'show/:idshow', component: SeatComponent, canActivate: [AuthGuard] },
    { path: 'seat/:seatid', component: BookseatComponent, canActivate: [AuthGuard] },
    { path: "basket/:seatid", component: RemoveseatComponent, canActivate: [AuthGuard] },
	{ path: 'checkout', component: CheckoutComponent, canActivate: [AuthGuard] },
    { path: 'user', component: UserComponent, canActivate: [AuthGuard] },
    {
        path: 'admin', component: AdminComponent, canActivate: [RoleGuard],
        data: {
            expectedRole: 'ROLE_ADMIN'
        },
    }
]
@NgModule({
    imports: [RouterModule.forRoot(Routes)],
    exports: [RouterModule]
})

export class AppRoutingModule {
}
