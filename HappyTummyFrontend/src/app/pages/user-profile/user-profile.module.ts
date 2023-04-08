import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserProfileRoutingModule } from './user-profile-routing.module';
import { HomeComponent } from './home/home.component';
import { SettingsComponent } from './settings/settings.component';
import { ReactiveFormsModule } from '@angular/forms'; 

@NgModule({
  declarations: [
    HomeComponent,
    SettingsComponent
  ],
  imports: [
    CommonModule,
    UserProfileRoutingModule,
    ReactiveFormsModule,
  ]
})
export class UserProfileModule { }
