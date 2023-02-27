import { Injectable } from '@angular/core';
import {CommonAPIService} from "../shared/services/common-api.service";
import {APINAME} from "../shared/constants/api.constant";

@Injectable({
  providedIn: 'root'
})
export class IngredientService {

  constructor(private commonAPIService: CommonAPIService) {}

  getAllIngredients(reqBody){
    return this.commonAPIService.getObservableResponse({
      originKey: 'API_URL',
      apiName: APINAME.GET_INGREDIENTS + "?" + new URLSearchParams(reqBody).toString(),
      methodType: 'get',
    });
  }
}
