import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { APINAME } from '../shared/constants/api.constant';
import { CommonAPIService } from '../shared/services/common-api.service';
import { AuthenticationService } from './authentication.service';

@Injectable({
  providedIn: 'root',
})
export class RecipesService {
  baseUrl = environment.API_URL + APINAME.GET_RECIPES;

  constructor(private commonAPIService: CommonAPIService,private httpClient:HttpClient,private authenticationService: AuthenticationService) {}

  getTodaysPick(reqBody) {
    return this.commonAPIService.getObservableResponse({
      originKey: 'API_URL',
      apiName: APINAME.GET_RECIPES + "?" + new URLSearchParams(reqBody).toString(),
      methodType: 'get',
    });
  }

  uploadRecipeImage(id: number, formData: FormData) {
    const headers = new HttpHeaders();
    headers.append('Content-Type', 'multipart/form-data');
    return this.httpClient.post(`${this.baseUrl}/upload/${id}`, formData, { headers });
  }

  findById(id){
    return this.commonAPIService.getObservableResponse({
      originKey: 'API_URL',
      apiName: APINAME.GET_RECIPES + "/" + id,
      methodType: 'get',
    });
  }

  addNewRecipe(reqBody) {
    const user = this.authenticationService.user;
    return this.commonAPIService.getObservableResponse({
      originKey: 'API_URL',
      apiName: APINAME.GET_RECIPES + "/" + user.id,
      parameterObject: reqBody,
      methodType: 'post',
    });
  }

  addReview(reviewData: FormData,recipeId : string) {
    const user = this.authenticationService.user;
    const headers = new HttpHeaders();
    headers.append('Content-Type','multipart/form-data');
    return this.httpClient.post(`${environment.API_URL}/reviews/${recipeId}/${user.id}`, reviewData, { headers });
  }
}
