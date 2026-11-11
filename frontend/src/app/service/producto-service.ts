import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Service, signal } from '@angular/core';
import { Producto } from '../interfaces/Producto';
import { Categoria } from '../enums/Categoria';

@Service()
export class ProductoService {
    http: HttpClient = inject(HttpClient);
    url: string = 'http://localhost:8080/api/productos'

    productos = signal<Producto[]>([]);
    productoById = signal<Producto | null>(null);
    productosByCategoria = signal<Producto[]>([]);

    error= signal<string>('');

    listProductos(){
        this.http.get<Producto[]>(this.url).subscribe(
            {
                next: (data) => {
                    this.productos.set(data)
                },
                error: (error) =>{
                    this.error.set("No se pudieron cargar los productos " + error)
                }
            }
        )
    }

    getById(id: number){
        this.http.get<Producto>(this.url + '/' + id).subscribe(
            {
                next: (data) => {
                    this.productoById.set(data)
                },
                error: error => {
                    this.error.set("No se pudo encontrar el producto con id: " + id)
                } 
            }
        )
    }

    getByCategoria(categoria: Categoria){
        const params = new HttpParams().set('categoria', categoria);

        this.http.get<Producto[]>(this.url + 'filtrar/categoria', { params }).subscribe(
            {
                next: (data) => {
                    this.productosByCategoria.set(data)
                },
                error: (error) => {
                    this.error.set("No se pudo encontrar productos con la categoria: " + categoria)
                }
            }
        )
    }

    add(producto: Producto){
        this.http.post(this.url, producto).subscribe(
            () => {
                this.listProductos()
            },
            error => {
                this.error.set("Error al cargar un producto " + error)
            }
        )
    }

    update(id: number, pUpdate: Partial<Producto>){
        this.http.patch(this.url + '/' + id, pUpdate).subscribe(
            {
                next: () => this.listProductos(),
                error: error => this.error.set("No se pudo actualizar el producto con id: " + id)
            }
        )
    }

    remove(id: number){
        this.http.delete(this.url + '/' + id).subscribe(
            {
                next: () => {
                    this.listProductos()
                },
                error: error => {
                    this.error.set("No se pudo eliminar el producto con id: " + id)
                }
            }
        )
    }
}