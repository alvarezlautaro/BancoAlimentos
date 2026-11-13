import { PageResponse, Remito, RemitoRequest } from './../interfaces/remito';
import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { EMPTY, expand, map, reduce, tap } from 'rxjs';

type RemitoApiResponse = Partial<Remito>;
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
    this.error.set('');
    this.remitoByID.set(null);
    this.obtenerTodosLosRemitos().subscribe({
        next: (remitos) => {
            this.remitos.set(remitos);
        },
        error: (error) => {
            this.error.set("No se pudieron cargar los remitos");
        }
    });
}

    private obtenerTodosLosRemitos(){
        return this.http.get<PageResponse<RemitoApiResponse>>(this.url).pipe(
            expand(pagina => pagina.number + 1 < pagina.totalPages
                ? this.http.get<PageResponse<RemitoApiResponse>>(this.url, {
                    params: new HttpParams()
                        .set('page', pagina.number + 1)
                        .set('size', pagina.size)
                })
                : EMPTY
            ),
            map(pagina => pagina.content.map(remito => this.normalizar(remito))),
            reduce((remitos, contenido) => remitos.concat(contenido), [] as Remito[])
        );
    }

    getById(id: number){
    this.http.get<RemitoApiResponse>(this.url + '/' + id).subscribe({
        next: (data) => {
            this.remitoByID.set(this.normalizar(data));
        },
        error: error => {
            this.error.set("No se pudo encontrar el remito con id: " + id);
        }
    });
}

    private normalizar(remito: RemitoApiResponse): Remito {
        if (remito.id === undefined || remito.fecha === undefined || remito.idInstitucion === undefined) {
            throw new Error('La respuesta del remito no contiene sus datos obligatorios.');
        }

        return {
            id: remito.id,
            fecha: remito.fecha,
            idInstitucion: remito.idInstitucion,
            ...(remito.institucionNombre === undefined ? {} : { institucionNombre: remito.institucionNombre }),
            ...(remito.items === undefined ? {} : { items: remito.items })
        };
    }

    crearRemito(remito: RemitoRequest){
        return this.http.post<RemitoApiResponse>(this.url, remito).pipe(
            map(data => this.normalizar(data)),
            tap(() => {
                this.error.set('');
                this.listRemitos();
            })
        );
    }

    add(remito: RemitoRequest){
        this.crearRemito(remito).subscribe({
            error: error => this.error.set("No se pudo agregar el remito: " + error)
        });
    }

    update(id: number, remito: RemitoRequest){
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
remove(id: number){
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
