// External Imports
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// Internal Imports
import { environment } from '../../../environments/environment';
import { catchError, map } from 'rxjs/operators';

// import { LoaderService } from '../components/loader/loader.service';

@Injectable({
  providedIn: 'root'
})

export class CommonAPIService {
  // Default Parameters
  httpDefaults: HTTPParam = {
    apiName: null,
    parameterObject: {},
    methodType: 'post',
    showLoading: true,
    isCachable: false,
    originKey: 'base_url',
    isOffline: true
  }

  // Tracking API Calls
  apiInProcess: any = []

  // Constructor
  constructor(private http: HttpClient) { }

  /**
   * POST REQUEST TO THE HTTP USING Observable
   * @param {string} apiName - API Name 
   * @param {*} [parameterObject] - Parameter to pass in request (By Default = {})
   * @returns {Observable<any>} 
   * @memberof CommonAPIService
   */
  private post(apiName: string, parameterObject?: any, showLoading?: boolean, isCachable?: boolean, originKey?: string, isOffline?: boolean): Observable<any> {
    return this.http.post(`${environment[originKey] + apiName}`, parameterObject).pipe(map((res: any) => {
      if (res) {
        return res;
      } else {
        return {};
      }
    })
    )
  }
  /**
 * POST REQUEST TO THE HTTP USING Observable
 * @param {string} apiName - API Name 
 * @param {*} [parameterObject] - Parameter to pass in request (By Default = {})
 * @returns {Observable<any>} 
 * @memberof CommonAPIService
 */
  private put(apiName: string, parameterObject?: any, showLoading?: boolean, isCachable?: boolean, originKey?: string, isOffline?: boolean): Observable<any> {
    return this.http.put(`${environment[originKey] + apiName}`, parameterObject).pipe(map((res: any) => {
      if (res) {
        return res;
      } else {
        return {};
      }
    })
    )
  }
  
  private patch(apiName: string, parameterObject?: any, showLoading?: boolean, isCachable?: boolean, originKey?: string, isOffline?: boolean): Observable<any> {
    return this.http.patch(`${environment[originKey] + apiName}`, parameterObject).pipe(map((res: any) => {
      if (res) {
        return res;
      } else {
        return {};
      }
    })
    )
  }
  /**
* POST REQUEST TO THE HTTP USING Observable
* @param {string} apiName - API Name 
* @param {*} [parameterObject] - Parameter to pass in request (By Default = {})
* @returns {Observable<any>} 
* @memberof CommonAPIService
*/
  private delete(apiName: string, parameterObject?: any, showLoading?: boolean, isCachable?: boolean, originKey?: string, isOffline?: boolean, data?: any): Observable<any> {
    let queryParams = this.getQueryParams(data);
    return this.http.delete(`${environment[originKey] + apiName}` + `${queryParams}`, parameterObject).pipe(map((res: any) => {
      if (res) {
        return res;
      } else {
        return {};
      }
    })
    )
  }
  private getQueryParams(data) {
    let params = '';
    if (Object.keys(data).length > 0) {
      for (const key in data) {
        params = '/';
        if (Object.prototype.hasOwnProperty.call(data, key)) {
          params += `${data[key]}`;
        }
      }
    }
    return params;
  }
  /**
   * GET REQUEST TO THE HTTP USING Observable
   * @param {string} apiName - API Name 
   * @param {*} [parameterObject] - Parameter to pass in request (By Default = {})
   * @returns {Observable<any>} 
   * @memberof CommonAPIService
   */
  private get(url: string, showLoading?: boolean, isCachable?: boolean, originKey?: string, responseType?: string, isOffline?: boolean, data?: any): Observable<any> {
    let queryParams = this.getQueryParams(data);
    return this.http.get(`${environment[originKey] + url}` + `${queryParams}`).pipe(map((res: any) => {
      if (responseType === 'blob') {
        return res;
      } else if (res) {
        return res;
      } else {
        return {};
      }
    })
    )
  }

  /**
  * 
  * GET HTTP RESPONSE USING PROMISE
  * @param {string} apiName API NAME
  * @param {any} [parameterObject={}] 
  * @param {string} [methodType="post"] 
  * @param {boolean} [showLoading=true] 
  * @returns {*} 
  * @memberof CommonAPIService
  */
  public getPromiseResponse(httpParam: HTTPParam): Promise<any> {
    const parameters = Object.assign({}, this.httpDefaults, httpParam);
    // if (parameters.showLoading) {
    //   this.apiInProcess.push(parameters.apiName);
    //   //   this.loaderService.show();
    // }

    switch (parameters.methodType) {
      case 'post':
        return this.post(parameters.apiName, parameters.parameterObject, parameters.showLoading, parameters.isCachable, parameters.originKey, parameters.isOffline).toPromise();
      case 'put':
        return this.put(parameters.apiName, parameters.parameterObject, parameters.showLoading, parameters.isCachable, parameters.originKey, parameters.isOffline).toPromise();
      case 'delete':
        return this.delete(parameters.apiName, parameters.parameterObject, parameters.showLoading, parameters.isCachable, parameters.originKey, parameters.isOffline, parameters.parameterObject).toPromise();
      case 'get':
        return this.get(parameters.apiName, parameters.showLoading, parameters.isCachable, parameters.originKey, parameters.responseType, parameters.isOffline, parameters.parameterObject).toPromise();
      case 'patch':
        return this.patch(parameters.apiName, parameters.parameterObject, parameters.showLoading, parameters.isCachable, parameters.originKey, parameters.isOffline).toPromise();
      default:
        return this.get(parameters.apiName, parameters.showLoading, parameters.isCachable, parameters.originKey, parameters.responseType, parameters.isOffline).toPromise();
    }
  }

  public getObservableResponse(httpParam: HTTPParam): Observable<any> {
    const parameters = Object.assign({}, this.httpDefaults, httpParam);
    switch (parameters.methodType) {
      case 'post':
        return this.post(parameters.apiName, parameters.parameterObject, parameters.showLoading, parameters.isCachable, parameters.originKey, parameters.isOffline);
      case 'put':
        return this.put(parameters.apiName, parameters.parameterObject, parameters.showLoading, parameters.isCachable, parameters.originKey, parameters.isOffline);
      case 'delete':
        return this.delete(parameters.apiName, parameters.parameterObject, parameters.showLoading, parameters.isCachable, parameters.originKey, parameters.isOffline, parameters.parameterObject);
      case 'get':
        return this.get(parameters.apiName, parameters.showLoading, parameters.isCachable, parameters.originKey, parameters.responseType, parameters.isOffline, parameters.parameterObject);
      case 'patch':
        return this.patch(parameters.apiName, parameters.parameterObject, parameters.showLoading, parameters.isCachable, parameters.originKey, parameters.isOffline);
      default:
        return this.get(parameters.apiName, parameters.showLoading, parameters.isCachable, parameters.originKey, parameters.responseType, parameters.isOffline);
    }
  }
}


export interface HTTPParam {
  apiName: string,
  parameterObject?: any;
  methodType?: any;
  showLoading?: boolean;
  isCachable?: boolean;
  originKey?: string;
  responseType?: string;
  isOffline?: boolean
};