import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { UserService } from 'src/app/services/user.service';
import { ListUsersComponent } from 'src/app/shared/dialog/list-users/list-users.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {
  username: string;
  userInformation: any;
  recpipeInformation: any[];
  likes = 0;
  followers = [];
  totalRecipes = 0;
  following = [];
  isLoggedInUser = false;
  isLoggedin = false;
  isFollowing = false;
  constructor(private router: Router, private activatedRoute: ActivatedRoute,private dialog: MatDialog, private userService: UserService, private authenticationService: AuthenticationService) {
    this.activatedRoute.params.subscribe((params) => {
      this.username = params['username'];
      if (this.username) {
        this.fetchUserProfile(this.username);
      }
    });
    this.isLoggedin = !!this.authenticationService.user.value;

  }

  fetchUserProfile(username: string) {
    this.userService.getUserProfile(username).subscribe((response) => {
      if (response.status == 'success') {
        this.userInformation = response.data.user;
        this.recpipeInformation = response.data.recipes;
        this.likes = response.data.likes;
        this.followers = response.data.followers;
        this.following = response.data.following;
        this.isLoggedInUser = this.userInformation.id == this.authenticationService.user.value.id;
        this.totalRecipes = response.data.totalRecipes;
        this.isFollowing = this.authenticationService.userFollowings.findIndex((user) => user.following.id == this.userInformation.id) > -1;
      }
    });
  }

  navigateToRecipeDetailPage(recipe) {
    console.log(recipe);

    this.router.navigate(['/pages/recipe', recipe.recipe.id]);
  }

  follow() {
    this.userService.followUser(this.userInformation.id).subscribe((response) => {
      if (response.status == 'success') {
        this.isFollowing = true;
      }
    });
  }

  unfollow() {
    this.userService.unfollowUser(this.userInformation.id).subscribe((response) => {
      if (response.status == 'success') {
        this.isFollowing = false;
      }
    });
  }
  editProfile() {
    console.log("hello");
    
    // this.router.navigateByUrl();
    this.router.navigate(["/pages/user-profile/settings"]);

  }

  listFollower() {
    this.dialog.open(ListUsersComponent, {
      height: '80%',
      panelClass: 'mat-dialog-container',
      width: '470px',
      data: {
        title: 'Followers',
        users: this.followers.map((user) => user.follower)
      }
    });
  }

  listFollowing() {
    this.dialog.open(ListUsersComponent, {
      height: '80%',
      panelClass: 'mat-dialog-container',
      width: '470px',
      data: {
        title: 'Following',
        users: this.following.map((user) => user.following)
      }
    });
  }
}
