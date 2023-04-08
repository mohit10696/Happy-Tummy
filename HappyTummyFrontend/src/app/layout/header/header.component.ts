import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from 'src/app/pages/user-profile/home/home.component';
import { RecipeUploadDialogComponent } from 'src/app/shared/dialog/recipe-upload-dialog/recipe-upload-dialog.component';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})


export class HeaderComponent implements OnInit {
  isLoggedIn = false;
  query = '';
  user;

  constructor(private router: Router, private dialog: MatDialog, private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.isLoggedIn = localStorage.getItem('user') ? true : false;
    if (this.isLoggedIn) {
      this.user = JSON.parse(localStorage.getItem('user'));
      console.log(this.user);
      this.user = this.user;
    }
    setInterval(()=>{
      console.log(this.user);
      
    },2000);
  }

  onLogout() {
    localStorage.clear();
    this.router.navigateByUrl('/authentication/login');
  }

  onUpload() {
    const dialogRef = this.dialog.open(RecipeUploadDialogComponent, {
      height: '80%',
      panelClass: 'mat-dialog-container',
      width: '570px'
    });
  }

  search() {
    let queryParams = this.activatedRoute.snapshot.queryParams;
    queryParams = {
      ...queryParams,
      query: this.query
    }
    this.router.navigate(['/pages/recipe-recommendation'], { queryParams });
  }

}
