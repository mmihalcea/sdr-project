export class AddProductRequest {
  name = '';
  description = '';
  type = -1;
  price = -1;
  files: string[] = [];


  constructor(other:any) {
    this.name = other.name;
    this.description = other.description;
    this.price = other.price;
    this.type = other.type;
  }
}
