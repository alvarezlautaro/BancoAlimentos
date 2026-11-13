import { ComponentFixture, TestBed } from '@angular/core/testing';
import { InstitucionDetail } from './institucion-detail';

describe('InstitucionDetail', () => {
  let component: InstitucionDetail;
  let fixture: ComponentFixture<InstitucionDetail>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InstitucionDetail],
    }).compileComponents();

    fixture = TestBed.createComponent(InstitucionDetail);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
