import { Component, inject, OnInit } from '@angular/core';
import { RemitoService } from '../../../service/remito-service';
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { RemitoRequest } from '../../../interfaces/remito';
import { InstitucionService } from '../../../services/instituciones/institucion-service';
import { Institucion } from '../../../models/institucion';
@Component({
  imports: [ReactiveFormsModule],
  selector: 'app-remito-edit',
  styleUrl: './remito-edit.css',
  templateUrl: './remito-edit.html',
})
export class RemitoEdit implements OnInit {
  ngOnInit(): void {
    this.institucionService.obtenerInstituciones().subscribe({
      next: instituciones => this.instituciones = instituciones,
      error: error => this.remitoService.error.set('No se pudieron cargar las instituciones: ' + error.message)
    });
    const r = this.remitoService.remitoByID();
    if(r){
      this.formulario.patchValue(r);
    }
  }


  remitoService = inject(RemitoService);    
  fb: FormBuilder = inject(FormBuilder);  
  institucionService = inject(InstitucionService);
  instituciones: Institucion[] = [];

  formulario = this.fb.group({
    fecha: [''],
    idInstitucion: [0, Validators.min(1)]
  });

  save(){
    if(this.formulario.invalid) return;  
      const remitoActual = this.remitoService.remitoByID();
      if(!remitoActual || remitoActual.id === undefined) return;

    const remitoEditado: RemitoRequest = {
      fecha: this.formulario.controls.fecha.value || remitoActual.fecha,
      idInstitucion: this.formulario.controls.idInstitucion.value || remitoActual.idInstitucion
    };

    this.remitoService.update(remitoActual.id, remitoEditado);

    this.remitoService.remitoByID.set(null);
  }

  cancel(){
    this.remitoService.remitoByID.set(null);
  }

  idValue(id?: string): number {
    return Number(id);
  }
}
