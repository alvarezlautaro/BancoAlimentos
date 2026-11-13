import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { InstitucionDetail } from './institucion-detail';
import { AuthService } from '../../../service/auth-service';
import { InstitucionService } from '../../../services/instituciones/institucion-service';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

describe('InstitucionDetail', () => {
  let component: InstitucionDetail;
  let fixture: ComponentFixture<InstitucionDetail>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InstitucionDetail],
      providers: [
        provideRouter([]),
        { provide: AuthService, useValue: { hasRole: () => false, hasAnyRole: () => false, hasAuthority: () => false } },
        { provide: InstitucionService, useValue: { obtenerInstitucionPorId: () => of(undefined) } },
        { provide: ActivatedRoute, useValue: { snapshot: { params: {} } } },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(InstitucionDetail);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
