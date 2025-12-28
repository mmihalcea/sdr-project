import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute} from "@angular/router";
import {Category} from "../category.model";
import {AddProductRequest} from "./add-product-request.model";
import {ProductService} from "../product.service";
import {LoadingService} from "../../core/service/loading.service";
import {MessageService} from "primeng/api";
import {FileUpload} from "primeng/fileupload";
import {toBase64} from "../../utils/functions";

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.scss']
})
export class AddProductComponent implements OnInit {
  addProductForm: FormGroup = new FormGroup({});
  categories: Category[] = [];
  files: [string, string][] = [];
  files2 = [];
  @ViewChild('fileUpload') fileUpload: FileUpload | null = null;

  constructor(private fb: FormBuilder, private activatedRoute: ActivatedRoute, private instrumentService: ProductService,
              private loadingService: LoadingService, private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe((data) => {
      this.categories = data.categories;
    });
    this.initForm();
  }

  initForm() {
    this.addProductForm = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      price: ['', Validators.required],
      type: ['', Validators.required],

    })
  }

  onSubmit() {
    if (this.addProductForm.valid) {
      const request = new AddProductRequest(this.addProductForm.value);
      request.files = this.files.map(value => value[1]);
      this.loadingService.show();
      this.instrumentService.addProduct(request).subscribe(res => {
        this.messageService.add({severity: 'success', summary: res.message});
        this.addProductForm.reset();
        this.files = [];
        this.fileUpload?.clear();
      }, error => {
        this.loadingService.hide();
        this.messageService.add({severity: 'error', summary: error});
      }, () => this.loadingService.hide())
    }

  }

  async addImages(event: any) {
    for (const file of event.files) {
      let duplicate = false;
      const result = await toBase64(file).catch(e => Error(e));
      if (result instanceof Error) {
        console.log('Error: ', result.message);
        return;
      }
      this.files.forEach(f => {
          if (f[0] === file.name) {
            console.log("matched" + file.name);
            duplicate = true;
            return
          }
        }
      );
      if (!duplicate) {
        this.files.push([file.name, result.substring(result.indexOf(',') +1)]);
      }

      console.log(this.files);
    }
  }

  removeImage(event: any) {
    this.files = this.files.filter(f => f[0] !== event.file.name);
  }


}
