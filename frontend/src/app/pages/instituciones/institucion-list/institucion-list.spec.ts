import { ComponentFixture, TestBed } from '@angular/core/testing';
import { InstitucionList } from './institucion-list';

describe('InstitucionList', () => {
  let component: InstitucionList;
  let fixture: ComponentFixture<InstitucionList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InstitucionList],
    }).compileComponents();

    fixture = TestBed.createComponent(InstitucionList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
