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
  veggiesItems:IRecipe[];
  pizzaItems:IRecipe[];

  constructor(private recipesService:RecipesService){}

  ngOnInit(): void {
    this.fetchTodayPick();
    this.fetchTopChickenItems();
    this.fetchVeggieItems();
    this.fetchPizzaItems();
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

  fetchVeggieItems(){
    let reqBody = {
      length: 4,
      tag : 'indian'
    }
    this.recipesService.getTodaysPick(reqBody).subscribe(res => {
      if(res.status === 'success'){
        this.veggiesItems = res.data;
      }
    });
  }

  fetchPizzaItems(){
    let reqBody = {
      length: 4,
      tag : 'pizza'
    }
    this.recipesService.getTodaysPick(reqBody).subscribe(res => {
      if(res.status === 'success'){
        this.pizzaItems = res.data;
      }
    });
  }

}
