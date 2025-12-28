import {StoreUser} from "./store-user";

export class ProfileUser extends StoreUser {
  profilePic: string | null;

  constructor(name: string, username: string, email: string, password: string, profilePic: string | null) {
    super(name, username, email, password);
    this.profilePic = profilePic;
  }
}
