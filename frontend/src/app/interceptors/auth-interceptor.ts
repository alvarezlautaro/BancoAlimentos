import { HttpErrorResponse, HttpInterceptorFn } from "@angular/common/http";
import { inject } from "@angular/core";
import { AuthService } from "../service/auth-service";
import { catchError, throwError } from "rxjs";

export const authInterceptor: HttpInterceptorFn = (req, next) => {
    const auth = inject(AuthService);
    const token = auth.token;

    if(token && !req.url.includes('/api/auth/')) {
        req = req.clone({ setHeaders: { Authorization: `Bearer ${token}` } });
    }

    return next(req).pipe(
        catchError((err: HttpErrorResponse) => {
            if(err.status === 401 && !req.url.includes('/api/auth/')) auth.logout();
            return throwError(() => err);
        })
    )
}