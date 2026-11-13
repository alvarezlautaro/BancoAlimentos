import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { DonacionService } from '../../../service/donacion-service';
import { Estado } from '../../../enums/Estado';
import { EstadoDonacion } from '../../../enums/EstadoDonacion';

@Component({
  imports: [RouterLink, CommonModule, ReactiveFormsModule],
  selector: 'app-donacion-form',
  styleUrl: './donacion-form.css',
  templateUrl: './donacion-form.html',
})
export class DonacionForm implements OnInit{
  formulario!: FormGroup;
  id?: string;

  constructor(
    private fb: FormBuilder,
    private donacionService: DonacionService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];

    this.formulario = this.fb.group({
      fecha: ['', Validators.required],
      ////Se inicializa pendiente
      estado: [EstadoDonacion.PENDIENTE, Validators.required],
      nroRemitoProveedor: ['', [Validators.required, Validators.pattern(/^[0-9]+$/)]],
      observaciones: [''],
      idDonante: ['', Validators.required],
      items: this.fb.array([], Validators.required)
    });

    if (this.id) {
      this.donacionService.obtenerDonacionPorId(this.id).subscribe({
        next: (donacion) => {
          this.formulario.patchValue({
            fecha: donacion.fecha,
            estado: donacion.estado,
            nroRemitoProveedor: donacion.nroRemitoProveedor,
            observaciones: donacion.observaciones,
            idDonante: donacion.idDonante
          });

          // Cargar items existentes
          if (donacion.items) {
            donacion.items.forEach(item => {
              this.agregarItemExistente(item);
            });
          }
        },
        error: (e) => {
          console.log(e);
          alert('Error al cargar la donación para editar.');
        }
      });
    } else {
      this.agregarItem();
    }
  }

  get items(): FormArray {
    return this.formulario.get('items') as FormArray;
  }

  agregarItem() {
    const itemForm = this.fb.group({
      productoId: ['', Validators.required],
      cantidad: ['', [Validators.required, Validators.min(1)]],
      valorUnitario: ['', [Validators.required, Validators.min(0)]],
      fechaVencimiento: ['', Validators.required]
    });
    this.items.push(itemForm);
  }

  agregarItemExistente(item: any) {
    const itemForm = this.fb.group({
      productoId: [item.productoId, Validators.required],
      cantidad: [item.cantidad, [Validators.required, Validators.min(1)]],
      valorUnitario: [item.valorUnitario, [Validators.required, Validators.min(0)]],
      fechaVencimiento: [item.fechaVencimiento, Validators.required]
    });
    this.items.push(itemForm);
  }

  eliminarItem(index: number) {
    if (this.items.length > 1) {
      this.items.removeAt(index);
    } else {
      alert('La donación debe tener al menos un ítem.');
    }
  }

  onSubmit() {
    if (this.formulario.invalid) {
      this.formulario.markAllAsTouched();
      return;
    }

    const donacionData = this.formulario.value;

    if (this.id) {
      this.donacionService.editarDonacion(this.id, donacionData).subscribe({
        next: () => {
          alert('Donación actualizada correctamente');
          this.router.navigate(['/donaciones']);
        },
        error: (e) => {
          console.log(e);
          alert('Error al actualizar la donación');
        }
      });
    } else {
      this.donacionService.crearDonacion(donacionData).subscribe({
        next: () => {
          alert('Donación creada correctamente');
          this.router.navigate(['/donaciones']);
        },
        error: (e) => {
          console.log(e);
          alert('Error al crear la donación');
        }
      });
    }
  }
}
