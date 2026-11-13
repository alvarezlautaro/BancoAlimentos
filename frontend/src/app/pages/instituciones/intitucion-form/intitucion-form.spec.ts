import { ComponentFixture, TestBed } from '@angular/core/testing';
import { IntitucionForm } from './intitucion-form';

describe('IntitucionForm', () => {
  let component: IntitucionForm;
  let fixture: ComponentFixture<IntitucionForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IntitucionForm],
    }).compileComponents();

    fixture = TestBed.createComponent(IntitucionForm);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
