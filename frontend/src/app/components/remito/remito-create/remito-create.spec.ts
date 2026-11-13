import { ComponentFixture, TestBed } from '@angular/core/testing';
import { signal } from '@angular/core';
import { of } from 'rxjs';
import { vi } from 'vitest';
import { RemitoCreate } from './remito-create';
import { RemitoService } from '../../../service/remito-service';
import { InstitucionService } from '../../../services/instituciones/institucion-service';
import { ItemdonacionService } from '../../../service/itemdonacion-service';

describe('RemitoCreate', () => {
  let component: RemitoCreate;
  let fixture: ComponentFixture<RemitoCreate>;
  const remitoService = { error: signal(''), crearRemito: vi.fn(() => of({})) };

  beforeEach(async () => {
    remitoService.crearRemito.mockClear();
    await TestBed.configureTestingModule({
      imports: [RemitoCreate],
      providers: [
        { provide: RemitoService, useValue: remitoService },
        { provide: InstitucionService, useValue: { obtenerInstituciones: () => of([]) } },
        { provide: ItemdonacionService, useValue: { listItems: vi.fn(), items: signal([]) } },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(RemitoCreate);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('submits the selected institution and donation items', () => {
    component.formulario.patchValue({
      fecha: '2026-11-13',
      idInstitucion: '3',
    });
    component.items.at(0).patchValue({
      idItemDonacion: '8',
      cantidad: 2,
    });

    component.add();

    expect(remitoService.crearRemito).toHaveBeenCalledWith({
      fecha: '2026-11-13',
      idInstitucion: 3,
      items: [{ idItemDonacion: 8, cantidad: 2 }],
    });
  });
});
