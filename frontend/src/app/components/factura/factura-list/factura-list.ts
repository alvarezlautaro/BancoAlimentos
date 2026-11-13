import { Component, inject, OnInit } from '@angular/core';
import { FacturaService } from '../../../service/factura-service';
import { FormsModule } from '@angular/forms';

@Component({
  imports: [FormsModule],
  selector: 'app-factura-list',
  styleUrl: './factura-list.css',
  templateUrl: './factura-list.html',
})
export class FacturaList implements OnInit {
  facturaService = inject(FacturaService);
  searchId: number | null = null;
  fechaDesde = '';
  fechaHasta = '';

  ngOnInit(): void {
    this.facturaService.listFacturas();
  }

  buscar() {
    if (this.searchId !== null) {
      this.facturaService.getById(this.searchId);
    }
  }

  filtrar() {
    this.facturaService.filtrarPorFecha(this.fechaDesde, this.fechaHasta);
  }

  mostrarTodas() {
    this.fechaDesde = '';
    this.fechaHasta = '';
    this.searchId = null;
    this.facturaService.mostrarTodas();
  }
}
