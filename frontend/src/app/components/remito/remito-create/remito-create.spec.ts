import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RemitoCreate } from './remito-create';

describe('RemitoCreate', () => {
  let component: RemitoCreate;
  let fixture: ComponentFixture<RemitoCreate>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RemitoCreate],
    }).compileComponents();

    fixture = TestBed.createComponent(RemitoCreate);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
