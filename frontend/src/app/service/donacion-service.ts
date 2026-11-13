import { HttpClient } from '@angular/common/http';
import { Injectable, Service } from '@angular/core';
import { Observable } from 'rxjs';
import { Donacion } from '../interfaces/donacion';

@Injectable({
    providedIn :'root'
})
export class DonacionService {

private url = 'http://localhost:8080/api/donaciones';

  constructor(private http: HttpClient) {}

  obtenerDonaciones(): Observable<Donacion[]> {
    return this.http.get<Donacion[]>(this.url);
  }

  obtenerDonacionPorId(id: string | number): Observable<Donacion> {
    return this.http.get<Donacion>(`${this.url}/${id}`);
  }

  crearDonacion(donacion: Donacion): Observable<Donacion> {
    return this.http.post<Donacion>(this.url, donacion);
  }

  editarDonacion(id: string | number, donacion: Donacion): Observable<Donacion> {
    return this.http.put<Donacion>(`${this.url}/${id}`, donacion);
  }

  eliminarDonacion(id: string | number): Observable<any> {
    return this.http.delete(`${this.url}/${id}`, { responseType: 'text' });
  }

  confirmarDonacion(id: string | number): Observable<Donacion> {
    return this.http.put<Donacion>(`${this.url}/${id}/confirmar`, {});
  }}
