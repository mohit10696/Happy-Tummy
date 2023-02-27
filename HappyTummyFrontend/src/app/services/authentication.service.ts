import { Injectable } from '@angular/core';
import {CommonAPIService} from "../shared/services/common-api.service";
import {APINAME} from "../shared/constants/api.constant";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private commonAPIService: CommonAPIService) {}

   login(reqBody : any){
    return this.commonAPIService.getObservableResponse({
      originKey: 'API_URL',
      apiName: APINAME.USER + "/login",
      parameterObject : reqBody,
      methodType: 'post',
    });
  }

   signup(reqBody : any){
    return this.commonAPIService.getObservableResponse({
      originKey: 'API_URL',
      apiName: APINAME.USER + "/signup",
      parameterObject : reqBody,
      methodType: 'post',
    });
  }
}
