import { Component, OnInit } from '@angular/core';
import {RecipeDetailComponent} from "../../pages/recipe-detail/recipe-detail.component";
import {RecipeUploadDialogComponent} from "../../shared/dialog/recipe-upload-dialog/recipe-upload-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit{
  isLoggedIn = false;
  query = '';

  constructor(private router : Router,private dialog: MatDialog,private activatedRoute : ActivatedRoute){}

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
  
  search(){
    let queryParams = this.activatedRoute.snapshot.queryParams;
    queryParams = {
      ...queryParams,
      query: this.query
    }
    this.router.navigate(['/pages/recipe-recommendation'],{queryParams});
  }

}
