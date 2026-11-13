import { Routes } from '@angular/router';
import { Login } from './pages/login/login';
import { authGuard, authorityGuard, guestGuard } from './guards/auth-guard';
import { Home } from './pages/home/home';
import { Historial } from './pages/auditoria/historial/historial';

export const routes: Routes = [
    { path: 'login', component: Login, canActivate: [guestGuard] },
    { path: 'home', component: Home, canActivate: [authGuard] },
    { path: '', pathMatch: 'full', redirectTo: 'home' },
    { path: 'auditoria/donantes/:id', component: Historial, canActivate: [authorityGuard], data: { entidad: 'donantes', authority: 'AUDITORIA_DONACION_VER' } },
    { path: 'auditoria/instituciones/:id', component: Historial, canActivate: [authorityGuard], data: { entidad: 'instituciones', authority: 'AUDITORIA_INSTITUCION_VER' } },
    { path: '**', redirectTo: 'home' },

];
