import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ProductoCreate } from './components/producto/producto-create/producto-create';
import { ProductoList } from './components/producto/producto-list/producto-list';

@Component({
  imports: [RouterOutlet, ProductoCreate, ProductoList],
  selector: 'app-root',
  styleUrl: './app.css',
  templateUrl: './app.html',
})
export class App {
  protected readonly title = signal('frontend');
}
