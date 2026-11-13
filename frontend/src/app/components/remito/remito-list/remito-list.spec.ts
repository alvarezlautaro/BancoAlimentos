import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { signal } from '@angular/core';
import { vi } from 'vitest';
import { RemitoService } from '../../../service/remito-service';
import { RemitoList } from './remito-list';

describe('RemitoList', () => {
  let component: RemitoList;
  let fixture: ComponentFixture<RemitoList>;
  let remitoServiceMock: { listRemitos: ReturnType<typeof vi.fn>; remitos: ReturnType<typeof signal>; remitoByID: ReturnType<typeof signal>; error: ReturnType<typeof signal>; remove: ReturnType<typeof vi.fn>; };

  beforeEach(async () => {
    remitoServiceMock = {
      listRemitos: vi.fn(),
      remitos: signal([]),
      remitoByID: signal(null),
      error: signal(null),
      remove: vi.fn(),
    };

    await TestBed.configureTestingModule({
      imports: [RemitoList],
      providers: [
        provideRouter([]),
        {
          provide: RemitoService,
          useValue: remitoServiceMock,
        },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(RemitoList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('shows the institution name and extra remito details', () => {
    remitoServiceMock.remitos.set([
      {
        id: 7,
        fecha: '2026-11-13',
        idInstitucion: 1,
        institucionNombre: 'Banco de Alimentos',
        items: [{ idItemDonacion: 1, cantidad: 2 }, { idItemDonacion: 2, cantidad: 5 }],
      },
    ]);

    fixture.detectChanges();

    const text = fixture.nativeElement.textContent;
    expect(text).toContain('Remito N° 7');
    expect(text).toContain('Banco de Alimentos');
    expect(text).toContain('Items:');
    expect(text).not.toContain('Institución: 1');
  });
});
