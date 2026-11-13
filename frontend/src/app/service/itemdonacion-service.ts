import { HttpClient } from '@angular/common/http';
import { inject, Service, signal } from '@angular/core';
import { ItemDonacionRequest, ItemDonacionResponse } from '../interfaces/ItemDonacion';

@Service()
export class ItemdonacionService {
    http: HttpClient = inject(HttpClient);
    url = 'http://localhost:8080/api/itemdonacion'

    items = signal<ItemDonacionResponse[]>([]);
    itemById = signal<ItemDonacionResponse | null>(null);

    error = signal<string>('');

    listItems(){
        this.http.get<ItemDonacionResponse[]>(this.url).subscribe(
            {
                next: (data) => {
                    this.items.set(data)
                },
                error: (error) => {
                    this.error.set('Error al cargar los items ' + error)
                }
            }
        )
    }

    getById(id: number){
        this.http.get<ItemDonacionResponse>(this.url + '/' + id).subscribe(
            {
                next: (data) => {
                    this.itemById.set(data)
                },
                error: error => {
                    this.error.set('No se pudo encontrar el item con id: ' + id)
                }
            }
        )
    }

    update(id: number, iUpdate: Partial<ItemDonacionRequest>){
        this.http.patch(this.url + '/' + id, iUpdate).subscribe(
            {
                next: () => this.listItems(),
                error: error => this.error.set('No se pudo actualizar el item con id: ' + id)
            }
        )
    }

    remove(id: number){
        this.http.delete(this.url + '/' + id).subscribe(
            {
                next: () => {
                    this.listItems()
                },
                error: error => {
                    this.error.set('No se pudo eliminar el item con id: ' + id)
                }
            }
        )
    }

}
