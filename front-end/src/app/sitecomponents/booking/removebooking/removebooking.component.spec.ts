import {async, ComponentFixture, TestBed} from "@angular/core/testing";

import {RemovebookingComponent} from "./removebooking.component";

describe("RemovebookingComponent", () => {
  let component: RemovebookingComponent;
  let fixture: ComponentFixture<RemovebookingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [RemovebookingComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RemovebookingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
