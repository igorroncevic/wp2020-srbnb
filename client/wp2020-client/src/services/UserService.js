import axios from "axios";
import Vue from "vue";

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

  async getMyInfo() {
    const myData = await this.apiClient
      .get("/users/myinfo")
      .then((response) => {
        console.log(response);
        if (response.status == 200) {
          return response.data;
        }
        return null;
      })
      .catch((err) => {
        console.log(err);
        return null;
      });
    return myData;
  }

  async updateUserData(data) {
    const success = await this.apiClient
      .put("/users", data)
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
