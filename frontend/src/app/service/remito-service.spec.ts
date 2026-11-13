import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { PageResponse, Remito, RemitoRequest } from '../interfaces/remito';
import { RemitoService } from './remito-service';

describe('RemitoService', () => {
  let service: RemitoService;
  let http: HttpTestingController;
  const url = 'http://localhost:8080/api/remitos';
  const remito: Remito = { id: 8, fecha: '2026-11-13', idInstitucion: 3 };
  const request: RemitoRequest = { fecha: remito.fecha, idInstitucion: remito.idInstitucion };
  const page: PageResponse<Remito> = {
    content: [remito],
    totalElements: 1,
    totalPages: 1,
    size: 10,
    number: 0,
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting(), RemitoService],
    });
    service = TestBed.inject(RemitoService);
    http = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    http.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('loads the paginated list into the remitos signal', () => {
    service.listRemitos();

    const request = http.expectOne(url);
    expect(request.request.method).toBe('GET');
    request.flush(page);
    expect(service.remitos()).toEqual([remito]);
  });

  it('collects remitos from every backend page', () => {
    const segundoRemito: Remito = { id: 9, fecha: '2026-11-14', idInstitucion: 4 };
    service.listRemitos();

    const primeraPagina = http.expectOne(url);
    primeraPagina.flush({ ...page, totalElements: 2, totalPages: 2 });

    const segundaPagina = http.expectOne(
      request => request.url === url && request.params.get('page') === '1' && request.params.get('size') === '10'
    );
    expect(segundaPagina.request.method).toBe('GET');
    segundaPagina.flush({
      ...page,
      content: [segundoRemito],
      totalElements: 2,
      totalPages: 2,
      number: 1,
    });

    expect(service.remitos()).toEqual([remito, segundoRemito]);
  });

  it('loads one remito by id', () => {
    service.getById(remito.id);

    const request = http.expectOne(`${url}/${remito.id}`);
    expect(request.request.method).toBe('GET');
    request.flush(remito);
    expect(service.remitoByID()).toEqual(remito);
  });

  it('creates a remito and refreshes the list', () => {
    service.add(request);

    const createRequest = http.expectOne(url);
    expect(createRequest.request.method).toBe('POST');
    expect(createRequest.request.body).toEqual(request);
    createRequest.flush(remito);

    const listRequest = http.expectOne(url);
    expect(listRequest.request.method).toBe('GET');
    listRequest.flush(page);
    expect(service.remitos()).toEqual([remito]);
  });

  it('updates a remito and refreshes the list', () => {
    service.update(remito.id, request);

    const updateRequest = http.expectOne(`${url}/${remito.id}`);
    expect(updateRequest.request.method).toBe('PUT');
    expect(updateRequest.request.body).toEqual(request);
    updateRequest.flush(remito);

    const listRequest = http.expectOne(url);
    listRequest.flush(page);
    expect(service.remitos()).toEqual([remito]);
  });

  it('deletes a remito and refreshes the list', () => {
    service.remove(remito.id);

    const deleteRequest = http.expectOne(`${url}/${remito.id}`);
    expect(deleteRequest.request.method).toBe('DELETE');
    deleteRequest.flush(null);

    const listRequest = http.expectOne(url);
    listRequest.flush(page);
    expect(service.remitos()).toEqual([remito]);
  });
});
