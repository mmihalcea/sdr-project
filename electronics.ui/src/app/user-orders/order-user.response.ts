import {StoreUser} from "../core/model/store-user";
import {UserAddressResponse} from "./user-address-response.model";

export class OrderUserResponse extends StoreUser {
  name = '';
  address = new UserAddressResponse();
}
