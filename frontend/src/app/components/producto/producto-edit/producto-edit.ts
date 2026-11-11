import { Component, inject, OnInit } from '@angular/core';
import { ProductoService } from '../../../service/producto-service';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Categoria } from '../../../enums/Categoria';
import { UnidadMedida } from '../../../enums/UnidadMedida';
import { Producto } from '../../../interfaces/Producto';

@Component({
  imports: [ReactiveFormsModule],
  selector: 'app-producto-edit',
  styleUrl: './producto-edit.css',
  templateUrl: './producto-edit.html',
})
export class ProductoEdit implements OnInit {
  productoService = inject(ProductoService);

  fb: FormBuilder = inject(FormBuilder);

  categorias = Object.values(Categoria);
  unidades = Object.values(UnidadMedida);

  formulario = this.fb.group(
    {
      nombre: ['', Validators.maxLength(55)],
      categoria: ['' as Categoria],
      unidadMedida: ['' as UnidadMedida]
    }
  )

  ngOnInit(): void {
    const p = this.productoService.productoById();
    if(p){
      this.formulario.patchValue(p);
    }
  }

  save(){ 
    if(this.formulario.invalid) return;

    const productoActual = this.productoService.productoById();
    if(!productoActual || !productoActual.id) return ;

    const productoEditado: Producto = {
      nombre: this.formulario.controls.nombre.value?.trim() || productoActual.nombre,
      categoria: this.formulario.controls.categoria.value as Categoria || productoActual.categoria,
      unidadMedida: this.formulario.controls.unidadMedida.value as UnidadMedida || productoActual.unidadMedida
    };

    this.productoService.update(productoActual.id, productoEditado);

    this.productoService.productoById.set(null);
  }

  cancel(){
    this.productoService.productoById.set(null);
  }
}
