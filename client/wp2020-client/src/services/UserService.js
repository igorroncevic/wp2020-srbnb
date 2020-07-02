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
    //var loginInfo = JSON.stringify(userInfo);
    const success = await this.apiClient
      .post("/users/login", userInfo)
      .then((response) => {
        if (response.status == 200) {
          this.loginUser(response.data);
          return true;
        }
        return false;
      })
      .catch((err) => {
        console.log(err);
        return false;
      });
      return success;
  }

  async signup(userInfo) {
    //var signupInfo = JSON.stringify(userInfo);
    const success = await this.apiClient
      .post("/register", userInfo)
      .then((response) => {
        if (response.status == 200) {
          return true;
        }
        return false;
      })
      .catch((err) => {
        console.log(JSON.stringify(err));
        return false;
      });
      return success;
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
