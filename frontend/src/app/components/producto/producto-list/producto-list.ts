import { Component, inject, OnInit } from '@angular/core';
import { ProductoService } from '../../../service/producto-service';
import { Categoria } from '../../../enums/Categoria';
import { Producto } from '../../../interfaces/Producto';
import { FormsModule } from '@angular/forms';
import { ProductoEdit } from '../producto-edit/producto-edit';

@Component({
  imports: [FormsModule, ProductoEdit],
  selector: 'app-producto-list',
  styleUrl: './producto-list.css',
  templateUrl: './producto-list.html',
})
export class ProductoList implements OnInit{
  productoService = inject(ProductoService);

  categorias = Object.values(Categoria);

  searchId = 0;
  categoria: Categoria | '' = '';

  ngOnInit(): void {
    this.productoService.listProductos()
  }

  buscar(){
    this.productoService.getById(this.searchId)
  }

  buscarCategoria(){
    if(this.categoria === ''){
      this.productoService.listProductos(); //si la categoria esta vacia muestra todas
      return;
    }

    this.productoService.getByCategoria(this.categoria);
  }

  editar(p: Producto){
    this.productoService.productoById.set(p)
  }

  eliminar(id: number){
    this.productoService.remove(id);
  }

}
