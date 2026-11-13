import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { InstitucionService } from '../../../services/instituciones/institucion-service';
import { Institucion } from '../../../models/institucion';
import { AuthService } from '../../../service/auth-service';
import { Estado } from '../../../enums/Estado';

@Component({
  imports: [ReactiveFormsModule, RouterLink],
  selector: 'app-intitucion-form',
  styleUrl: './intitucion-form.css',
  templateUrl: './intitucion-form.html',
})
export class InstitucionForm implements OnInit{
  

  formulario!: FormGroup;
  id?: string;
  esTesoreria = false;

  constructor(
    private fb: FormBuilder,
    private institucionService: InstitucionService,
    private route: ActivatedRoute,
    private router: Router,
    private auth: AuthService
  ) {}

  ngOnInit(): void {
    this.formulario = this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(3)]],
      tipo: ['', [Validators.required]],
      direccion: ['', [Validators.required]],
      telefono: ['', [Validators.pattern('^[0-9]+$')]],
      email: ['', [Validators.email]],
      estado: ['', [Validators.required]]
    });

    this.esTesoreria = this.auth.hasRole('USER_TESORERIA')
      && !this.auth.hasRole('USER_INSTITUCIONAL');
    if (this.esTesoreria) {
      ['nombre', 'tipo', 'direccion', 'telefono', 'email'].forEach(campo => {
        this.formulario.get(campo)?.disable();
      });
    }

    this.id = this.route.snapshot.params['id'];

    if (this.id) {
      //EDITAR
      this.institucionService.obtenerInstitucionPorId(this.id).subscribe({
        next: (institucionEncontrada) => this.formulario.patchValue({
          ...institucionEncontrada,
          estado: institucionEncontrada.estadoPago ?? institucionEncontrada.estado
        }),
        error: (e) => {
          console.log(e);
          alert('Error al cargar los datos de la institución');
        }
      });
    }
  }

  onSubmit(): void {
    if (this.formulario.invalid) {
      alert('No se pueden guardar los datos ingresados. Verificá los campos obligatorios y formatos.');
      return;
    }

    const dataForm: Institucion = { ...this.formulario.getRawValue() };

    if (this.id) {
      const actualizacion = this.esTesoreria
        ? this.institucionService.actualizarEstadoPago(this.id, dataForm.estadoPago ?? dataForm.estado as Estado)
        : this.institucionService.editarInstitucion({ ...dataForm, id: this.id });
      actualizacion.subscribe({
        next: () => {
          alert('Institución actualizada correctamente.');
          this.router.navigate(['/instituciones', this.id]);
        },
        error: (e) => {
          console.log(e);
          alert('Error al editar los datos de la institución');
        }
      });
    } else {
      // Validar duplicados antes de crear (usando el método que armamos antes en el servicio)
      this.institucionService.institucionRepetida(dataForm).subscribe({
        next: (repetida) => {
          if (repetida) {
            alert('Ya existe una institución registrada con el mismo nombre y dirección.');
            return;
          }

          // Crear nueva institución si no está repetida
          this.institucionService.crearInstitucion(dataForm).subscribe({
            next: (data) => {
              alert('Institución creada correctamente.');
              this.router.navigate(['/instituciones', data.id]);
            },
            error: (e) => {
              console.log(e);
              alert('Error al crear la institución.');
            }
          });
        },
        error: (e) => {
          console.log(e);
          alert('Error al validar si la institución está repetida.');
        }
      });
    }
  }
}
