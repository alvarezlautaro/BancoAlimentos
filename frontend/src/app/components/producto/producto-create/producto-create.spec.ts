import { ComponentFixture, TestBed } from '@angular/core/testing';
import { vi } from 'vitest';
import { ProductoCreate } from './producto-create';
import { ProductoService } from '../../../service/producto-service';
import { Categoria } from '../../../enums/Categoria';
import { UnidadMedida } from '../../../enums/UnidadMedida';
import { of } from 'rxjs';

describe('ProductoCreate', () => {
  let fixture: ComponentFixture<ProductoCreate>;
  let component: ProductoCreate;
  const service = { crearProducto: vi.fn(() => of({})) };

  beforeEach(async () => {
    service.crearProducto.mockClear();
    await TestBed.configureTestingModule({
      imports: [ProductoCreate],
      providers: [{ provide: ProductoService, useValue: service }],
    }).compileComponents();

    fixture = TestBed.createComponent(ProductoCreate);
    component = fixture.componentInstance;
  });

  it('does not create a product while the form is invalid', () => {
    component.add();

    expect(service.crearProducto).not.toHaveBeenCalled();
  });

  it('sends a valid product to the service and resets the form', () => {
    component.formulario.setValue({
      nombre: 'Arroz',
      categoria: Categoria.CEREALES_Y_DERIVADOS,
      unidadMedida: UnidadMedida.KILOGRAMO,
    });

    component.add();

    expect(service.crearProducto).toHaveBeenCalledWith({
      nombre: 'Arroz',
      categoria: Categoria.CEREALES_Y_DERIVADOS,
      unidadMedida: UnidadMedida.KILOGRAMO,
    });
    expect(component.formulario.value.nombre).toBe('');
  });
});
