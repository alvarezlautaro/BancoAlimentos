import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FacturaCreate } from './factura-create';

describe('FacturaCreate', () => {
  let component: FacturaCreate;
  let fixture: ComponentFixture<FacturaCreate>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FacturaCreate],
    }).compileComponents();

    fixture = TestBed.createComponent(FacturaCreate);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
