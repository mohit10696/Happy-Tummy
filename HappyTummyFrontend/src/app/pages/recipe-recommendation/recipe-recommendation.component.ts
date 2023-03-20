import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RecipesService } from 'src/app/services/recipes.service';
import { IngredientService } from '../../services/ingredient.service';

@Component({
  selector: 'app-recipe-recommendation',
  templateUrl: './recipe-recommendation.component.html',
  styleUrls: ['./recipe-recommendation.component.scss'],
})
export class RecipeRecommendationComponent implements OnInit {
  recipeId;
  recipeDetails: any;
  filterObj = {
    mealPreference: '',
    ingredients: [],
    q: '',
    length: 12,
    pageIndex: 0,
  };
  ingredients: any;
  searchIngredient: any;
  ingredient: any;

  mealPreference = ['Vegetarian', 'Non vegetarian', 'Vegan'];
  recipes: any;
  constructor(
    private recipeService: RecipesService,
    private activatedRoutes: ActivatedRoute,
    private route : Router,
    private ingredientsService: IngredientService
  ) {}

  ngOnInit(): void {
    this.activatedRoutes.queryParams.subscribe((params) => {
      if (params['query']) {
        this.filterObj.q = params['query'];
      }
      if(params['pageIndex']) {
        this.filterObj.pageIndex = +params['pageIndex'];
      }
      if(params['ingredients']) {
        this.filterObj.ingredients = params['ingredients'].split('||');
      }
      this.fetchRecipe();
      this.fetchIngredients();
    });
  }

  fetchRecipe() {
    const reqBody:any = Object.entries(this.filterObj).reduce((a,[k,v]) => (v ? (a[k]=v, a) : a), {});
    if(reqBody.ingredients.length == 0) {
      delete reqBody.ingredients;
    }else{
      reqBody.ingredients = reqBody.ingredients.join('&&');
    }
    this.recipeService.getTodaysPick(reqBody).subscribe((res) => {
      if (res.status === 'success') {
        this.recipes = res.data.map(data => {
          return {
            ...data,
            ingredients: data.ingredients.split(',').map((item) => {
              return item.replaceAll('[', '').replaceAll(']', '').replaceAll('"', '').replaceAll("'","").trim();
              }).filter((item) => {
                return item.length > 0;
                }),
          }
        });
      }
      console.log(this.recipes);

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
            checked : this.filterObj.ingredients.includes(item)
          }
        });
      }
    });
  }

  onIngredientStateChange(){
    setTimeout(() => {
      this.filterObj.ingredients = this.ingredients.filter((item) => {
        return item.checked;
      }).map((item) => {
        return item.plain_ingredient;
      });
      this.changeRoute();
    });
  }

  filterIngredientList(){
    this.ingredients = this.ingredients.filter((item) => {
      return item.name.toLowerCase().includes(this.searchIngredient.toLowerCase());
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
        ingredients: this.filterObj.ingredients.join('||')
      },
    });
    this.scrollToTop();
  }
  scrollToTop() {
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }
}
