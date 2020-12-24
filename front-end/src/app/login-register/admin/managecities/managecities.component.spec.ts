import {async, ComponentFixture, TestBed} from "@angular/core/testing";

import {ManagecitiesComponent} from "./managecities.component";

describe("ManagecitiesComponent", () => {
  let component: ManagecitiesComponent;
  let fixture: ComponentFixture<ManagecitiesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ManagecitiesComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManagecitiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
