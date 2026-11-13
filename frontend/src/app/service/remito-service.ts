import { PageResponse, Remito, RemitoRequest } from './../interfaces/remito';
import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
@Injectable({
  providedIn: 'root'
})


export class RemitoService {

    url: string = 'http://localhost:8080/api/remitos'
    http: HttpClient = inject(HttpClient); 

    remitos = signal<Remito[]>([]);
    remitoByID = signal<Remito | null>(null);
    
    error= signal<string>('');

   listRemitos(){
    this.http.get<PageResponse<Remito>>(this.url).subscribe({
        next: (data) => {
            this.remitos.set(data.content);
        },
        error: (error) => {
            this.error.set("No se pudieron cargar los remitos");
        }
    });
}

    getById(id: string){
    this.http.get<Remito>(this.url + '/' + id).subscribe({
        next: (data) => {
            this.remitoByID.set(data);
        },
        error: error => {
            this.error.set("No se pudo encontrar el remito con id: " + id);
        }
    });
}

    add(remito: RemitoRequest){
        this.http.post(this.url, remito).subscribe(
            () => {
                this.listRemitos();
            },
            (error) => {
                this.error.set("No se pudo agregar el remito: " + error);
            }
        );
        }

    update(id: string, remito: RemitoRequest){ 
        this.http.put<Remito>( this.url + '/' + id,remito ).subscribe
        ({
        next: () => {
            this.listRemitos();
        },
        error: (error) => {
            this.error.set("No se pudo actualizar el remito");
        }
    });
}
remove(id: string){
    this.http.delete<void>(
        this.url + '/' + id
    ).subscribe({
        next: () => {
            this.listRemitos();
        },
        error: (error) => {
            this.error.set("No se pudo eliminar el remito");
        }
    });
}
}
