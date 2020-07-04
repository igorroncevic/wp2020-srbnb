import axios from "axios";
import usersService from "./UserService";

class AmenitiesService {
  constructor() {
    this.apiClient = axios.create({
      baseURL: "http://localhost:8080",
    });
  }

  async getAllAmenities() {
    const headers = usersService.setHeaders();
    var amenities = await this.apiClient
      .get("/amenities", {
        headers,
      })
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
    return amenities;
  }

  async getAmenitiesForApartment(id) {
    const headers = usersService.setHeaders();
    var amenities = await this.apiClient
      .get(`/apartments/${id}/amenities`, {
        headers,
      })
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
    return amenities;
  }
}

const amenitiesService = new AmenitiesService();

export default amenitiesService;
