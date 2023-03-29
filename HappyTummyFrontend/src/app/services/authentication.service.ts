import { Injectable } from '@angular/core';
import { CommonAPIService } from "../shared/services/common-api.service";
import { APINAME } from "../shared/constants/api.constant";
import { tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  user: any;
  token: string;
  userFollowers: any[];
  userFollowings: any[];
  constructor(private commonAPIService: CommonAPIService) {
    this.user = JSON.parse(localStorage.getItem('user'));
    if (this.user) {
      this.user = this.user.user;
    }
    this.token = localStorage.getItem('token');
  }

  fetchUserFollowers() {
    return this.commonAPIService.getObservableResponse({
      originKey: 'API_URL',
      apiName: APINAME.USERS + "/followers",
      methodType: 'get',
    }).pipe(tap(response => {
      this.userFollowers = response.data;
    }));
  }

  fetchUserFollowings() {
    return this.commonAPIService.getObservableResponse({
      originKey: 'API_URL',
      apiName: APINAME.USERS + "/following",
      methodType: 'get',
    }).pipe(tap(response => {
      this.userFollowings = response.data;
    }));
  }

    login(reqBody : any){
      return this.commonAPIService.getObservableResponse({
        originKey: 'API_URL',
        apiName: APINAME.USER + "/login",
        parameterObject: reqBody,
        methodType: 'post',
      }).pipe(tap(res => {
        localStorage.setItem('user', JSON.stringify(res.data.user));
        localStorage.setItem('token', res.data.token);
      }));
    }

    signup(reqBody : any){
      return this.commonAPIService.getObservableResponse({
        originKey: 'API_URL',
        apiName: APINAME.USER + "/signup",
        parameterObject: reqBody,
        methodType: 'post',
      });
    }
  }
