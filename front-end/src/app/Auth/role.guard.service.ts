import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, Router} from "@angular/router";
import {AuthService} from "./auth.service";
import {JwtHelperService} from "@auth0/angular-jwt";


@Injectable()
export class RoleGuardService implements CanActivate {
  jwtHelper = new JwtHelperService();

  constructor(public auth: AuthService, public router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot): boolean {
    // this will be passed from the route config
    // on the data property
    const expectedRole = route.data.expectedRole;
    const token = localStorage.getItem("token");
    // decode the token to get its payload
    // const tokenPayload = jwt_decode(token);
    const tokenPayLoad = this.jwtHelper.decodeToken(token);
    // console.log(tokenPayLoad);
    if (
      tokenPayLoad.aud !== expectedRole
    ) {
      return false;
    }
    return true;
  }
}
