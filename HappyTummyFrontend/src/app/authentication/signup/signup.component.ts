import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {
  signupForm:FormGroup;

  constructor(private authenticationService: AuthenticationService,private router:Router,private toasterService: ToastrService){}
  
  ngOnInit(): void {
    this.initForm();
  }

  initForm() {
    this.signupForm = new FormGroup({
      name : new FormControl(),
      email: new FormControl(),
      password: new FormControl(),
    });
  }


  submitForm(){
    this.authenticationService.signup(this.signupForm.value).subscribe(res => {
      this.toasterService.success("signup Successful");
      localStorage.setItem('user',JSON.stringify(this.signupForm.value));
      this.router.navigateByUrl('/pages/dashboard');
    },error => {
      this.toasterService.error("User Already Exits");
    });
  }

}
