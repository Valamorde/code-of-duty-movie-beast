import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageshowsComponent } from './manageshows.component';

describe('ManageshowsComponent', () => {
  let component: ManageshowsComponent;
  let fixture: ComponentFixture<ManageshowsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ManageshowsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageshowsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
