import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../core/auth/auth.service";
import {TokenStorageService} from "../core/auth/token-storage.service";
import {AuthLoginInfo} from "../core/model/login-info";
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup = new FormGroup({});
  isLoggedIn = false;
  errorMessage = '';
  forgotPasswordErrorMessage = '';
  forgotPasswordSuccessMessage = '';
  forgotPasswordEmail = '';
  forgotPassword = false;
  roles: string[] = [];

  constructor(private route: ActivatedRoute, private authService: AuthService, private tokenStorage: TokenStorageService,
              private router: Router, private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.loginForm = this.initForm();

    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.roles = this.tokenStorage.getAuthorities();
    }
  }

  initForm() {
    return new FormGroup({
      username: new FormControl(),
      password: new FormControl()
    });
  }

  onSubmit() {
    this.errorMessage = '';
    let loginInfo = new AuthLoginInfo(this.loginForm.get('username')?.value, this.loginForm.get('password')?.value);
    this.authService.login(loginInfo).subscribe(response => {
      this.tokenStorage.saveToken(response.accessToken);
      this.tokenStorage.saveAuthorities(response.authorities);
      this.tokenStorage.saveUsername(response.username);
      this.roles = this.tokenStorage.getAuthorities();
      this.router.navigate(['/home']);
    }, error => {
      console.error(error);
      this.messageService.add({severity: 'error', summary: 'Autentificare nereușită'});
    })
  }

  onForgotPasswordClick() {
    this.forgotPasswordErrorMessage = '';
    this.forgotPasswordSuccessMessage = '';
    this.authService.forgotPassword(this.forgotPasswordEmail).subscribe(res => {
      this.forgotPasswordSuccessMessage = res.message;
    }, error => this.forgotPasswordErrorMessage = error.error.message)
  }

  back() {
    this.forgotPassword = false;
    this.forgotPasswordEmail = '';
    this.forgotPasswordErrorMessage = '';
    this.forgotPasswordSuccessMessage = '';
  }

}
