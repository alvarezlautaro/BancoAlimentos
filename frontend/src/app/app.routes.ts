import { Routes } from '@angular/router';
import { Login } from './pages/login/login';
import { authGuard, guestGuard } from './guards/auth-guard';
import { Home } from './pages/home/home';

export const routes: Routes = [
    {path: '', component: Login, canActivate: [guestGuard]},
    { path: 'home', component: Home, canActivate: [authGuard] },


];
