import { Component, OnInit } from '@angular/core';
import { IRecipe } from 'src/app/models/recipe.model';
import { RecipesService } from 'src/app/services/recipes.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit{
  todaysPick:IRecipe[];
  topChickenItems:IRecipe[];

  constructor(private recipesService:RecipesService){}

  ngOnInit(): void {
    this.fetchTodayPick();
    this.fetchTopChickenItems();
  }

  
  fetchTodayPick() {
    let reqBody = {
      length: 8
    }
    this.recipesService.getTodaysPick(reqBody).subscribe(res => {
      if(res.status === 'success'){
        this.todaysPick = res.data;
        console.log(this.todaysPick);
        
      }
    });
  }

  fetchTopChickenItems(){
    let reqBody = {
      length: 6,
      tag : 'chicken'
    }
    this.recipesService.getTodaysPick(reqBody).subscribe(res => {
      if(res.status === 'success'){
        this.topChickenItems = res.data;
      }
    });
  }

}
