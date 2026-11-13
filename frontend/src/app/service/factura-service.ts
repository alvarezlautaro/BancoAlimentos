import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Factura } from '../interfaces/factura-interface';

@Injectable({
  providedIn: 'root'
})
export class FacturaService {

  private http = inject(HttpClient);

  private url = 'http://localhost:8080/api/facturas';

  facturas = signal<Factura[]>([]);
  facturaById = signal<Factura | null>(null);
  error = signal<string | null>(null);
  private todasFacturas = signal<Factura[]>([]);

  listFacturas() {
    this.error.set(null);
    this.facturaById.set(null);
    this.http.get<Factura[]>(this.url).subscribe({
      next: (data) => {
        this.todasFacturas.set(data);
        this.facturas.set(data);
      },
      error: () => {
        this.todasFacturas.set([]);
        this.facturas.set([]);
        this.error.set('No se pudieron cargar las facturas');
      }
    });
  }

  getById(id: number) {
    this.error.set(null);
    this.facturaById.set(null);
    if (!Number.isInteger(id) || id < 1) {
      this.error.set('Ingresá un ID de factura válido');
      return;
    }

    this.http.get<Factura>(this.url + '/' + id).subscribe({
      next: (data) => {
        this.facturaById.set(data);
      },
      error: () => {
        this.error.set('No se pudo encontrar la factura con id: ' + id);
      }
    });
  }

  filtrarPorFecha(fechaDesde: string, fechaHasta: string) {
    this.error.set(null);
    this.facturaById.set(null);

    if (fechaDesde && fechaHasta && fechaDesde > fechaHasta) {
      this.error.set('La fecha inicial no puede ser posterior a la fecha final');
      return;
    }

    const facturasFiltradas = this.todasFacturas().filter(factura => {
      const fecha = factura.fecha.slice(0, 10);

      if (fechaDesde && fecha < fechaDesde) return false;
      if (fechaHasta && fecha > fechaHasta) return false;

      return true;
    });

    this.facturas.set(facturasFiltradas);
  }

  mostrarTodas() {
    this.error.set(null);
    this.facturaById.set(null);
    this.facturas.set(this.todasFacturas());
  }
}
