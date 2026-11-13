import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FacturaEdit } from './factura-edit';

describe('FacturaEdit', () => {
  let component: FacturaEdit;
  let fixture: ComponentFixture<FacturaEdit>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FacturaEdit],
    }).compileComponents();

    fixture = TestBed.createComponent(FacturaEdit);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
