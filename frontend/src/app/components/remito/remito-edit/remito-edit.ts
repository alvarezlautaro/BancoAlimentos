import { Component, inject, OnInit } from '@angular/core';
import { RemitoService } from '../../../service/remito-service';
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { RemitoRequest } from '../../../interfaces/remito';
@Component({
  imports: [ReactiveFormsModule],
  selector: 'app-remito-edit',
  styleUrl: './remito-edit.css',
  templateUrl: './remito-edit.html',
})
export class RemitoEdit implements OnInit {
  ngOnInit(): void {
    const r = this.remitoService.remitoByID();
    if(r){
      this.formulario.patchValue(r);
    }
  }


  remitoService = inject(RemitoService);    
  fb: FormBuilder = inject(FormBuilder);  

  formulario = this.fb.group({
    fecha: [''],
    idInstitucion: [0, Validators.min(1)]
  });

  save(){
    if(this.formulario.invalid) return;  
      const remitoActual = this.remitoService.remitoByID();
      if(!remitoActual || !remitoActual.externalId) return;

    const remitoEditado: RemitoRequest = {
      fecha: this.formulario.controls.fecha.value || remitoActual.fecha,
      idInstitucion: this.formulario.controls.idInstitucion.value || remitoActual.idInstitucion
    };

    this.remitoService.update(remitoActual.externalId, remitoEditado);

    this.remitoService.remitoByID.set(null);
  }

  cancel(){
    this.remitoService.remitoByID.set(null);
  }
}
