import { Component, Inject } from '@angular/core';
import { FormArray, FormControl, FormGroup } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-recipe-upload-dialog',
  templateUrl: './recipe-upload-dialog.component.html',
  styleUrls: ['./recipe-upload-dialog.component.scss']
})
export class RecipeUploadDialogComponent {
  @Inject(MAT_DIALOG_DATA) private data: any;
  recipeForm:FormGroup;
  private dialogRef: MatDialogRef<any>;

  constructor(){
    this.initForm();
  }

  initForm() {
    this.recipeForm = new FormGroup({
      recipe_name: new FormControl(),
      recipe_description: new FormControl(),
      steps: new FormControl(),
      ingredients: new FormArray([]),
    });
  }

  onClose(){
    this.dialogRef.close(true);
  }

  onSubmit(){
    console.log(this.recipeForm.value);
  }

  get getIngredients() : FormArray {
    return this.recipeForm.get("ingredients") as FormArray
  }
  addIngredient(name : string){
    if(name == null || name == ""){
      return;
    }
    this.getIngredients.push(new FormControl(name));
  }

  onFileSelected(event){
    console.log(event);
  }
}
