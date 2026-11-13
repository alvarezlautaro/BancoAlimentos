import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { Categoria } from '../enums/Categoria';
import { UnidadMedida } from '../enums/UnidadMedida';
import { ProductoRequest, ProductoResponse } from '../interfaces/Producto';
import { ProductoService } from './producto-service';

describe('ProductoService CRUD methods', () => {
  let service: ProductoService;
  let http: HttpTestingController;
  const url = 'http://localhost:8080/api/productos';
  const producto: ProductoResponse = {
    id: 8,
    nombre: 'Lentejas',
    categoria: Categoria.LEGUMBRES,
    unidadMedida: UnidadMedida.KILOGRAMO,
  };
  const request: ProductoRequest = {
    nombre: producto.nombre,
    categoria: producto.categoria,
    unidadMedida: producto.unidadMedida,
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting(), ProductoService],
    });
    service = TestBed.inject(ProductoService);
    http = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    http.verify();
  });

  it('loads all products into the signal', () => {
    service.listProductos();

    const request = http.expectOne(url);
    expect(request.request.method).toBe('GET');
    request.flush([producto]);
    expect(service.productos()).toEqual([producto]);
  });

  it('loads a product by id', () => {
    service.getById(producto.id);

    const request = http.expectOne(`${url}/${producto.id}`);
    expect(request.request.method).toBe('GET');
    request.flush(producto);
    expect(service.productoById()).toEqual(producto);
  });

  it('filters products by category', () => {
    service.getByCategoria(producto.categoria);

    const request = http.expectOne(`${url}/filtrar/categoria?categoria=${producto.categoria}`);
    expect(request.request.method).toBe('GET');
    request.flush([producto]);
    expect(service.productos()).toEqual([producto]);
    expect(service.error()).toBe('');
  });

  it('creates a product and reloads the list', () => {
    service.add(request);

    const createRequest = http.expectOne(url);
    expect(createRequest.request.method).toBe('POST');
    expect(createRequest.request.body).toEqual(request);
    createRequest.flush(producto);

    const listRequest = http.expectOne(url);
    listRequest.flush([producto]);
    expect(service.productos()).toEqual([producto]);
  });

  it('updates a product with PATCH and reloads the list', () => {
    const changes = { nombre: 'Lentejas rojas' };
    service.update(producto.id, changes);

    const updateRequest = http.expectOne(`${url}/${producto.id}`);
    expect(updateRequest.request.method).toBe('PATCH');
    expect(updateRequest.request.body).toEqual(changes);
    updateRequest.flush(producto);

    const listRequest = http.expectOne(url);
    listRequest.flush([producto]);
    expect(service.productos()).toEqual([producto]);
  });

  it('deletes a product and reloads the list', () => {
    service.remove(producto.id);

    const deleteRequest = http.expectOne(`${url}/${producto.id}`);
    expect(deleteRequest.request.method).toBe('DELETE');
    deleteRequest.flush(null);

    const listRequest = http.expectOne(url);
    listRequest.flush([]);
    expect(service.productos()).toEqual([]);
  });
});
