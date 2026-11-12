import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { ItemdonacionService } from '../../../service/itemdonacion-service';
import { ItemDonacionRequest } from '../../../interfaces/ItemDonacion';

@Component({
  imports: [ReactiveFormsModule],
  selector: 'app-item-donacion-edit',
  styleUrl: './item-donacion-edit.css',
  templateUrl: './item-donacion-edit.html',
})
export class ItemDonacionEdit implements OnInit{
  itemService = inject(ItemdonacionService);
  
  fb: FormBuilder = inject(FormBuilder);

  formulario = this.fb.nonNullable.group(
    {
      fechaVencimiento: [''],
      valorUnitario: [0],
      cantidad: [0],
      productoId: [0],
      donanteId: [0]
    }
  );

  ngOnInit(): void {
    const i = this.itemService.itemById();
    if(i){
      this.formulario.patchValue(i);
    }
  }

  save(){
    if(this.formulario.invalid) return;

    const itemActual = this.itemService.itemById();
    if(!itemActual || !itemActual.id) return;

    // FALTA QUE AGARRE LOS DATOS NUEVOS O USE LOS QUE YA ESTABAN EN EL ACTUAL
    const itemEditado: ItemDonacionRequest = {
      fechaVencimiento: this.formulario.controls.fechaVencimiento.value,
      cantidad: this.formulario.controls.cantidad.value || itemActual.cantidad,
      donanteId: this.formulario.controls.donanteId.value ,
      productoId: this.formulario.controls.productoId.value,
      valorUnitario: this.formulario.controls.valorUnitario.value
    }
  }

}
