import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Url } from './url.component';

describe('Url', () => {
  let component: Url;
  let fixture: ComponentFixture<Url>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Url],
    }).compileComponents();

    fixture = TestBed.createComponent(Url);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
