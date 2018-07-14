import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagemoviesComponent } from './managemovies.component';

describe('ManagemoviesComponent', () => {
  let component: ManagemoviesComponent;
  let fixture: ComponentFixture<ManagemoviesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ManagemoviesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManagemoviesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
