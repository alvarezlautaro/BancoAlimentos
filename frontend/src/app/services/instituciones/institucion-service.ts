import { HttpClient } from '@angular/common/http';
import { Injectable, Service } from '@angular/core';
import { Institucion } from '../../models/institucion';
import { map } from 'rxjs';

@Injectable({
    providedIn :'root'
})
export class InstitucionService {
        private url = 'http://localhost:3000/instituciones';

    constructor(private http : HttpClient){}

    obtenerInstituciones(){
        return this.http.get<Institucion[]>(this.url)
    }

    obtenerInstitucionPorId( id : string){
        return this.http.get<Institucion>(`${this.url}/${id}`)
    }

    crearInstitucion( institucion : Institucion){
        return this.http.post<Institucion>(this.url, institucion)
    }

    eliminarInstitucion( id : string){
        return this.http.delete<Institucion>(`${this.url}/${id}`)
    }

    editarInstitucion( institucion : Institucion){
        return this.http.put<Institucion>(`${this.url}/${institucion.id}`, institucion)
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
}
