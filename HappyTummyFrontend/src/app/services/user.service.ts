import { Injectable } from '@angular/core';
import { CommonAPIService } from '../shared/services/common-api.service';
import { APINAME } from '../shared/constants/api.constant';
import { Observable, tap } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { AuthenticationService } from './authentication.service';


@Injectable({
  providedIn: 'root'
})
export class UserService {
  baseUrl = environment.API_URL + APINAME.USER;
  constructor(private commonAPIService: CommonAPIService, private httpClient: HttpClient, private authenticationService: AuthenticationService) { }

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

  updateUser(followingId: number, reqBody): Observable<any> {
    return this.commonAPIService.getObservableResponse({
      originKey: 'API_URL',
      apiName: APINAME.USER + '/updateProfile/' + followingId,
      parameterObject: reqBody,
      methodType: 'patch',
    }).pipe(tap(res => {
      if (res.status == 'success') {
        localStorage.setItem('user', JSON.stringify(res.data));
        this.authenticationService.user.next(res.data); 
      }
    }));
  }

  updateUserProfile(id: number, formData: FormData) {
    const headers = new HttpHeaders();
    headers.append('Content-Type', 'multipart/form-data');
    return this.httpClient.patch(`${this.baseUrl}/updateProfileImage/${id}`, formData, { headers });
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
