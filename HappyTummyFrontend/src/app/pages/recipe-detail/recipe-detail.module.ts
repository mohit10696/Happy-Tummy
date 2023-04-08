import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RecipeDetailRoutingModule } from './recipe-detail-routing.module';
import { RecipeDetailComponent } from './recipe-detail.component';
import {FormsModule} from "@angular/forms";


@NgModule({
  declarations: [RecipeDetailComponent],
    imports: [
        CommonModule,
        RecipeDetailRoutingModule,
        FormsModule
    ]
})
export class RecipeDetailModule { }
