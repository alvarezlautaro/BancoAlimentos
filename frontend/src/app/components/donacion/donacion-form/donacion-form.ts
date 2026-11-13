import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { DonacionService } from '../../../service/donacion-service';
import { Estado } from '../../../enums/Estado';
import { EstadoDonacion } from '../../../enums/EstadoDonacion';
import { Categoria } from '../../../enums/Categoria';
import { UnidadMedida } from '../../../enums/UnidadMedida';
import { ProductoService } from '../../../service/producto-service';
import { ProductoResponse } from '../../../interfaces/Producto';
import { EmpresaService } from '../../../services/empresas/empresa';
import { Empresa } from '../../../models/empresa';

@Component({
  imports: [RouterLink, CommonModule, ReactiveFormsModule],
  selector: 'app-donacion-form',
  styleUrl: './donacion-form.css',
  templateUrl: './donacion-form.html',
})
export class DonacionForm implements OnInit{
  formulario!: FormGroup;
  id?: string;
  donantes: Empresa[] = [];
  productos: ProductoResponse[] = [];
  categorias = Object.values(Categoria);
  unidades = Object.values(UnidadMedida);

  constructor(
    private fb: FormBuilder,
    private donacionService: DonacionService,
    private route: ActivatedRoute,
    private router: Router,
    private empresaService: EmpresaService,
    private productoService: ProductoService
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

    this.empresaService.obtenerEmpresas().subscribe({
      next: donantes => this.donantes = donantes,
      error: error => alert('No se pudieron cargar los donantes: ' + error.message)
    });
    this.productoService.obtenerProductos().subscribe({
      next: productos => this.productos = productos,
      error: error => alert('No se pudieron cargar los productos: ' + error.message)
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
      productoNombre: [''],
      categoria: ['' as Categoria | '',],
      unidadMedida: ['' as UnidadMedida | ''],
      cantidad: ['', [Validators.required, Validators.min(1)]],
      valorUnitario: ['', [Validators.required, Validators.min(0)]],
      fechaVencimiento: ['', Validators.required]
    });
    this.items.push(itemForm);
  }

  agregarItemExistente(item: any) {
    const itemForm = this.fb.group({
      productoId: [item.productoId, Validators.required],
      productoNombre: [''],
      categoria: ['' as Categoria | ''],
      unidadMedida: ['' as UnidadMedida | ''],
      cantidad: [item.cantidad, [Validators.required, Validators.min(1)]],
      valorUnitario: [item.valorUnitario, [Validators.required, Validators.min(0)]],
      fechaVencimiento: [item.fechaVencimiento, Validators.required]
    });
    this.items.push(itemForm);
  }

  onProductoChange(index: number): void {
    const item = this.items.at(index) as FormGroup;
    const esNuevo = item.get('productoId')?.value === 'nuevo';
    for (const controlName of ['productoNombre', 'categoria', 'unidadMedida']) {
      const control = item.get(controlName)!;
      control.setValidators(esNuevo ? Validators.required : null);
      control.updateValueAndValidity();
    }
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

    const valor = this.formulario.getRawValue() as {
      fecha: string;
      estado: EstadoDonacion;
      nroRemitoProveedor: string | number;
      observaciones: string;
      idDonante: string | number;
      items: Array<{
        productoId: string | number;
        productoNombre: string;
        categoria: Categoria | '';
        unidadMedida: UnidadMedida | '';
        cantidad: string | number;
        valorUnitario: string | number;
        fechaVencimiento: string;
      }>;
    };
    const donacionData = {
      ...valor,
      nroRemitoProveedor: Number(valor.nroRemitoProveedor),
      idDonante: Number(valor.idDonante),
      items: valor.items.map(item => item.productoId === 'nuevo'
        ? {
            cantidad: Number(item.cantidad),
            valorUnitario: Number(item.valorUnitario),
            fechaVencimiento: item.fechaVencimiento,
            producto: {
              nombre: item.productoNombre,
              categoria: item.categoria,
              unidadMedida: item.unidadMedida
            }
          }
        : {
            cantidad: Number(item.cantidad),
            valorUnitario: Number(item.valorUnitario),
            fechaVencimiento: item.fechaVencimiento,
            productoId: Number(item.productoId)
          })
    };

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
