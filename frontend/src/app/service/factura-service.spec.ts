import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { Factura } from '../interfaces/factura-interface';
import { FacturaService } from './factura-service';

describe('FacturaService', () => {
  let service: FacturaService;
  let http: HttpTestingController;
  const url = 'http://localhost:8080/api/facturas';
  const facturas: Factura[] = [
    { id: 1, idDonacion: 11, fecha: '2026-01-10', tipo: 'A', nombreDonante: 'Donante 1' },
    { id: 2, idDonacion: 12, fecha: '2026-02-20', tipo: 'B', nombreDonante: 'Donante 2' },
  ];

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting(), FacturaService],
    });
    service = TestBed.inject(FacturaService);
    http = TestBed.inject(HttpTestingController);
  });

  afterEach(() => http.verify());

  it('loads invoices and filters from the complete source list', () => {
    service.listFacturas();
    const request = http.expectOne(url);
    expect(request.request.method).toBe('GET');
    request.flush(facturas);

    service.filtrarPorFecha('2026-02-01', '2026-02-28');
    expect(service.facturas()).toEqual([facturas[1]]);

    service.filtrarPorFecha('2026-01-01', '2026-12-31');
    expect(service.facturas()).toEqual(facturas);

    service.filtrarPorFecha('', '');
    expect(service.facturas()).toEqual(facturas);
  });

  it('rejects invalid date ranges without losing the loaded invoices', () => {
    service.listFacturas();
    http.expectOne(url).flush(facturas);

    service.filtrarPorFecha('2026-03-01', '2026-02-01');

    expect(service.facturas()).toEqual(facturas);
    expect(service.error()).toContain('fecha inicial');
  });

  it('searches by invoice ID and clears stale results before searching', () => {
    service.getById(2);
    const request = http.expectOne(`${url}/2`);
    expect(request.request.method).toBe('GET');
    request.flush(facturas[1]);

    expect(service.facturaById()).toEqual(facturas[1]);
  });

  it('clears the invoice result when given an invalid ID', () => {
    service.getById(0);

    expect(service.facturaById()).toBeNull();
    expect(service.error()).toContain('ID');
    http.expectNone(url);
  });
});
