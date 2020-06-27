import axios from "axios";

class UsersService {
  constructor() {
    this.apiClient = axios.create({
      baseURL: "http://localhost:8080",
      headers: {
        Accept: "application/json",
        Authorization: "Bearer " + this.getToken(),
      },
    });
  }

  async login(userInfo) {
    var loginInfo = JSON.stringify(userInfo);
    await this.apiClient.post("/login", loginInfo).then((response) => {
      if (response.status == 200) {
        this.loginUser(response.data);
        return true;
      } else {
        return false;
      }
    });
  }

  async signup(userInfo) {
    var signupInfo = JSON.stringify(userInfo);
    await this.apiClient.post("/register", signupInfo).then((response) => {
      if (response.status == 200) {
        return true;
      } else {
        return false;
      }
    });
  }

  getToken() {
    return localStorage.getItem("jwt");
  }

  loginUser(token) {
    window.localStorage.setItem("jwt", token);
  }

  logoutUser() {
    window.localStorage.removeItem("jwt");
  }
}

const usersService = new UsersService();

export default usersService;
