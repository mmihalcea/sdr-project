import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute} from "@angular/router";
import {UserProfileService} from "./user-profile.service";
import {ProfileUser} from "../core/model/profile-user";
import {TokenStorageService} from "../core/auth/token-storage.service";
import {Role} from '../utils/role';
import {Category} from "../product/category.model";
import {MessageService} from "primeng/api";
import {StoreUser} from "../core/model/store-user";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {
  userForm: FormGroup = new FormGroup({});
  readonly passwordPattern = Validators.pattern('^(?=.{8,})(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$');
  successMessage = '';
  errorMessage = '';
  profilePic: string | null = null;
  userDetails: StoreUser = new StoreUser();
  Role = Role;
  categories: Category[] = [];

  constructor(private fb: FormBuilder, private route: ActivatedRoute, private userProfileService: UserProfileService,
              private tokenStorage: TokenStorageService, private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.route.data.subscribe((data) => {
      this.userDetails = data.userDetails[0];
      this.initForm(this.userDetails);
      this.categories = data.userDetails[1];
    });
  }

  initForm(data: any) {
    const userDetails = data;
    this.userForm = this.fb.group({
      name: [userDetails.name, Validators.required],
      username: [{value: userDetails.username, disabled: true}, Validators.pattern('[a-z]{8,}')],
      email: [userDetails.email, Validators.email],
      imgSrc: ['data:image/jpg;base64,' + userDetails.profilePic],
      file: [],
      passwords: this.fb.group({
        password: ['', this.passwordPattern],
        passwordConfirm: ['', this.passwordPattern],
      }, {validators: [this.passwordConfirming]})

    })
  }

  passwordConfirming(c: AbstractControl): { invalid: boolean } {
    if (c.get('password')?.value !== c.get('passwordConfirm')?.value) {
      return {invalid: true};
    }
    return {invalid: false};
  }

  onSubmit() {
    console.log(this.userForm);
    let user = new ProfileUser(this.userForm.get('name')?.value, this.userForm.get('username')?.value,
      this.userForm.get('email')?.value, this.userForm.get('password')?.value,
      this.profilePic);
    this.userProfileService.updateUser(user)
      .subscribe(res => this.successMessage = res.message,
        error => this.errorMessage = error.error.message);
  }

  getFieldErrorMessage(field: string): string {
    if (this.userForm.get(field)?.hasError('required')) {
      return 'Acest camp este obligatoriu';
    }

    switch (field) {
      case 'email':
        return this.userForm.get(field)?.hasError('email') ? 'E-mail invalid' : '';
      case 'username':
        return this.userForm.get(field)?.hasError('pattern') ? 'Username invalid! Acesta trebuie sa contina minim 8 caratere, litere mici.' : '';
      case 'password':
        return this.userForm.get(field)?.hasError('pattern') ? 'Parola invalida! Aceasta trebuie sa contina minim 8 caractere, o litera mare si un carater special' : '';
      case 'confirmPassword':


      default:
        return '';
    }
  }

  userHasRole(role: string) {
    return this.tokenStorage.getAuthorities().includes(role);
  }

  updateCategories() {
    this.userProfileService.updateCategories(this.userDetails.categories).subscribe(res => {
      this.messageService.add({severity: 'success', summary: res.message})
    })
  }
}
