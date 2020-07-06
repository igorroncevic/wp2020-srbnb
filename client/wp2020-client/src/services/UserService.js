import axios from "axios";

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
        this.loginUser(response.data);
        return true;
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
      .post("/users", userInfo, {
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

  async searchUsers(data) {
    const headers = this.setHeaders();
    console.log(data);
    const users = await this.apiClient
      .get("/users", {
        params: data,
        headers,
      })
      .then((response) => {
        return response.data;
      })
      .catch((err) => {
        console.log(err);
        return false;
      });
    return users;
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

  getUserType() {
    return localStorage.getItem("userType");
  }

  loginUser(data) {
    window.localStorage.setItem("jwt", data.JWT);
    window.localStorage.setItem("userType", data.UserType);
  }

  logoutUser() {
    window.localStorage.removeItem("jwt");
    window.localStorage.removeItem("userType");
  }
}

const usersService = new UsersService();

export default usersService;
