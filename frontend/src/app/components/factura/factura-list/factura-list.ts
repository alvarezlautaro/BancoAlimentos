import { Component, inject, OnInit } from '@angular/core';
import { FacturaService } from '../../../service/factura-service';
import { ReactiveFormsModule } from '@angular/forms';
 import { FormsModule } from '@angular/forms';
@Component({
  imports: [FormsModule ],
  selector: 'app-factura-list',
  styleUrl: './factura-list.css',
  templateUrl: './factura-list.html',
})
export class FacturaList implements OnInit {

  facturaService = inject(FacturaService);
  searchId = 0;
fechaDesde = '';
fechaHasta = '';
  ngOnInit(): void {
    this.facturaService.listFacturas();
  }

  buscar() {
this.facturaService.getById(this.searchId);  }

filtrar() {
  this.facturaService.filtrarPorFecha(this.fechaDesde, this.fechaHasta);
}
}
