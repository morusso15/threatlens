import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UrlComponent } from './url.component';

describe('Url', () => {
  let component: UrlComponent;
  let fixture: ComponentFixture<UrlComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UrlComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(UrlComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
