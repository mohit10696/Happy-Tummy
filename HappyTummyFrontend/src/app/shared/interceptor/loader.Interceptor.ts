import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { catchError, finalize, tap } from 'rxjs/operators';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { LoaderService } from 'src/app/services/loader.service';
import { UserService } from 'src/app/services/user.service';

@Injectable()
export class LoaderInterceptor implements HttpInterceptor {

  private count = 0;

  constructor(private loaderService: LoaderService,private toasterService:ToastrService,private authenticationService: AuthenticationService) { }
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (this.count === 0) {
      this.loaderService.setHttpProgressStatus(true);
    }
    if(this.authenticationService.user.value){     
      req = req.clone({
        setHeaders: {
          Authorization: `Bearer ${this.authenticationService.token}`
        }
      });
    }
    this.count++;
    return next.handle(req).pipe(
      tap( (res:any) => {
        if(res.status == 'success'){
          this.toasterService.success(res.message || 'success');
        }
      }),
      catchError((error) => {
        this.toasterService.error(error?.error?.message || error?.message);
        throw error;
      }),
      finalize(() => {
        this.count--;
        if (this.count === 0) {
          this.loaderService.setHttpProgressStatus(false);
        }
      })
    );
  }
}