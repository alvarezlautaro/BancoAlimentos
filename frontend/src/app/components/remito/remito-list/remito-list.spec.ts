import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RemitoList } from './remito-list';

describe('RemitoList', () => {
  let component: RemitoList;
  let fixture: ComponentFixture<RemitoList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RemitoList],
    }).compileComponents();

    fixture = TestBed.createComponent(RemitoList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
