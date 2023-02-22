import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { APINAME } from '../shared/constants/api.constant';
import { CommonAPIService } from '../shared/services/common-api.service';

@Injectable({
  providedIn: 'root',
})
export class RecipesService {
  constructor(private commonAPIService: CommonAPIService) {}

  getTodaysPick(reqBody) {
    return this.commonAPIService.getObservableResponse({
      originKey: 'API_URL',
      apiName: APINAME.GET_RECIPES + "?" + new URLSearchParams(reqBody).toString(),
      methodType: 'get',
    });
  }

  findById(id){
    return this.commonAPIService.getObservableResponse({
      originKey: 'API_URL',
      apiName: APINAME.GET_RECIPES + "/" + id,
      methodType: 'get',
    });
  }
}
