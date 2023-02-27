import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

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
