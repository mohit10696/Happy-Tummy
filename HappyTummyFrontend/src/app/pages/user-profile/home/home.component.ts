import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {
  username: string;
  userInformation: any;
  recpipeInformation: any[];
  constructor(private router: Router,private activatedRoute : ActivatedRoute,private userService: UserService){
    this.username = this.activatedRoute.snapshot.params?.['username'];
    if(this.username){
      this.fetchUserProfile(this.username);
    }
  }

  fetchUserProfile(username: string) {
    this.userService.getUserProfile(username).subscribe((response) => {
      if(response.status == 'success'){
        this.userInformation = response.data.user;
        this.recpipeInformation = response.data.recipes;
      }
    });
  }
}
