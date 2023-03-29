import { Injectable } from '@angular/core';
import { CommonAPIService } from '../shared/services/common-api.service';
import { APINAME } from '../shared/constants/api.constant';
import { Observable } from 'rxjs';


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

  followUser(followingId: number): Observable<any> {
    return this.commonAPIService.getObservableResponse({
      originKey: 'API_URL',
      apiName: APINAME.USERS + '/follow/' + followingId,
      methodType: 'post',
    });
  }

  unfollowUser(followingId: number): Observable<any> {
    return this.commonAPIService.getObservableResponse({
      originKey: 'API_URL',
      apiName: APINAME.USERS + '/unfollow/' + followingId,
      methodType: 'post',
    });
  }

  getFollowersList(userId: number): Observable<any> {
    return this.commonAPIService.getObservableResponse({
      originKey: 'API_URL',
      apiName: APINAME.USERS + '/' + userId + '/followers',
      methodType: 'get',
    });
  }

  getFollowingList(userId: number): Observable<any> {
    return this.commonAPIService.getObservableResponse({
      originKey: 'API_URL',
      apiName: APINAME.USERS + '/' + userId + '/following',
      methodType: 'get',
    });
  }

  getFollowersCount(userId: number): Observable<number> {
    return this.commonAPIService.getObservableResponse({
      originKey: 'API_URL',
      apiName: APINAME.USERS + '/' + userId + '/followers/count',
      methodType: 'get',
    });
  }

  getFollowingCount(userId: number): Observable<number> {
    return this.commonAPIService.getObservableResponse({
      originKey: 'API_URL',
      apiName: APINAME.USERS + '/' + userId + '/following/count',
      methodType: 'get',
    });
  }
}
