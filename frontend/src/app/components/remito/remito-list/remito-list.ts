import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RemitoResponse } from '../../../interfaces/remito';
import { RemitoService } from '../../../service/remito-service';
import { RemitoCreate } from '../remito-create/remito-create';
import { RemitoEdit } from '../remito-edit/remito-edit';

@Component({
  imports: [FormsModule, RemitoCreate, RemitoEdit],
  selector: 'app-remito-list',
  styleUrl: './remito-list.css',
  templateUrl: './remito-list.html',
})
export class RemitoList implements OnInit {
  readonly remitoService = inject(RemitoService);
  searchId = '';
  institucionId = '';
  fecha = '';

  ngOnInit() {
    this.remitoService.listRemitos();
  }

  buscar() {
    if (this.searchId.trim()) {
      this.remitoService.getById(this.searchId.trim());
    }
  }

  buscarPorInstitucion() {
    if (!this.institucionId.trim()) {
      this.remitoService.listRemitos();
      return;
    }

    this.remitoService.getbyInstitucion(this.institucionId).subscribe({
      next: (remitos) => {
        this.remitoService.remitos.set(remitos);
        this.remitoService.error.set('');
      },
      error: (error) => {
        this.remitoService.error.set('No se pudieron cargar los remitos de la institución: ' + error);
      },
    });
  }

  buscarPorFecha() {
    if (!this.fecha) {
      this.remitoService.listRemitos();
      return;
    }

    this.remitoService.getByFecha(this.fecha).subscribe({
      next: (remitos) => {
        this.remitoService.remitos.set(remitos);
        this.remitoService.error.set('');
      },
      error: (error) => {
        this.remitoService.error.set('No se pudieron cargar los remitos de la fecha: ' + error);
      },
    });
  }

  editar(remito: RemitoResponse) {
    this.remitoService.remitoById.set(remito);
  }

  eliminar(id: string) {
    this.remitoService.remover(id);
    if (this.remitoService.remitoById()?.id === id) {
      this.remitoService.remitoById.set(null);
    }
  }
}
