import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Institucion } from '../../../models/institucion';
import { InstitucionService } from '../../../services/instituciones/institucion-service';
import { AuthService } from '../../../service/auth-service';

@Component({
  imports: [RouterLink],
  selector: 'app-institucion-detail',
  styleUrl: './institucion-detail.css',
  templateUrl: './institucion-detail.html',
})
export class InstitucionDetail implements OnInit {
  auth = inject(AuthService);
  readonly puedeEditar = this.auth.hasAnyRole(['USER_INSTITUCIONAL', 'USER_TESORERIA']);
  readonly puedeEliminar = this.auth.hasRole('USER_INSTITUCIONAL');
  readonly puedeVerAuditoria = this.auth.hasAuthority('AUDITORIA_INSTITUCION_VER');
  institucion ?: Institucion;
  id ?: string;

  constructor(
    private institucionService : InstitucionService,
    private route : ActivatedRoute,
    private router : Router,
    private cdr : ChangeDetectorRef
  ){}

  ngOnInit(): void {
    
    this.id = this.route.snapshot.params['id'];

    if(this.id){
      this.institucionService.obtenerInstitucionPorId(this.id).subscribe({
        next : (institucionEncontrada) => {
          this.institucion = institucionEncontrada;
          this.cdr.detectChanges();
        },
        error : (e) => {
          console.log(e);
          alert('Error al buscar detalles de la institución.');
        }
      });
    }
  }

  deleteInstitucion(){
    if(!confirm('¿Desea eliminar la institución?')) return; 

    this.institucionService.eliminarInstitucion(this.id!).subscribe({
      next : () => {
        alert('Institución eliminada correctamente.');
        this.router.navigate(['/instituciones']);
      },
      error : (e) => {
        console.log(e);
        alert('Error al eliminar la institución');
      }
    });
  }}
