import {Injectable} from '@angular/core';
import {BehaviorSubject} from "rxjs";

const TOKEN_KEY = 'AuthToken';
const USERNAME_KEY = 'AuthUsername';
const AUTHORITIES_KEY = 'AuthAuthorities';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {
  private roles: Array<string> = [];
  private _usernameSource = new BehaviorSubject<string>('');
  username$ = this._usernameSource.asObservable();

  constructor() {
  }

  logout() {
    window.sessionStorage.clear();
    this._usernameSource.next('');
  }

  public saveToken(token: string) {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string | null {
    return sessionStorage.getItem(TOKEN_KEY);
  }

  public saveUsername(username: string) {
    window.sessionStorage.removeItem(USERNAME_KEY);
    window.sessionStorage.setItem(USERNAME_KEY, username);
    this._usernameSource.next(username);
  }

  public getUsername(): string | null {
    return sessionStorage.getItem(USERNAME_KEY);
  }

  public saveAuthorities(authorities: string[]) {
    window.sessionStorage.removeItem(AUTHORITIES_KEY);
    window.sessionStorage.setItem(AUTHORITIES_KEY, JSON.stringify(authorities));
  }

  public getAuthorities(): string[] {
    this.roles = [];

    if (sessionStorage.getItem(TOKEN_KEY)) {
      const authorities = sessionStorage.getItem(AUTHORITIES_KEY);
      if (authorities !== null) {
        JSON.parse(authorities).forEach((authority: { authority: string; }) => {
          this.roles.push(authority.authority);
        });
      }

    }

    return this.roles;
  }
}
