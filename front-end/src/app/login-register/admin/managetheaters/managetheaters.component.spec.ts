import {async, ComponentFixture, TestBed} from "@angular/core/testing";

import {ManagetheatersComponent} from "./managetheaters.component";

describe("ManagetheatersComponent", () => {
  let component: ManagetheatersComponent;
  let fixture: ComponentFixture<ManagetheatersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ManagetheatersComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManagetheatersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
