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

  listFacturas() {
    this.http.get<Factura[]>(this.url).subscribe({
      next: (data) => {
        this.facturas.set(data);
      },
      error: (error) => {
        this.error.set('No se pudieron cargar las facturas');
 }
    });
 }

  getById(id: number) {
    this.http.get<Factura>(this.url + '/' + id).subscribe({
      next: (data) => {
        this.facturaById.set(data);
      },
      error: (error) => {
        this.error.set('No se pudo encontrar la factura con id: ' + id);
      }
    });
  }

  filtrarPorFecha(fechaDesde: string, fechaHasta: string) {

  const facturasFiltradas = this.facturas().filter(factura => {

    if (fechaDesde && factura.fecha < fechaDesde) {
      return false;
    }

    if (fechaHasta && factura.fecha > fechaHasta) {
      return false;
    }

    return true;
  });

  this.facturas.set(facturasFiltradas);
}
}
