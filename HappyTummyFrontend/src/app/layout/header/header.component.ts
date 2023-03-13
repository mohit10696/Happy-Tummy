import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {RecipeDetailComponent} from "../../pages/recipe-detail/recipe-detail.component";
import {RecipeUploadDialogComponent} from "../../shared/dialog/recipe-upload-dialog/recipe-upload-dialog.component";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit{
  isLoggedIn = false;

  constructor(private router : Router,private dialog: MatDialog){}
  ngOnInit(): void {
    this.isLoggedIn = localStorage.getItem('user') ? true : false;
  }

  onLogout(){
    localStorage.clear();
    this.router.navigateByUrl('/authentication/login');
  }

  onUpload(){
    const dialogRef = this.dialog.open(RecipeUploadDialogComponent,{ height: '80%',
      panelClass: 'mat-dialog-container',
      width: '570px'});
  }

}
