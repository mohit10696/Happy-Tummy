import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RecipeRecommendationRoutingModule } from './recipe-recommendation-routing.module';
import { RecipeRecommendationComponent } from './recipe-recommendation.component';
import {FormsModule} from "@angular/forms";
import { FilterByIngredientsPipe } from './filter-by-ingredients.pipe';
import { FilterBySearchPipe } from './filter-by-search.pipe';


@NgModule({
  declarations: [RecipeRecommendationComponent, FilterByIngredientsPipe, FilterBySearchPipe],
    imports: [
        CommonModule,
        RecipeRecommendationRoutingModule,
        FormsModule
    ]
})
export class RecipeDetailModule { }
