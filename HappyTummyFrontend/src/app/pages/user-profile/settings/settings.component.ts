import { Component } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { UserService } from 'src/app/services/user.service';
import { FormControl, FormGroup } from '@angular/forms';
import { LoginComponent } from 'src/app/authentication/login/login.component';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss']
})
export class SettingsComponent {
  updateForm:FormGroup;
  isLoggedInUser = false;
  file : File;
  selectedImage: any;
  userInformation: any;
  isLoggedin = false;
  constructor(private userService: UserService, private authenticationService: AuthenticationService,private toasterService:ToastrService) {

  }

  ngOnInit(): void {
    this.isLoggedin = !!this.authenticationService.user.value;
    this.userInformation = this.authenticationService.user.value;    
    this.initForm();
    this.updateForm.patchValue({
      name : this.userInformation.name,
      email : this.userInformation.email,
      bio : this.userInformation.bio
    });
  }

  initForm() {
    this.updateForm = new FormGroup({
      email: new FormControl(),
      username: new FormControl(),
      bio:new FormControl(),
      name: new FormControl(),
      phone: new FormControl(),

    });
  }

  submitUpdateForm() {
    this.userService.updateUser(this.userInformation.id,this.updateForm.value).subscribe((response) => {
      if (response.status == 'success') {
        console.log(response)
        this.userInformation = {
          ...this.userInformation,
          ...response.data
        }
        this.authenticationService.user.next(this.userInformation);
        localStorage.setItem("user",JSON.stringify(this.userInformation));
      }
    });
  }

  onFileSelected(event) {
    const file = (event.target as HTMLInputElement).files?.[0];
    this.file = file;
    this.readImageFile(file);
    this.uploadImage();
  }

  uploadImage() {
    const formData = new FormData();
    formData.append('file', this.file);
    this.userService.updateUserProfile(this.userInformation.id, formData).subscribe((res:any) => {
      if (res.status == 'success') {
        this.toasterService.success("Profie updated successfully");
      }
    }
    );
  }

  readImageFile(file: File | null): void {
    if (!file) {
      return;
    }

    const reader = new FileReader();
    reader.onload = () => {
      const imageDataUrl = reader.result as string;
      this.selectedImage = imageDataUrl;
    };
    reader.readAsDataURL(file);
  }



}
