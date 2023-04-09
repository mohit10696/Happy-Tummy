import { APP_INITIALIZER, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {RouterModule} from "@angular/router";
import { FooterComponent } from './layout/footer/footer.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { NgxSpinnerModule } from 'ngx-spinner';
import { LoaderInterceptor } from './shared/interceptor/loader.Interceptor';
import { ToastrModule } from 'ngx-toastr';
import { RecipeUploadDialogComponent } from './shared/dialog/recipe-upload-dialog/recipe-upload-dialog.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ImageViewerComponent } from './shared/dialog/image-viewer/image-viewer.component';
import { MapComponent } from './shared/dialog/map/map.component';
import { AgmCoreModule } from '@agm/core';
import { AuthenticationService } from './services/authentication.service';
import { ListUsersComponent } from './shared/dialog/list-users/list-users.component';
import { IngredientSelecterComponent } from './shared/dialog/ingredient-selecter/ingredient-selecter.component';
import {DragDropModule} from '@angular/cdk/drag-drop';
import { UserService } from './services/user.service';

export function initializeApp(authenticationService: AuthenticationService) {
  return () => {
    if(authenticationService.user.value){
      return Promise.all([authenticationService.fetchUserFollowers().toPromise(),authenticationService.fetchUserFollowings().toPromise(),authenticationService.getMyProfile().toPromise()]);
    }else{
      return Promise.resolve();
    }
  };
}

@NgModule({
  declarations: [
    AppComponent,
    RecipeUploadDialogComponent,
    ImageViewerComponent,
    MapComponent,
    ListUsersComponent,
    IngredientSelecterComponent,
  ],
  imports: [
    BrowserModule,
    DragDropModule,
    NgxSpinnerModule.forRoot({ type: 'ball-grid-pulse' }),
    RouterModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(), // ToastrModule added
    AppRoutingModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyD8GED_UGjPTaqX1UcI957lovSsquUUSPo',
      libraries: ['places']
    })
  ],
  providers: [{ 
    provide: HTTP_INTERCEPTORS, useClass: LoaderInterceptor, multi:true
  },
  AuthenticationService,
  {
    provide: APP_INITIALIZER,
    useFactory: initializeApp,
    deps: [AuthenticationService,UserService],
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
