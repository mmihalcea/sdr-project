import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HomeComponent} from './home/home.component';
import {ButtonModule} from "primeng/button";
import {HttpClientModule} from "@angular/common/http";
import {ToolbarModule} from "primeng/toolbar";
import {httpInterceptorProviders} from "./core/auth/auth-interceptor";
import {SharedModule} from "./shared/shared.module";
import {ToastModule} from "primeng/toast";
import {MessageService} from "primeng/api";
import {CoreModule} from "./core/core.module";
import {BadgeModule} from "primeng/badge";
import {FormsModule} from "@angular/forms";
import {DialogService} from 'primeng/dynamicdialog';

@NgModule({
  declarations: [AppComponent, HomeComponent],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    ButtonModule,
    SharedModule,
    FormsModule,
    HttpClientModule,
    ToolbarModule,
    ToastModule,
    CoreModule,
    BadgeModule
  ],
  providers: [httpInterceptorProviders, MessageService, DialogService],
  bootstrap: [AppComponent],
})
export class AppModule {
}
