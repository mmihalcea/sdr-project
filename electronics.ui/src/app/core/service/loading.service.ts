import {Injectable} from '@angular/core';
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class LoadingService {

  private _showLoading = new BehaviorSubject<boolean>(false);
  showLoading$ = this._showLoading.asObservable();

  constructor() {
  }

  show() {
    this._showLoading.next(true);
  }

  hide() {
    this._showLoading.next(false);
  }

}
