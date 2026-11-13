import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { Home } from './home';
import { AuthService } from '../../service/auth-service';
import { DonacionService } from '../../service/donacion-service';
import { RemitoService } from '../../service/remito-service';
import { EstadoDonacion } from '../../enums/EstadoDonacion';
import { Remito } from '../../interfaces/remito';
import { signal } from '@angular/core';
import { of } from 'rxjs';

describe('Home', () => {
  let component: Home;
  let fixture: ComponentFixture<Home>;
  let roles: string[];

  const authServiceMock = {
    username: () => 'usuario',
    hasRole: (role: string) => roles.includes(role),
    hasAnyRole: (requiredRoles: string[]) => requiredRoles.some(role => roles.includes(role)),
    hasAuthority: (authority: string) => roles.includes(authority),
    logout: vi.fn(),
  };
  const donacionServiceMock = {
    obtenerDonaciones: vi.fn(() => of([])),
  };
  const remitoServiceMock = {
    listRemitos: vi.fn(),
    remitos: signal<Remito[]>([]),
    error: signal(''),
  };

  beforeEach(async () => {
    roles = ['USER_DEPOSITO', 'AUDITORIA_DONACION_VER'];
    donacionServiceMock.obtenerDonaciones.mockClear();
    remitoServiceMock.listRemitos.mockClear();
    await TestBed.configureTestingModule({
      imports: [Home],
      providers: [
        provideRouter([]),
        { provide: AuthService, useValue: authServiceMock },
        { provide: DonacionService, useValue: donacionServiceMock },
        { provide: RemitoService, useValue: remitoServiceMock },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(Home);
    component = fixture.componentInstance;
    fixture.detectChanges();
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('shows only deposit modules for a deposit user', () => {
    const anchors = fixture.nativeElement.querySelectorAll('nav[aria-label="Módulos de depósito"] a') as NodeListOf<HTMLAnchorElement>;
    const links = Array.from(anchors, link => link.getAttribute('href'));

    expect(links).toEqual([
      '/empresas',
      '/donaciones',
      '/remitos',
      '/itemdonacion',
      '/productos',
      '/facturas',
      '/instituciones',
    ]);
  });

  it('shows only institutions for institutional and treasury users', () => {
    roles = ['USER_TESORERIA'];
    fixture.destroy();
    fixture = TestBed.createComponent(Home);
    component = fixture.componentInstance;
    fixture.detectChanges();

    const anchors = fixture.nativeElement.querySelectorAll('nav[aria-label="Módulos institucionales"] a') as NodeListOf<HTMLAnchorElement>;
    const links = Array.from(anchors, link => link.getAttribute('href'));

    expect(links).toEqual(['/instituciones']);
  });

  it('loads recent donations and remitos for deposit users only', () => {
    expect(donacionServiceMock.obtenerDonaciones).toHaveBeenCalledOnce();
    expect(remitoServiceMock.listRemitos).toHaveBeenCalledOnce();
    expect(fixture.nativeElement.textContent).toContain('Últimas donaciones cargadas');
    expect(fixture.nativeElement.textContent).toContain('Últimos remitos emitidos');
  });

  it('orders the recent dashboard records by ID and issue date', () => {
    component.donaciones.set([
      { id: 2, fecha: '2026-02-01', estado: EstadoDonacion.PENDIENTE, nroRemitoProveedor: 2, idDonante: 2, items: [] },
      { id: 9, fecha: '2026-01-01', estado: EstadoDonacion.PENDIENTE, nroRemitoProveedor: 9, idDonante: 9, items: [] },
    ]);
    remitoServiceMock.remitos.set([
      { id: 1, fecha: '2026-01-01', idInstitucion: 1 },
      { id: 2, fecha: '2026-03-01', idInstitucion: 2 },
    ]);

    expect(component.ultimasDonaciones().map(donacion => donacion.id)).toEqual([9, 2]);
    expect(component.ultimosRemitos().map(remito => remito.id)).toEqual([2, 1]);
  });

  it('explains when the session has no supported role', () => {
    roles = [];
    fixture.destroy();
    fixture = TestBed.createComponent(Home);
    fixture.detectChanges();

    expect(fixture.nativeElement.textContent).toContain('Perfil de acceso no disponible');
  });
});
