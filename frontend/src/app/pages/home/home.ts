import { Component } from '@angular/core';
import { ProductoCreate } from '../../components/producto/producto-create/producto-create';
import { ProductoList } from '../../components/producto/producto-list/producto-list';
import { ItemDonacionCreate } from '../../components/itemdonacion/item-donacion-create/item-donacion-create';
import { ItemDonacionList } from '../../components/itemdonacion/item-donacion-list/item-donacion-list';

@Component({
  imports: [ProductoCreate, ProductoList, ItemDonacionCreate, ItemDonacionList],
  selector: 'app-home',
  styleUrl: './home.css',
  templateUrl: './home.html',
})
export class Home {}
