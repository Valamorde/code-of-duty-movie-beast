import {async, ComponentFixture, TestBed} from "@angular/core/testing";

import {RemoveseatComponent} from "./removeseat.component";

describe("RemoveseatComponent", () => {
  let component: RemoveseatComponent;
  let fixture: ComponentFixture<RemoveseatComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [RemoveseatComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RemoveseatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
