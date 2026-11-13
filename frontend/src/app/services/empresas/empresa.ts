import { HttpClient } from '@angular/common/http';
import { Injectable, Service } from '@angular/core';
import { Empresa } from '../../models/empresa';
import { map } from 'rxjs';


@Injectable({
    providedIn :'root'
})
export class EmpresaService{

    private url = 'http://localhost:8080/api/donantes';

    constructor(private http : HttpClient){}

    obtenerEmpresas(){
        return this.http.get<Empresa[]>(this.url);
    }

    obtenerEmpresaPorId(id : string){
        return this.http.get<Empresa>(`${this.url}/${id}`);
    }

    crearEmpresa(empresa : Empresa){
        return this.http.post<Empresa>(this.url, empresa);
    }

    eliminarEmpresa(id : string){
        return this.http.delete<Empresa>(`${this.url}/${id}`);
    }
    
    editarEmpresa(empresa : Empresa){
        return this.http.put<Empresa>(`${this.url}/${empresa.id}`, empresa)
    }

    empresaRepetida(empresa : Empresa){
        return this.obtenerEmpresas().pipe(
           map((empresas : Empresa[]) => 
        {
            return empresas.some( e => e.cuit.toString() == empresa.cuit.toString())
        })
        )
    }

}
