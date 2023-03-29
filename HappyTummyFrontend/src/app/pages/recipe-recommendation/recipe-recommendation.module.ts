import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RecipeRecommendationRoutingModule } from './recipe-recommendation-routing.module';
import { RecipeRecommendationComponent } from './recipe-recommendation.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";


@NgModule({
  declarations: [RecipeRecommendationComponent],
    imports: [
        CommonModule,
        RecipeRecommendationRoutingModule,
        ReactiveFormsModule,
        FormsModule
    ]
})
export class RecipeDetailModule { }
