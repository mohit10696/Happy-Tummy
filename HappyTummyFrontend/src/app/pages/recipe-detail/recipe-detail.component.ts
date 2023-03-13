import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RecipesService } from 'src/app/services/recipes.service';

@Component({
  selector: 'app-recipe-detail',
  templateUrl: './recipe-detail.component.html',
  styleUrls: ['./recipe-detail.component.scss']
})
export class RecipeDetailComponent implements OnInit{

  recipeId;
  recipeDetails:any;
  constructor(private recipeService: RecipesService,private activatedRoutes : ActivatedRoute){}

  ngOnInit(): void {
    this.activatedRoutes.params.subscribe(params => {
      if(params['id']){
        this.recipeId = params['id'];
        this.fetchDetails();
      }
    });
  }

  fetchDetails() {
    this.recipeService.findById(this.recipeId).subscribe(
      res => {
        if(res.status === 'success'){
          this.recipeDetails = res.data;
          console.log(this.recipeDetails);
        }
      }
    );
  }

}
