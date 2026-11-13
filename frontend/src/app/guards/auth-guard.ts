import { inject } from "@angular/core";
import { CanActivateFn, Router } from "@angular/router";
import { AuthService } from "../service/auth-service";

export const authGuard: CanActivateFn = () => {
    const auth = inject(AuthService);
    return auth.isLoggedIn() ? true : inject(Router).createUrlTree(['/login']);
}

export const roleGuard: CanActivateFn = (route) => {
    const auth = inject(AuthService);
    const router = inject(Router);
    const required: string[] = route.data['roles'] ?? [];

    if(!auth.isLoggedIn()) return router.createUrlTree(['/login']);
    return auth.hasAnyRole(required) ? true : router.createUrlTree(['/forbidden']);
}

export const guestGuard: CanActivateFn = () => {
    const auth = inject(AuthService);
    return auth.isLoggedIn() ? inject(Router).createUrlTree(['/home']) : true;
}

export const authorityGuard: CanActivateFn = (route) => {
    const auth = inject(AuthService);
    const router = inject(Router);
    if (!auth.isLoggedIn()) return router.createUrlTree(['/login']);
    return auth.hasAuthority(route.data['authority']) ? true : router.createUrlTree(['/home']);
}