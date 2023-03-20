import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from 'src/app/pages/user-profile/home/home.component';

// const routes: Routes = [
//   { path: 'home', component: HomeComponent }
// ];

const routes: Routes = [
  { path: 'home/:id', component: HomeComponent }
];

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
