import { CommonModule } from '@angular/common';
import { Component, OnInit, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Donacion } from '../../../interfaces/donacion';
import { DonacionService } from '../../../service/donacion-service';

@Component({
  imports: [RouterLink, FormsModule, CommonModule],
  selector: 'app-donacion-list',
  styleUrl: './donacion-list.css',
  templateUrl: './donacion-list.html',
})
export class DonacionList implements OnInit{
  donaciones = signal<Donacion[]>([]);
  filtroDonacion = '';

  constructor(private donacionService: DonacionService) {}

  ngOnInit(): void {
    this.cargarDonaciones();
  }

  cargarDonaciones() {
    this.donacionService.obtenerDonaciones().subscribe({
      next: (data) => {
        this.donaciones.set(data);
      },
      error: (e) => {
        console.log(e);
        alert('Error al cargar las donaciones');
      }
    });
  }

  eliminarDonacion(id: number) {
    if (!confirm(`¿Estás seguro de eliminar la donación N° ${id}?`)) return;

    this.donacionService.eliminarDonacion(id).subscribe({
      next: () => {
        this.cargarDonaciones();
        alert('Donación eliminada correctamente');
      },
      error: (e) => {
        console.log(e);
        alert('No se pudo eliminar la donación');
      }
    });
  }

  confirmar(id: number) {
    this.donacionService.confirmarDonacion(id).subscribe({
      next: () => {
        this.cargarDonaciones();
        alert('Donación confirmada exitosamente');
      },
      error: (e) => {
        console.log(e);
        alert('Error al confirmar la donación');
      }
    });
  }

  get donacionesFiltradas() {
    const lista = this.donaciones();
    const filtro = this.filtroDonacion.toLowerCase().trim();

    if (!filtro) {
      return lista;
    } else {
      return lista.filter(donacion => {
        const idCoincide = donacion.id?.toString().toLowerCase().includes(filtro);
        const estadoCoincide = String(donacion.estado || '').toLowerCase().includes(filtro);
        const remitoCoincide = donacion.nroRemitoProveedor?.toString().toLowerCase().includes(filtro);
        const observacionesCoincide = donacion.observaciones?.toLowerCase().includes(filtro);
        const donanteCoincide = donacion.nombreDonante?.toLowerCase().includes(filtro);

        return idCoincide || estadoCoincide || remitoCoincide || observacionesCoincide || donanteCoincide;
      });
    }
  }
}
