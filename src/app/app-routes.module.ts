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



const Routes: Routes = [
    { path: '*', redirectTo: '/login' },
    { path: 'Movies', component: MoviesComponent, canActivate: [AuthGuard] },
    { path: 'Cities', component: CitiesComponent, canActivate: [AuthGuard] },
    { path: 'Shows', component: ShowsComponent, canActivate: [AuthGuard] },
    { path: 'Theaters', component: TheatersComponent, canActivate: [AuthGuard] },
    { path: 'Basket', component: BasketComponent, canActivate: [AuthGuard] },
    { path: 'login', component: LoginComponent },
    { path: 'signup', component: SignupComponent },
    { path: 'movieShow/:id', component: MovieandshowComponent, canActivate: [AuthGuard] },
    { path: 'seat/:idseat', component: SeatComponent, canActivate: [AuthGuard] }
]



@NgModule({
    imports: [RouterModule.forRoot(Routes)],
    exports: [RouterModule]
})

export class AppRoutingModule {
}
