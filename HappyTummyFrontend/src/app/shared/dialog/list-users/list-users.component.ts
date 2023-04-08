import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';

@Component({
  selector: 'app-list-users',
  templateUrl: './list-users.component.html',
  styleUrls: ['./list-users.component.scss']
})
export class ListUsersComponent implements OnInit {

  users: any[];
  title;
  constructor(public dialogRef: MatDialogRef<ListUsersComponent>, @Inject(MAT_DIALOG_DATA) public data: any,private router: Router) {
    this.users = data.users;
    console.log(this.users);
    
    this.title = data.title;
  }

  ngOnInit(): void {
  }

  navigateTo(item){
    this.dialogRef.close();
    this.router.navigateByUrl(`/pages/user-profile/${item.name}`);
  }

}
