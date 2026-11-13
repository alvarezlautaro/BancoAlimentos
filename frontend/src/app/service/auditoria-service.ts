import { HttpClient } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { Revision } from '../interfaces/Revision';

@Service()
export class AuditoriaService {
    private http = inject(HttpClient);

    getHistorial(entidad: 'donantes' | 'instituciones', id: number) {
        return this.http.get<Revision[]>(`http://localhost:8080/api/${entidad}/${id}/historial`);
    }
}
