import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RecipesService } from 'src/app/services/recipes.service';
import { HttpClient } from '@angular/common/http';
import { ToastrService } from "ngx-toastr";

@Component({
  selector: 'app-recipe-detail',
  templateUrl: './recipe-detail.component.html',
  styleUrls: ['./recipe-detail.component.scss']
})
export class RecipeDetailComponent implements OnInit{

  recipeId;
  recipeDetails:any;
  isLoggedIn;
  reviewText: string;
  rating: number=0;
  image: File;
  selectedFiles: any;
  searchQuery:string;


  constructor(
    private recipeService: RecipesService,
    private activatedRoutes : ActivatedRoute,
    private toasterService: ToastrService,
    private http: HttpClient,
  ){}

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

  setRating(rating: number) {
    this.rating= rating;
  }

  onFileSelected(event: any) {
    this.image = event.target.files[0];
  }

  onSubmit() {
    if(localStorage.getItem('user')){
      this.isLoggedIn=true;
    }
    if(this.isLoggedIn){
      const reviewData = new FormData();
      reviewData.append('reviewText', this.reviewText);
      reviewData.append('rating', this.rating.toString());

      reviewData.append('image', this.image);
      reviewData.append('date', Date.now().toString());

      this.recipeService.addReview(reviewData,this.recipeId).subscribe((res:any) => {
        if(res.status == "success"){
          this.toasterService.success("Review added");
          this.ngOnInit();
        }
      })
    }

  }

  encodeSearchQueryUrl(): string {
    if (this.recipeDetails && this.recipeDetails.recipe && this.recipeDetails.recipe.name) {
      const searchQuery = encodeURIComponent(this.recipeDetails.recipe.name);
      return `https://www.youtube.com/results?search_query=${searchQuery}`;
    } else {
      return '';
    }
  }

}
