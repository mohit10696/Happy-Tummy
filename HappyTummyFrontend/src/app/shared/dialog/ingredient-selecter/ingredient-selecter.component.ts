import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CdkDragDrop, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';
import { Router } from '@angular/router';

@Component({
  selector: 'app-ingredient-selecter',
  templateUrl: './ingredient-selecter.component.html',
  styleUrls: ['./ingredient-selecter.component.scss']
})
export class IngredientSelecterComponent implements OnInit {
  title = 'Ingredient Selecter';
  filteredIngredients = [];
  ingredients = [];
  search = '';
  selectedIngredients = [];
  constructor(public dialogRef: MatDialogRef<IngredientSelecterComponent>, @Inject(MAT_DIALOG_DATA) public data: any, private route: Router) { }
  mealPreference: any = [{
    name: 'Vegetarian',
    value: 'veg',
    checked: false
  }, {
    name: 'Vegan',
    value: 'vegan',
    checked: false
  }, {
    name: 'Non-Vegetarian',
    value: 'nonveg',
    checked: false
  }];
  selectedMealPreference: any = [];


  ngOnInit(): void {
    if (this.data) {
      console.log(this.data);
      this.ingredients = this.data.ingredients.map(ingredient => ingredient);
      this.filter();
    }
  }

  drop(event: CdkDragDrop<string[]>) {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      transferArrayItem(
        event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex,
      );
    }
  }

  drop2(event: CdkDragDrop<string[]>) {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      transferArrayItem(
        event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex,
      );
    }
  }

  filter() {
    if (this.search == '' || this.search == null) {
      this.filteredIngredients = this.ingredients;
      return;
    }
    this.filteredIngredients = this.ingredients.filter(ingredient => ingredient.plain_ingredient.toLowerCase().includes(this.search.toLowerCase()));
  }

  save() {
    this.route.navigate(['/pages/recipe-recommendation'], {
      queryParams: {
        ingredients: this.selectedIngredients.map((item) => {
          return item.plain_ingredient;
        }).join('||'),
        mealPreference: this.selectedMealPreference.map((item) => {
          return item.value;
        }).join('||')
      },
    });
    this.dialogRef.close({
      ingredients: this.selectedIngredients,
      mealPreference: this.selectedMealPreference
    });

  }

}

