import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, Service } from '@angular/core';
import { Institucion } from '../../models/institucion';
import { EMPTY, expand, map, reduce } from 'rxjs';
import { PageResponse } from '../../interfaces/remito';
import { Estado } from '../../enums/Estado';

@Injectable({
    providedIn :'root'
})
export class InstitucionService {
        private url = 'http://localhost:8080/api/instituciones';

    constructor(private http : HttpClient){}

    obtenerInstituciones(){
        return this.http.get<PageResponse<Institucion>>(this.url).pipe(
            expand(pagina => pagina.number + 1 < pagina.totalPages
                ? this.http.get<PageResponse<Institucion>>(this.url, {
                    params: new HttpParams()
                        .set('page', pagina.number + 1)
                        .set('size', pagina.size)
                })
                : EMPTY
            ),
            map(pagina => pagina.content.map(institucion => this.normalizar(institucion))),
            reduce((instituciones, contenido) => instituciones.concat(contenido), [] as Institucion[])
        );
    }

    obtenerInstitucionPorId( id : string){
        return this.http.get<Institucion>(`${this.url}/${id}`).pipe(
            map(institucion => this.normalizar(institucion))
        )
    }

    crearInstitucion( institucion : Institucion){
        return this.http.post<Institucion>(this.url, institucion).pipe(
            map(creada => this.normalizar(creada))
        )
    }

    eliminarInstitucion( id : string){
        return this.http.delete<Institucion>(`${this.url}/${id}`)
    }

    editarInstitucion( institucion : Institucion){
        return this.http.put<Institucion>(`${this.url}/${institucion.id}`, institucion).pipe(
            map(actualizada => this.normalizar(actualizada))
        )
    }

    actualizarEstadoPago(id: string, estado: Estado) {
        return this.http.patch<Institucion>(`${this.url}/${id}`, { id: Number(id), estado }).pipe(
            map(actualizada => this.normalizar(actualizada))
        );
    }

    
    institucionRepetida(institucion : Institucion){
        return this.obtenerInstituciones().pipe(
            map((intituciones : Institucion[]) => 
            {
                return intituciones.some(i =>
                    i.nombre.trim().toLowerCase() === institucion.nombre.trim().toLowerCase() 
                    && i.direccion.trim().toLowerCase() === institucion.direccion.trim().toLowerCase()
                 )
            }
        )
        )
    }

    private normalizar(institucion: Institucion): Institucion {
        if (institucion.estadoPago || !institucion.estado) {
            return institucion;
        }

        const { estado, ...datos } = institucion;
        return { ...datos, estadoPago: estado as Estado };
    }
}
