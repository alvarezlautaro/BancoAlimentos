import { Routes } from '@angular/router';
import { EmpresaDetail } from './pages/empresas/empresa-detail/empresa-detail';
import { EmpresaForm } from './pages/empresas/empresa-form/empresa-form';
import { EmpresaList } from './pages/empresas/empresa-list/empresa-list';
import { Home } from './pages/home/home';

export const routes: Routes = [
       ///falta agregar los guards para proteger las rutas segun el rol

    {path : '', redirectTo: 'home', pathMatch: 'full'},
    {path : 'home', component : Home},
    {path : 'empresas', component : EmpresaList},
    {path : 'empresas/form', component : EmpresaForm},
    {path : 'empresas/form/:id', component : EmpresaForm},
    {path : 'empresas/:id', component : EmpresaDetail},
/*     {path : 'instituciones', component : InstitucionList},
    {path : 'instituciones/form', component : InstitucionForm},
    {path : 'instituciones/form/:id', component : InstitucionForm},
    {path : 'instituciones/:id', component : InstitucionDetails}, */
    {path : '**', redirectTo : 'home'}
];
