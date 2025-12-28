import {Component, OnInit} from '@angular/core';
import {TokenStorageService} from "./core/auth/token-storage.service";
import {Router} from "@angular/router";
import {LoadingService} from "./core/service/loading.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'electronics';
  username: string | null = null;
  blockedPanel = false;


  constructor(private tokenStorage: TokenStorageService, private router: Router, private loadingService: LoadingService) {
    this.tokenStorage.username$.subscribe(username => this.username = username);
  }

  ngOnInit() {
    this.username = this.tokenStorage.getUsername();
    this.loadingService.showLoading$.subscribe(res => this.blockedPanel = res);
  }


}
