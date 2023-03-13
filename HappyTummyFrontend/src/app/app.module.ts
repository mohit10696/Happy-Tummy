import { NgModule } from '@angular/core';
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
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    RecipeUploadDialogComponent,
  ],
  imports: [
    BrowserModule,
    NgxSpinnerModule.forRoot({ type: 'ball-grid-pulse' }),
    RouterModule,
    HttpClientModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(), // ToastrModule added
    AppRoutingModule
  ],
  providers: [{ 
    provide: HTTP_INTERCEPTORS, useClass: LoaderInterceptor, multi:true
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
