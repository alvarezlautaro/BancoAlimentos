import { TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { App } from './app';
import { routes } from './app.routes';
import { AuthService } from './service/auth-service';
import { DonacionService } from './service/donacion-service';
import { RemitoService } from './service/remito-service';
import { of } from 'rxjs';
import { signal } from '@angular/core';

describe('App', () => {
  const authServiceMock = {
    isLoggedIn: () => true,
    username: () => 'deposito',
    hasRole: (role: string) => role === 'USER_DEPOSITO',
    hasAnyRole: (roles: string[]) => roles.includes('USER_DEPOSITO'),
    hasAuthority: (authority: string) => authority === 'AUDITORIA_DONACION_VER',
    logout: vi.fn(),
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [App],
      providers: [
        provideRouter(routes),
        { provide: AuthService, useValue: authServiceMock },
        { provide: DonacionService, useValue: { obtenerDonaciones: () => of([]) } },
        { provide: RemitoService, useValue: { listRemitos: () => undefined, remitos: signal([]), error: signal('') } },
      ],
    })
      .compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(App);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it('renders the shared application header', async () => {
    const fixture = TestBed.createComponent(App);
    fixture.detectChanges();
    await fixture.whenStable();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('.brand')?.textContent).toContain('Banco de Alimentos');
  });
});
