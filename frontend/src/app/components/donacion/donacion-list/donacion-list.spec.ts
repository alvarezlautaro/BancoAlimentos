import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DonacionList } from './donacion-list';

describe('DonacionList', () => {
  let component: DonacionList;
  let fixture: ComponentFixture<DonacionList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DonacionList],
    }).compileComponents();

    fixture = TestBed.createComponent(DonacionList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
