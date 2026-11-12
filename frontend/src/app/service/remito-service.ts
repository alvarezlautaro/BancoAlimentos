import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Service, signal } from '@angular/core';
import { RemitoResponse } from '../interfaces/remito';

@Service()
export class RemitoService {


    http: HttpClient=inject(HttpClient)
    url:string='http://localhost:8080/api/remitos'
    remitos= signal<RemitoResponse[]>([]);
    remitoById= signal<RemitoResponse | null>(null);

    error= signal<string>('');
    
    listRemitos(){
        this.http.get<RemitoResponse[]>(this.url).subscribe(
            {
                next: (data) => {
                    this.remitos.set(data)
                },
                error: (error) =>{
                    this.error.set("No se pudieron cargar los remitos " + error)
                }
            }
        )
        }

        add(remito: RemitoResponse){
            this.http.post(this.url, remito).subscribe(
                () => {
                    this.listRemitos(); 
                },
                (error) => {
                    this.error.set("No se pudo agregar el remito: " + error);
                }
            );
            }

        getById(id: string){
            this.http.get<RemitoResponse>(this.url + '/' + id).subscribe(
                {
                    next: (data) => {
                        this.remitoById.set(data)
                    },
                    error: error => {
                        this.error.set("No se pudo encontrar el remito con id: " + id)
                    } 
                }
            )
            }

            update(id: string, rUpdate: Partial<RemitoResponse>){
                this.http.put(this.url + '/' + id, rUpdate).subscribe(
                    () => {
                        this.listRemitos(); 
                    },
                    (error) => {
                        this.error.set("No se pudo actualizar el remito: " + error);
                    }
                );
           }

           getbyInstitucion(institucionId: string){
            const params = new HttpParams().set('institucionId', institucionId);
            return this.http.get<RemitoResponse[]>(this.url, { params });
} 

            getByFecha(fecha: string){
                const params = new HttpParams().set('fecha', fecha);
                return this.http.get<RemitoResponse[]>(this.url, { params });
            }
            
            remover(id: string){
                this.http.delete(this.url + '/' + id).subscribe(
                    () => {
                        this.listRemitos(); 
                    },
                    (error) => {
                        this.error.set("No se pudo eliminar el remito: " + error);
                    }
                );
            }
}
