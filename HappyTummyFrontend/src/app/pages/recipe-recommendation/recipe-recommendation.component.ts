import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { debounceTime, distinctUntilChanged, tap } from 'rxjs';
import { RecipesService } from 'src/app/services/recipes.service';
import { IngredientService } from '../../services/ingredient.service';
import { MatDialog } from '@angular/material/dialog';
import { IngredientSelecterComponent } from 'src/app/shared/dialog/ingredient-selecter/ingredient-selecter.component';

@Component({
  selector: 'app-recipe-recommendation',
  templateUrl: './recipe-recommendation.component.html',
  styleUrls: ['./recipe-recommendation.component.scss'],
})
export class RecipeRecommendationComponent implements OnInit {
  recipeId;
  recipeDetails: any;
  filterObj = {
    mealPreference: [],
    ingredients: [],
    q: '',
    length: 12,
    pageIndex: 0,
  };
  ingredients: any;
  filteredIngredients: any;
  searchIngredient: any;
  ingredient: any;
  filterByIngredient: FormControl = new FormControl();
  filterByMealPreference: FormControl = new FormControl();

  mealPreference = [{
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
  }]
  recipes: any;

  constructor(
    private recipeService: RecipesService,
    private activatedRoutes: ActivatedRoute,
    private route: Router,
    private ingredientsService: IngredientService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.fetchIngredients();
    this.activatedRoutes.queryParams.subscribe((params) => {
      if (params['query']) {
        this.filterObj.q = params['query'];
      }
      if (params['pageIndex']) {
        this.filterObj.pageIndex = +params['pageIndex'];
      }
      if (params['ingredients']) {
        this.filterObj.ingredients = params['ingredients'].split('||');
      }
      if (params['mealPreference']) {
        this.filterObj.mealPreference = params['mealPreference'].split('||');
        this.mealPreference.forEach((item) => {
          if (this.filterObj.mealPreference.includes(item.value)) {
            item.checked = true;
          }
        });
      }
      this.fetchRecipe();
    });


    this.filterByIngredient.valueChanges.pipe(
      debounceTime(500),
      distinctUntilChanged(),
      tap(value => {
        this.filterIngredientList(value);
      })
    ).subscribe((value) => { });
  }

  fetchRecipe() {
    const reqBody: any = Object.entries(this.filterObj).reduce((a, [k, v]) => (v ? (a[k] = v, a) : a), {});
    if (reqBody.ingredients.length == 0) {
      delete reqBody.ingredients;
    } else {
      reqBody.ingredients = reqBody.ingredients.join('&&');
    }
    if (reqBody.mealPreference.length == 0) {
      delete reqBody.mealPreference;
    }

    this.recipeService.getTodaysPick(reqBody).subscribe((res) => {
      if (res.status === 'success') {
        this.recipes = res.data.map(data => {
          return {
            ...data,
            ingredients: data?.ingredients?.split(',').map((item) => {
              return item.replaceAll('[', '').replaceAll(']', '').replaceAll('"', '').replaceAll("'", "").trim();
            }).filter((item) => {
              return item.length > 0;
            }),
          }
        });
      }
      console.log(this.recipes);

    });
  }

  onMealPrefrenceStateChange() {
    setTimeout(() => {
      this.filterObj.mealPreference = this.mealPreference.filter((item) => {
        return item.checked;
      }).map((item) => {
        return item.value;
      });
      this.changeRoute();
    });
  }

  private fetchIngredients() {
    let reqBody = {
      length: 8,
    };

    this.ingredientsService.getAllIngredients(reqBody).subscribe((res) => {
      if (res.status === 'success') {
        this.ingredients = res.data.map((item) => {
          return {
            plain_ingredient: item,
            checked: this.filterObj.ingredients.includes(item)
          }
        });
        this.filteredIngredients = this.ingredients;
        this.openIngredientSelecter();
      }
    });
  }

  openIngredientSelecter() {    
    if(Object.keys(this.activatedRoutes.snapshot.queryParams).length > 0){
      return;
    }
    this.dialog.open(IngredientSelecterComponent, {
      width: '600px',
      height: '500px',
      data: {
        ingredients: this.ingredients,
        onIngredientStateChange: this.onIngredientStateChange.bind(this),
        filterIngredientList: this.filterIngredientList.bind(this),
        searchIngredient: this.searchIngredient,
        ingredient: this.ingredient,
      },
    }).afterClosed().subscribe((res) => {
    });
  }

  onIngredientStateChange() {
    setTimeout(() => {
      this.filterObj.ingredients = this.ingredients.filter((item) => {
        return item.checked;
      }).map((item) => {
        return item.plain_ingredient;
      });
      this.changeRoute();
    });
  }

  filterIngredientList(value) {
    console.log(value);

    if (!value) {
      this.filteredIngredients = this.ingredients;
      return;
    }
    this.filteredIngredients = this.ingredients.filter((item) => {
      return item.plain_ingredient.toLowerCase().includes(value.toLowerCase());
    });
  }

  onNextPage() {
    this.filterObj.pageIndex++;
    this.changeRoute();
  }
  onPreviousPage() {
    this.filterObj.pageIndex--;
    this.changeRoute();
  }
  changeRoute() {
    this.route.navigate(['/pages/recipe-recommendation'], {
      queryParams: {
        q: this.filterObj.q,
        pageIndex: this.filterObj.pageIndex,
        ingredients: this.filterObj.ingredients.join('||'),
        mealPreference: this.filterObj.mealPreference.join('||'),
      },
    });
    this.scrollToTop();

  }
  scrollToTop() {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }
  removeQ() {
    this.filterObj.q = '';
    this.changeRoute();
  }
  removeMealPreference(mf) {
    this.mealPreference.forEach((item) => {
      if (item.value == mf) {
        item.checked = false;
      }
    });
    this.onMealPrefrenceStateChange();
  }
  removeIngredient(i) {
    this.ingredients.forEach((item) => {
      if (item.plain_ingredient == i) {
        item.checked = false;
      }
    });
    this.onIngredientStateChange();
  }
}
