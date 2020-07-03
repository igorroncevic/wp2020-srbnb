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
    var apartments = await this.apiClient
      .post("/search/apartments", searchParams)
      .then((response) => {
        if (response.status == 200) {
          return response.data;
        }
        return [];
      })
      .catch((err) => {
        console.log(err);
        return [];
      });
    return apartments;
  }

  async getApartmentById(id) {
    var apartment = await this.apiClient
      .get(`/apartments/${id}`)
      .then((response) => {
        if (response.status == 200) {
          return response.data;
        }
        return null;
      })
      .catch((err) => {
        console.log(err);
        return null;
      });
    return apartment;
  }
}

const apartmentsService = new ApartmentsService();

export default apartmentsService;
