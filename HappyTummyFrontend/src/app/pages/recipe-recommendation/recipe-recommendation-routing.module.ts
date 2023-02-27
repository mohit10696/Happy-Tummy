import { Component, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {RecipeRecommendationComponent} from "./recipe-recommendation.component";

const routes: Routes = [
  {
    path: "",
    component: RecipeRecommendationComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RecipeRecommendationRoutingModule { }
