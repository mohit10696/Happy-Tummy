import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RecipesService } from 'src/app/services/recipes.service';
import {IngredientService} from "../../services/ingredient.service";

@Component({
  selector: 'app-recipe-recommendation',
  templateUrl: './recipe-recommendation.component.html',
  styleUrls: ['./recipe-recommendation.component.scss']
})
export class RecipeRecommendationComponent implements OnInit{

  recipeId;
  recipeDetails:any;
  searchIngredient: any;
  ingredients: any;
  ingredient: any;

  mealPreference = ['Vegetarian','Non vegetarian', 'Vegan']
  recipes: any;
  constructor(private recipeService: RecipesService,private activatedRoutes : ActivatedRoute,private ingredientsService:IngredientService){}

  ngOnInit(): void {
    this.activatedRoutes.params.subscribe(params => {
      if(params['id']){
        this.recipeId = params['id'];

      }
    });
    this.fetchRecipe();
    this.fetchIngredients();
  }

  fetchRecipe() {
    let reqBody = {
      length: 8
    }

    this.recipeService.getTodaysPick(reqBody).subscribe(
      res => {
        if(res.status === 'success'){
          this.recipes = res.data;
        }
      }
    );
  }

  private fetchIngredients() {
    let reqBody = {
      length: 8
    };

    this.ingredientsService.getAllIngredients(reqBody).subscribe(
      res => {
        if(res.status === 'success'){
          this.ingredients = res.data;
        }
      }
    );
  }
}
