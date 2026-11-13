import { HttpClient } from '@angular/common/http';
import { Injectable, Service } from '@angular/core';
import { Empresa } from '../../models/empresa';
import { map } from 'rxjs';

interface DonanteRequestDTO {
    razonSocial: string;
    cuit: string;
    telefono: string;
    email: string;
    direccion: string;
}

interface DonanteResponseDTO extends DonanteRequestDTO {
    id: number;
}

@Injectable({
    providedIn :'root'
})
export class EmpresaService{

    private url = 'http://localhost:8080/api/donantes';

    constructor(private http : HttpClient){}

    obtenerEmpresas(){
        return this.http.get<DonanteResponseDTO[]>(this.url).pipe(
            map(donantes => donantes.map(donante => this.toEmpresa(donante)))
        );
    }

    obtenerEmpresaPorId(id : string){
        return this.http.get<DonanteResponseDTO>(`${this.url}/${id}`).pipe(
            map(donante => this.toEmpresa(donante))
        );
    }

    crearEmpresa(empresa : Empresa){
        return this.http.post<DonanteResponseDTO>(this.url, this.toRequestDTO(empresa)).pipe(
            map(donante => this.toEmpresa(donante))
        );
    }

    eliminarEmpresa(id : string){
        return this.http.delete<string>(`${this.url}/${id}`);
    }
    
    editarEmpresa(empresa : Empresa){
        return this.http.put<DonanteResponseDTO>(`${this.url}/${empresa.id}`, this.toRequestDTO(empresa)).pipe(
            map(donante => this.toEmpresa(donante))
        );
    }

    empresaRepetida(empresa : Empresa){
        return this.obtenerEmpresas().pipe(
           map((empresas : Empresa[]) => 
        {
            return empresas.some( e => e.cuit.toString() == empresa.cuit.toString())
        })
        )
    }

    private toRequestDTO(empresa: Empresa): DonanteRequestDTO {
        return {
            razonSocial: empresa.razon_social,
            cuit: empresa.cuit,
            telefono: empresa.telefono,
            email: empresa.email,
            direccion: empresa.direccion
        };
    }

    private toEmpresa(donante: DonanteResponseDTO): Empresa {
        return {
            id: String(donante.id),
            razon_social: donante.razonSocial,
            cuit: donante.cuit,
            telefono: donante.telefono,
            email: donante.email,
            direccion: donante.direccion
        };
    }
}
