import { Injectable } from '@angular/core';
import { CommonAPIService } from '../shared/services/common-api.service';
import { APINAME } from '../shared/constants/api.constant';


@Injectable({
  providedIn: 'root'
})
export class UserService {
  
  constructor(private commonAPIService: CommonAPIService) { }

  getUserProfile(username: string) {
    return this.commonAPIService.getObservableResponse({
      originKey: 'API_URL',
      apiName: APINAME.USER + '/getProfile/' + username,
      methodType: 'get',
    });
  }
}
