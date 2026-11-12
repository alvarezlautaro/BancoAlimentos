import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ItemDonacionCreate } from './components/itemdonacion/item-donacion-create/item-donacion-create';
import { ItemDonacionList } from './components/itemdonacion/item-donacion-list/item-donacion-list';
import { ProductoCreate } from './components/producto/producto-create/producto-create';
import { ProductoList } from './components/producto/producto-list/producto-list';
import { RemitoList } from './components/remito/remito-list/remito-list';

@Component({
  imports: [RouterOutlet, RemitoList],
  selector: 'app-root',
  styleUrl: './app.css',
  templateUrl: './app.html',
})
export class App {
  protected readonly title = signal('frontend');
}
