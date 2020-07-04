import axios from "axios";
import Vue from "vue";

class UsersService {
  constructor() {
    this.apiClient = axios.create({
      baseURL: "http://localhost:8080",
    });
  }

  async login(userInfo) {
    const headers = this.setHeaders();
    const success = await this.apiClient
      .post("/users/login", userInfo, {
        headers,
      })
      .then((response) => {
        if (response.status == 200) {
          this.loginUser(response.data.JWT);
          Vue.prototype.$userType = response.data.UserType;
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
    const headers = this.setHeaders();
    const success = await this.apiClient
      .post("/register", userInfo, {
        headers,
      })
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

  async getMyInfo() {
    const headers = this.setHeaders();
    const myData = await this.apiClient
      .get("/users/myinfo", {
        headers,
      })
      .then((response) => {
        return response.data;
      })
      .catch((err) => {
        console.log(err);
        return {};
      });
    return myData;
  }

  async updateUserData(data) {
    const headers = this.setHeaders();
    const success = await this.apiClient
      .put("/users", data, {
        headers,
      })
      .then((response) => {
        if (response.status == 200) {
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

  setHeaders() {
    const jwt = this.getToken();
    var headers = {
      Accept: "application/json",
      Authorization: "Bearer " + jwt,
    };
    return headers;
  }

  getToken() {
    return localStorage.getItem("jwt");
  }

  loginUser(token) {
    window.localStorage.setItem("jwt", token);
  }

  logoutUser() {
    window.localStorage.removeItem("jwt");
    Vue.prototype.$userType = "";
  }
}

const usersService = new UsersService();

export default usersService;
