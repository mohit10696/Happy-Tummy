import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './layout/header/header.component';
import {RouterModule} from "@angular/router";
import { FooterComponent } from './layout/footer/footer.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { NgxSpinnerModule } from 'ngx-spinner';
import { LoaderInterceptor } from './shared/interceptor/loader.Interceptor';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
  ],
  imports: [
    BrowserModule,
    NgxSpinnerModule.forRoot({ type: 'ball-grid-pulse' }),
    RouterModule,
    HttpClientModule,
    BrowserAnimationsModule,
    AppRoutingModule
  ],
  providers: [{ 
    provide: HTTP_INTERCEPTORS, useClass: LoaderInterceptor, multi:true
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
