import axios from "axios";
import usersService from "./UserService";

class ApartmentsService {
  constructor() {
    this.apiClient = axios.create({
      baseURL: "http://localhost:8080",
    });
  }

  async searchApartments(params) {
    const headers = usersService.setHeaders();
    var apartments = await this.apiClient
      .get("/apartments", {
        params,
        headers,
      })
      .then((response) => {
        return response.data;
      })
      .catch((err) => {
        console.log(err);
        return [];
      });
    return apartments;
  }

  async getApartmentById(id) {
    const headers = usersService.setHeaders();
    var apartment = await this.apiClient
      .get(`/apartments/${id}`, {
        headers,
      })
      .then((response) => {
        return response.data;
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
