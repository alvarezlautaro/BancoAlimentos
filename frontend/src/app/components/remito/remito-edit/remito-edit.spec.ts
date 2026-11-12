import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RemitoEdit } from './remito-edit';

describe('RemitoEdit', () => {
  let component: RemitoEdit;
  let fixture: ComponentFixture<RemitoEdit>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RemitoEdit],
    }).compileComponents();

    fixture = TestBed.createComponent(RemitoEdit);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
