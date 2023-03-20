import { Injectable } from '@angular/core';
import {CommonAPIService} from "../shared/services/common-api.service";
import {APINAME} from "../shared/constants/api.constant";
import { tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  user : any;
  constructor(private commonAPIService: CommonAPIService) {
    this.user = JSON.parse(localStorage.getItem('user'));
  }

   login(reqBody : any){
    return this.commonAPIService.getObservableResponse({
      originKey: 'API_URL',
      apiName: APINAME.USER + "/login",
      parameterObject : reqBody,
      methodType: 'post',
    }).pipe(tap(res => {
      localStorage.setItem('user',JSON.stringify(res.data));
      this.user = res.data;
    }));

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
