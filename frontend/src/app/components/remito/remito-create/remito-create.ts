import { Component, inject, OnInit } from '@angular/core';
import { RemitoService } from '../../../service/remito-service';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { RemitoRequest } from '../../../interfaces/remito';
import { InstitucionService } from '../../../services/instituciones/institucion-service';
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
  }
  remitoservice=inject(RemitoService);
  fb: FormBuilder = inject(FormBuilder);
  institucionService = inject(InstitucionService);

   instituciones: any[] = [];

  formulario= this.fb.nonNullable.group(
    {
      fecha: ['',[Validators.required]],
      idInstitucion: ['',[Validators.required]],
    }
  )


  add(){
    if(this.formulario.invalid) return;   

    const r: RemitoRequest = {
      fecha: this.formulario.controls.fecha.value,
      idInstitucion: Number(this.formulario.controls.idInstitucion.value)
    }

    this.remitoservice.add(r);
    this.formulario.reset();
}
}