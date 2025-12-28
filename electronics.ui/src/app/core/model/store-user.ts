import {UserAddressResponse} from "../../user-orders/user-address-response.model";

export class StoreUser {
  name = '';
  username = '';
  email = '';
  password = '';
  categories: Array<number> = [];
  address = new UserAddressResponse();


  constructor(name?: string, username?: string, email?: string, password?: string) {
    if (name !== undefined) {
      this.name = name;
    }
    if (username !== undefined) {
      this.username = username;
    }
    if (email !== undefined) {
      this.email = email;
    }
    if (password !== undefined) {
      this.password = password;
    }
  }
}
