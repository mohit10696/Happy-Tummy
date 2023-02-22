import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RecipeDetailRoutingModule } from './recipe-detail-routing.module';
import { RecipeDetailComponent } from './recipe-detail.component';


@NgModule({
  declarations: [RecipeDetailComponent],
  imports: [
    CommonModule,
    RecipeDetailRoutingModule
  ]
})
export class RecipeDetailModule { }
