import { DatePipe } from '@angular/common';
import { Component, computed, inject, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuditoriaService } from '../../../service/auditoria-service';
import { Revision } from '../../../interfaces/Revision';

@Component({
  imports: [DatePipe],
  selector: 'app-historial',
  templateUrl: './historial.html',
})
export class Historial {
  private route = inject(ActivatedRoute);
  private auditoriaService = inject(AuditoriaService);

  entidad = this.route.snapshot.data['entidad'] as 'donantes' | 'instituciones';
  titulo = this.entidad === 'donantes' ? 'Historial del donante' : 'Historial de la institución';
  etiquetas = { ADD: 'Alta', MOD: 'Modificación', DEL: 'Baja' };

  revisiones = signal<Revision[]>([]);
  error = signal('');

  columnas = computed(() => {
    const fila = this.revisiones().find(r => r.tipo !== 'DEL' && r.datos);
    return fila ? Object.keys(fila.datos).filter(c => c !== 'id') : [];
  });

  constructor() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.auditoriaService.getHistorial(this.entidad, id).subscribe({
      next: data => this.revisiones.set(data),
      error: err => this.error.set('No se pudo cargar el historial (error ' + err.status + ')'),
    });
  }
}
