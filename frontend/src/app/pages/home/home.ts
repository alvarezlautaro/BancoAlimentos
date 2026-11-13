import { Component, inject } from '@angular/core';
import { ProductoCreate } from '../../components/producto/producto-create/producto-create';
import { ProductoList } from '../../components/producto/producto-list/producto-list';
import { ItemDonacionCreate } from '../../components/itemdonacion/item-donacion-create/item-donacion-create';
import { ItemDonacionList } from '../../components/itemdonacion/item-donacion-list/item-donacion-list';
import { EmpresaDetail } from '../empresas/empresa-detail/empresa-detail';
import { EmpresaForm } from '../empresas/empresa-form/empresa-form';
import { EmpresaList } from '../empresas/empresa-list/empresa-list';
import { InstitucionList } from '../instituciones/institucion-list/institucion-list';
import { InstitucionForm } from '../instituciones/intitucion-form/intitucion-form';
import { InstitucionDetail } from '../instituciones/institucion-detail/institucion-detail';
import { DonacionList } from '../../components/donacion/donacion-list/donacion-list';
import { DonacionForm } from '../../components/donacion/donacion-form/donacion-form';
import { DonacionDetails } from '../../components/donacion/donacion-details/donacion-details';
import { RemitoCreate } from '../../components/remito/remito-create/remito-create';
import { RemitoList } from '../../components/remito/remito-list/remito-list';
import { RemitoEdit } from '../../components/remito/remito-edit/remito-edit';
import { FacturaList } from '../../components/factura/factura-list/factura-list';
import { FacturaEdit } from '../../components/factura/factura-edit/factura-edit';
import { AuthService } from '../../service/auth-service';

@Component({
  imports: [ProductoCreate, ProductoList, ItemDonacionCreate, ItemDonacionList, EmpresaDetail, EmpresaForm, EmpresaList, InstitucionList, InstitucionForm, InstitucionDetail, DonacionList, DonacionForm, DonacionDetails, RemitoCreate, RemitoList, RemitoEdit, FacturaList, FacturaEdit],
  selector: 'app-home',
  styleUrl: './home.css',
  templateUrl: './home.html',
})
export class Home {
  auth = inject(AuthService);
}
