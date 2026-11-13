import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Donacion } from '../../../interfaces/donacion';
import { DonacionService } from '../../../service/donacion-service';

@Component({
  imports: [RouterLink, CommonModule],
  selector: 'app-donacion-details',
  styleUrl: './donacion-details.css',
  templateUrl: './donacion-details.html',
})
export class DonacionDetails implements OnInit{
  donacion?: Donacion;
  id?: string;

  constructor(
    private donacionService: DonacionService,
    private route: ActivatedRoute,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];

    if (this.id) {
      this.donacionService.obtenerDonacionPorId(this.id).subscribe({
        next: (donacionEncontrada) => {
          this.donacion = donacionEncontrada;
          this.cdr.detectChanges();
        },
        error: (e) => {
          console.log(e);
          alert('Error al buscar detalles de la donación.');
        }
      });
    }
  }

  deleteDonacion() {
    if (!confirm('¿Desea eliminar la donación?')) return; 

    this.donacionService.eliminarDonacion(this.id!).subscribe({
      next: () => {
        alert('Donación eliminada correctamente.');
        this.router.navigate(['/donaciones']);
      },
      error: (e) => {
        console.log(e);
        alert('Error al eliminar la donación');
      }
    });
  }

  confirmar() {
    if (!this.id) return;
    this.donacionService.confirmarDonacion(this.id).subscribe({
      next: (donacionActualizada) => {
        this.donacion = donacionActualizada;
        alert('Donación confirmada exitosamente.');
        this.cdr.detectChanges();
      },
      error: (e) => {
        console.log(e);
        alert('Error al confirmar la donación');
      }
    });
  }
}
