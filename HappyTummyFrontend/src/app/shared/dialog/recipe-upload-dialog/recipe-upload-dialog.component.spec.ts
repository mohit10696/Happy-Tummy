import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecipeUploadDialogComponent } from './recipe-upload-dialog.component';

describe('RecipeUploadDialogComponent', () => {
  let component: RecipeUploadDialogComponent;
  let fixture: ComponentFixture<RecipeUploadDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RecipeUploadDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RecipeUploadDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
