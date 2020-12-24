import {async, ComponentFixture, TestBed} from "@angular/core/testing";
import {MovieandshowComponent} from "./movieandshow.component";

describe("MovieandshowComponent", () => {
  let component: MovieandshowComponent;
  let fixture: ComponentFixture<MovieandshowComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [MovieandshowComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MovieandshowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
