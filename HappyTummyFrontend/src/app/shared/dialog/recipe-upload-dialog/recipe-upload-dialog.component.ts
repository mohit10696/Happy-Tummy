import { Component, Inject } from '@angular/core';
import { FormArray, FormControl, FormGroup } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { RecipesService } from 'src/app/services/recipes.service';

@Component({
  selector: 'app-recipe-upload-dialog',
  templateUrl: './recipe-upload-dialog.component.html',
  styleUrls: ['./recipe-upload-dialog.component.scss']
})
export class RecipeUploadDialogComponent {
  @Inject(MAT_DIALOG_DATA) private data: any;
  recipeForm: FormGroup;
  file : File;
  selectedImage: any;


  constructor(private recipesService: RecipesService,private dialogRef: MatDialogRef<RecipeUploadDialogComponent>) {
    this.initForm();
  }

  initForm() {
    this.recipeForm = new FormGroup({
      steps: new FormArray([]),
      ingredients: new FormArray([]),
      nutrition: new FormArray([]),
      tag: new FormArray([]),
      recipe: new FormGroup({
        intro: new FormControl(),
        name: new FormControl(),
        tip: new FormControl(),
        serve: new FormControl(),
        cookTime: new FormControl(),
        prepTime: new FormControl(),
        difficulty: new FormControl(),
      })
    });
  }

  onClose() {
    console.log(this.dialogRef);

    this.dialogRef.close(true);
  }

  onSubmit() {
    this.uploadRecipeInfo();
  }

  uploadRecipeInfo() {
    let reqBody = this.recipeForm.value;
    reqBody = {
      ...reqBody,
      recipe: {
        ...reqBody.recipe,
      }
    }
    this.recipesService.addNewRecipe(reqBody).subscribe(res => {
      if(this.file == null){
        this.onClose();
        return;
      }
      this.uploadRecipeImage(res.data.id);
    }
    );
  }

  uploadRecipeImage(id: number) {
    const formData = new FormData();
    formData.append('file', this.file);
    this.recipesService.uploadRecipeImage(id, formData).subscribe(res => {
      this.onClose();
    }
    );
  }

  get getIngredients(): FormArray {
    return this.recipeForm.get("ingredients") as FormArray
  }

  get getSteps(): FormArray {
    return this.recipeForm.get("steps") as FormArray
  }

  get gerNutrition(): FormArray {
    return this.recipeForm.get("nutrition") as FormArray
  }

  get getTag(): FormArray {
    return this.recipeForm.get("tag") as FormArray
  }

  get getIngredient(): FormArray {
    return this.recipeForm.get("ingredients") as FormArray
  }

  addStep(description: string) {
    if (description == null || description == "") {
      return;
    }
    this.getSteps.push(new FormGroup({
      position: new FormControl(this.getSteps.length),
      description: new FormControl(description)
    })
    );
  }

  addNutrition(nutrition: string) {
    if (nutrition == null || nutrition == "") {
      return;
    }
    this.gerNutrition.push(new FormGroup({
      nutrition: new FormControl(nutrition)
    })
    );
  }

  addTag(tag: string) {
    if (tag == null || tag == "") {
      return;
    }
    this.getTag.push(new FormGroup({
      tag: new FormControl(tag)
    })
    );
  }
  addIngredient(name: string) {
    if (name == null || name == "") {
      return;
    }
    this.getIngredient.push(new FormGroup({
      plain_ingredient: new FormControl(name)
    })
    );
  }

  onFileSelected(event) {
    const file = (event.target as HTMLInputElement).files?.[0];
    this.file = file;
    this.readImageFile(file);
  }

  readImageFile(file: File | null): void {
    if (!file) {
      return;
    }

    const reader = new FileReader();
    reader.onload = () => {
      const imageDataUrl = reader.result as string;
      this.selectedImage = imageDataUrl;
    };
    reader.readAsDataURL(file);
  }

  removeStep(index: number) {
    this.getSteps.removeAt(index);
  }

  removeNutrition(index: number) {
    this.gerNutrition.removeAt(index);
  }

  removeTag(index: number) {
    this.getTag.removeAt(index);
  }

  removeIngredient(index: number) {
    this.getIngredient.removeAt(index);
  }
}
