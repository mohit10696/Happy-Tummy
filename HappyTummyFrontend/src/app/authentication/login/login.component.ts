import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})


export class LoginComponent implements OnInit{
  loginForm:FormGroup;
  data=[]

  constructor(private authenticationService: AuthenticationService,private router:Router,private toasterService: ToastrService){}
  ngOnInit(): void {
    this.initForm();
  }

  initForm() {
    this.loginForm = new FormGroup({
      email: new FormControl(),
      password: new FormControl(),
    });
  }

  submitForm(){
    this.authenticationService.login(this.loginForm.value).subscribe(res => {
      this.toasterService.success("Login Successful");
      this.router.navigateByUrl('/pages/dashboard');
      
    },error => {
      this.toasterService.error("Invalid login or password");
    })
  }



}
