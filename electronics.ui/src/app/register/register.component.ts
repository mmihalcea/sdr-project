import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {StoreUser} from "../core/model/store-user";
import {County} from "./county-response.model";
import {ActivatedRoute} from "@angular/router";
import {RegisterService} from "./register.service";
import {Feature, Point} from "geojson";
import {UserAddressResponse} from "../user-orders/user-address-response.model";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup = new FormGroup({});
  token = '';
  hidePassword = true;
  successMessage = '';
  errorMessage = '';
  showProgressBar = false;
  counties: County[] = [];
  streetResults: string[] = [];

  constructor(private fb: FormBuilder, private registerService: RegisterService, private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe((data) => {
      this.counties = data.counties;
    });
    this.initForm();
  }

  initForm() {
    this.registerForm = this.fb.group({
      name: ['', Validators.required],
      username: ['', Validators.pattern('[a-z]{8,}')],
      email: ['', Validators.email],
      password: ['', Validators.pattern('^(?=.{8,})(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$')],
      county: [''],
      city: [''],
      street: [''],
      number: [''],
      postalCode: [''],
      lat: [-1],
      lon: [-1],
    })
  }

  onSubmit() {
    if (this.registerForm.valid) {
      let registerRequest = new StoreUser(this.registerForm.get('name')?.value,
        this.registerForm.get('username')?.value, this.registerForm.get('email')?.value,
        this.registerForm.get('password')?.value);
      let userAddr = new UserAddressResponse();
      userAddr.city = this.registerForm.get('city')?.value;
      userAddr.county = {id: this.registerForm.get('county')?.value}
      userAddr.street = this.registerForm.get('street')?.value;
      userAddr.number = this.registerForm.get('number')?.value;
      userAddr.postCode = this.registerForm.get('postalCode')?.value;
      userAddr.lat = this.registerForm.get('lat')?.value;
      userAddr.lon = this.registerForm.get('lon')?.value;
      registerRequest.address = userAddr;
      this.errorMessage = '';
      this.successMessage = '';
      this.showProgressBar = true;
      this.registerService.register(registerRequest).subscribe(res => {
          this.successMessage = res.message
        },
        error => {
          this.errorMessage = error.error.message;
          this.showProgressBar = false
        }, () => this.showProgressBar = false)
    }
  }


  getFieldErrorMessage(field: string) {
    if (this.registerForm.get(field)?.hasError('required')) {
      return 'Acest camp este obligatoriu';
    }

    switch (field) {
      case 'email':
        return this.registerForm.get(field)?.hasError('email') ? 'E-mail invalid' : '';
      case 'username':
        return this.registerForm.get(field)?.hasError('pattern') ? 'Username invalid! Acesta trebuie sa contina minim 8 caratere, litere mici.' : '';
      case 'password':
        return this.registerForm.get(field)?.hasError('pattern') ? 'Parola invalida! Aceasta trebuie sa contina minim 8 caractere, o litera mare si un carater special' : '';
    }
    return '';
  }

  searchStreet($event: any) {
    //in a real application, make a request to a remote url with the query and return filtered results, for demo we filter at client side
    let filtered: any[] = [];
    let query = $event.query;

    this.registerService.searchStreet(query).subscribe(collection => {
      const countyName = this.counties.filter(c => c.id === this.registerForm.controls['county'].value)[0]?.name
      collection.features.forEach(f => {
        if (f.properties?.county === countyName) {
          filtered.push(f);
        }
      })
      this.streetResults = filtered;
      console.log(this.streetResults);
    })

  }

  onSelectAddress(feature: Feature) {
    if (feature.properties) {
      this.registerForm.controls['street'].setValue(feature.properties['name']);
      this.registerForm.controls['city'].setValue(feature.properties['city']);
      this.registerForm.controls['postalCode'].setValue(feature.properties['postcode']);

      let point = feature.geometry as Point;
      this.registerForm.controls['lat'].setValue(point.coordinates[1]);
      this.registerForm.controls['lon'].setValue(point.coordinates[0]);
    }


    console.log(feature);
  }
}
