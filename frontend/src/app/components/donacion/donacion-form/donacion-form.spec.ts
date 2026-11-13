import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DonacionForm } from './donacion-form';

describe('DonacionForm', () => {
  let component: DonacionForm;
  let fixture: ComponentFixture<DonacionForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DonacionForm],
    }).compileComponents();

    fixture = TestBed.createComponent(DonacionForm);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
