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

  constructor(private router : Router){}
  ngOnInit(): void {
    this.isLoggedIn = localStorage.getItem('user') ? true : false;
  }

  onLogout(){
    localStorage.clear();
    this.router.navigateByUrl('/authentication/login');
  }
  
}
