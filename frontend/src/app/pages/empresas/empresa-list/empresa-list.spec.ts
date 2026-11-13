import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { EmpresaList } from './empresa-list';
import { EmpresaService } from '../../../services/empresas/empresa';
import { of } from 'rxjs';

describe('EmpresaList', () => {
  let component: EmpresaList;
  let fixture: ComponentFixture<EmpresaList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmpresaList],
      providers: [
        provideRouter([]),
        { provide: EmpresaService, useValue: { obtenerEmpresas: () => of([]) } },
      ],
    })
      .compileComponents();

    fixture = TestBed.createComponent(EmpresaList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('filters by identifier, company name, CUIT, email, and address', () => {
    component.empresas.set([
      {
        id: 'EMP-01',
        razon_social: 'Alimentos Uno',
        cuit: '20123456789',
        telefono: '55555555',
        email: 'uno@example.com',
        direccion: 'Calle Norte',
      },
      {
        id: 'EMP-02',
        razon_social: 'Alimentos Dos',
        cuit: '20987654321',
        telefono: '66666666',
        email: 'dos@example.com',
        direccion: 'Calle Sur',
      },
    ]);

    for (const filtro of ['EMP-01', 'Uno', '20123456789', 'uno@example.com', 'Calle Norte']) {
      component.filtroEmpresa = filtro;
      expect(component.empresasFiltradas.map(empresa => empresa.id)).toEqual(['EMP-01']);
    }
  });
});
