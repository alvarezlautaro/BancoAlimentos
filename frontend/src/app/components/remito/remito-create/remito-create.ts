import { Component, inject, OnInit } from '@angular/core';
import { RemitoService } from '../../../service/remito-service';
import { FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RemitoRequest } from '../../../interfaces/remito';
import { InstitucionService } from '../../../services/instituciones/institucion-service';
import { Institucion } from '../../../models/institucion';
import { ItemdonacionService } from '../../../service/itemdonacion-service';
import { ItemDonacionResponse } from '../../../interfaces/ItemDonacion';

@Component({
  imports: [ReactiveFormsModule],
  selector: 'app-remito-create',
  styleUrl: './remito-create.css',
  templateUrl: './remito-create.html',
})
export class RemitoCreate implements OnInit  {
ngOnInit(): void {
    this.institucionService.obtenerInstituciones().subscribe({
      next: (data) => {
        this.instituciones = data;
      },
      error: (error) => {
        console.log(error);
      }
    });
    this.itemDonacionService.listItems();
  }
  remitoservice=inject(RemitoService);
  fb: FormBuilder = inject(FormBuilder);
  institucionService = inject(InstitucionService);
  itemDonacionService = inject(ItemdonacionService);

   instituciones: Institucion[] = [];
   get itemsDisponibles(): ItemDonacionResponse[] {
    return this.itemDonacionService.items();
   }

  formulario = this.fb.group({
    fecha: ['', Validators.required],
    idInstitucion: ['', Validators.required],
    items: this.fb.array([this.crearDetalle()])
  });

  get items(): FormArray {
    return this.formulario.controls.items;
  }

  private crearDetalle(): FormGroup {
    return this.fb.group({
      idItemDonacion: ['', Validators.required],
      cantidad: [1, [Validators.required, Validators.min(1)]]
    });
  }

  agregarItem(): void {
    this.items.push(this.crearDetalle());
  }

  eliminarItem(index: number): void {
    if (this.items.length > 1) {
      this.items.removeAt(index);
    }
  }


  add(){
    if(this.formulario.invalid) {
      this.formulario.markAllAsTouched();
      return;
    }

    const value = this.formulario.getRawValue();
    const r: RemitoRequest = {
      fecha: value.fecha!,
      idInstitucion: Number(value.idInstitucion),
      items: value.items.map(item => ({
        idItemDonacion: Number(item['idItemDonacion']),
        cantidad: Number(item['cantidad'])
      }))
    }

    this.remitoservice.crearRemito(r).subscribe({
      next: () => {
        this.formulario.reset({ fecha: '', idInstitucion: '' });
        this.items.clear();
        this.items.push(this.crearDetalle());
      },
      error: error => this.remitoservice.error.set('No se pudo crear el remito: ' + error.message)
    });
}
}