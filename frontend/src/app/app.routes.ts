import { Routes } from '@angular/router';
import { authGuard, authorityGuard, guestGuard, roleGuard } from './guards/auth-guard';
import { Home } from './pages/home/home';
import { Historial } from './pages/auditoria/historial/historial';
import { Login } from './pages/login/login';
import { EmpresaDetail } from './pages/empresas/empresa-detail/empresa-detail';
import { EmpresaForm } from './pages/empresas/empresa-form/empresa-form';
import { EmpresaList } from './pages/empresas/empresa-list/empresa-list';
import { InstitucionDetail } from './pages/instituciones/institucion-detail/institucion-detail';
import { InstitucionList } from './pages/instituciones/institucion-list/institucion-list';
import { InstitucionForm } from './pages/instituciones/intitucion-form/intitucion-form';
import { DonacionDetails } from './components/donacion/donacion-details/donacion-details';
import { DonacionForm } from './components/donacion/donacion-form/donacion-form';
import { DonacionList } from './components/donacion/donacion-list/donacion-list';
import { FacturaList } from './components/factura/factura-list/factura-list';
import { ItemDonacionList } from './components/itemdonacion/item-donacion-list/item-donacion-list';
import { ProductoCreate } from './components/producto/producto-create/producto-create';
import { ProductoList } from './components/producto/producto-list/producto-list';
import { RemitoCreate } from './components/remito/remito-create/remito-create';
import { RemitoList } from './components/remito/remito-list/remito-list';

const deposito = ['USER_DEPOSITO'];
const instituciones = ['USER_DEPOSITO', 'USER_INSTITUCIONAL', 'USER_TESORERIA'];
const administracionInstituciones = ['USER_INSTITUCIONAL', 'USER_TESORERIA'];

export const routes: Routes = [
    { path: 'login', component: Login, canActivate: [guestGuard] },
    { path: 'home', component: Home, canActivate: [authGuard] },
    { path: '', pathMatch: 'full', redirectTo: 'home' },

    { path: 'empresas', component: EmpresaList, canActivate: [roleGuard], data: { roles: deposito } },
    { path: 'empresas/form', component: EmpresaForm, canActivate: [roleGuard], data: { roles: deposito } },
    { path: 'empresas/form/:id', component: EmpresaForm, canActivate: [roleGuard], data: { roles: deposito } },
    { path: 'empresas/:id', component: EmpresaDetail, canActivate: [roleGuard], data: { roles: deposito } },

    { path: 'donaciones', component: DonacionList, canActivate: [roleGuard], data: { roles: deposito } },
    { path: 'donaciones/form', component: DonacionForm, canActivate: [roleGuard], data: { roles: deposito } },
    { path: 'donaciones/form/:id', component: DonacionForm, canActivate: [roleGuard], data: { roles: deposito } },
    { path: 'donaciones/:id', component: DonacionDetails, canActivate: [roleGuard], data: { roles: deposito } },

    { path: 'itemdonacion', component: ItemDonacionList, canActivate: [roleGuard], data: { roles: deposito } },
    { path: 'productos', component: ProductoList, canActivate: [roleGuard], data: { roles: deposito } },
    { path: 'productos/form', component: ProductoCreate, canActivate: [roleGuard], data: { roles: deposito } },
    { path: 'facturas', component: FacturaList, canActivate: [roleGuard], data: { roles: deposito } },
    { path: 'remitos', component: RemitoList, canActivate: [roleGuard], data: { roles: deposito } },
    { path: 'remitos/crear', component: RemitoCreate, canActivate: [roleGuard], data: { roles: deposito } },

    { path: 'instituciones', component: InstitucionList, canActivate: [roleGuard], data: { roles: instituciones } },
    { path: 'instituciones/form', component: InstitucionForm, canActivate: [roleGuard], data: { roles: ['USER_INSTITUCIONAL'] } },
    { path: 'instituciones/form/:id', component: InstitucionForm, canActivate: [roleGuard], data: { roles: administracionInstituciones } },
    { path: 'instituciones/:id', component: InstitucionDetail, canActivate: [roleGuard], data: { roles: instituciones } },

    { path: 'auditoria/donantes/:id', component: Historial, canActivate: [authorityGuard], data: { entidad: 'donantes', authority: 'AUDITORIA_DONACION_VER' } },
    { path: 'auditoria/instituciones/:id', component: Historial, canActivate: [authorityGuard], data: { entidad: 'instituciones', authority: 'AUDITORIA_INSTITUCION_VER' } },
    { path: '**', redirectTo: 'home' },
];
