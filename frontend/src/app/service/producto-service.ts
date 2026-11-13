import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Service, signal } from '@angular/core';
import { ProductoRequest, ProductoResponse } from '../interfaces/Producto';
import { Categoria } from '../enums/Categoria';
import { Observable, tap } from 'rxjs';

@Service()
export class ProductoService {
    http: HttpClient = inject(HttpClient);
    url: string = 'http://localhost:8080/api/productos'

    productos = signal<ProductoResponse[]>([]);
    productoById = signal<ProductoResponse | null>(null);

    error= signal<string>('');

    obtenerProductos(): Observable<ProductoResponse[]> {
        return this.http.get<ProductoResponse[]>(this.url);
    }

    listProductos(){
        this.obtenerProductos().subscribe(
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
        this.http.get<ProductoResponse>(this.url + '/' + id).subscribe(
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

        this.http.get<ProductoResponse[]>(this.url + '/filtrar/categoria', { params }).subscribe(
            {
                next: (data) => {
                    this.productos.set(data);
                    this.error.set(''); //Para limpiar errores anteriores
                },
                error: (error) => {
                    this.error.set("No se pudo encontrar productos con la categoria: " + categoria)
                }
            }
        )
    }

    crearProducto(producto: ProductoRequest): Observable<ProductoResponse> {
        return this.http.post<ProductoResponse>(this.url, producto).pipe(
            tap(() => {
                this.error.set('');
                this.listProductos()
            })
        );
    }

    add(producto: ProductoRequest){
        this.crearProducto(producto).subscribe({
            error: error => this.error.set("Error al cargar un producto " + error)
        });
    }

    update(id: number, pUpdate: Partial<ProductoRequest>){
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