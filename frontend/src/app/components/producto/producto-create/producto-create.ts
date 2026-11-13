import { Component, inject } from '@angular/core';
import { ProductoService } from '../../../service/producto-service';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { UnidadMedida } from '../../../enums/UnidadMedida';
import { Categoria } from '../../../enums/Categoria';
import { ProductoRequest } from '../../../interfaces/Producto';

@Component({
  imports: [ReactiveFormsModule],
  selector: 'app-producto-create',
  styleUrl: './producto-create.css',
  templateUrl: './producto-create.html',
})
export class ProductoCreate {
  productoService = inject(ProductoService);

  fb: FormBuilder = inject(FormBuilder);

  categorias = Object.values(Categoria);
  unidades = Object.values(UnidadMedida);

  formulario = this.fb.nonNullable.group(
    {
      nombre: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(55)]],
      categoria: ['' as Categoria, Validators.required],
      unidadMedida: ['' as UnidadMedida, Validators.required]
    }
  )

  add(){
    if(this.formulario.invalid) return;

    const p: ProductoRequest = {
      nombre: this.formulario.controls.nombre.value,
      categoria: this.formulario.controls.categoria.value as Categoria,
      unidadMedida: this.formulario.controls.unidadMedida.value as UnidadMedida
    };

    this.productoService.crearProducto(p).subscribe({
      next: () => this.formulario.reset(),
      error: error => this.productoService.error.set('Error al crear el producto: ' + error.message)
    });
  }
}
