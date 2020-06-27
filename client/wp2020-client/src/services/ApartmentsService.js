import axios from "axios";
import usersService from "./UserService";

class ApartmentsService {
  constructor() {
    this.apiClient = axios.create({
      baseURL: "http://localhost:8080",
      headers: {
        Accept: "application/json",
        Authorization: "Bearer " + usersService.getToken(),
      },
    });
  }

  async searchApartments(params) {
    var searchParams = JSON.stringify(params);
    //prebaciti u get
    await this.apiClient
      .post("/search/apartments", searchParams)
      .then((response) => {
        console.log(response);
        console.log(response.data);
        if (response.status == 200) {
          return response.data;
        } else {
          return null;
        }
      });
  }
}

const apartmentsService = new ApartmentsService();

export default apartmentsService;
