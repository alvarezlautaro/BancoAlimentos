import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DonacionDetails } from './donacion-details';

describe('DonacionDetails', () => {
  let component: DonacionDetails;
  let fixture: ComponentFixture<DonacionDetails>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DonacionDetails],
    }).compileComponents();

    fixture = TestBed.createComponent(DonacionDetails);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
