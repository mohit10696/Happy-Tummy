import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IngredientSelecterComponent } from './ingredient-selecter.component';

describe('IngredientSelecterComponent', () => {
  let component: IngredientSelecterComponent;
  let fixture: ComponentFixture<IngredientSelecterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IngredientSelecterComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IngredientSelecterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
