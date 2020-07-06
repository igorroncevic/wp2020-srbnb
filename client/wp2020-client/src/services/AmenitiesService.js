import axios from "axios";
import usersService from "./UserService";

class AmenitiesService {
  constructor() {
    this.apiClient = axios.create({
      baseURL: "http://localhost:8080",
    });
  }

  async addAmenity(amenity) {
    const headers = usersService.setHeaders();
    var success = await this.apiClient
      .post("/amenities", amenity, {
        headers,
      })
      .then(() => {
        return true;
      })
      .catch((err) => {
        console.log(err);
        return false;
      });
    return success;
  }

  async deleteAmenity(amenity) {
    const headers = usersService.setHeaders();
    var success = await this.apiClient
      .delete(`/amenities/${amenity.id}`, {
        headers,
      })
      .then(() => {
        return true;
      })
      .catch((err) => {
        console.log(err);
        return false;
      });
    return success;
  }

  async updateAmenity(amenity) {
    const headers = usersService.setHeaders();
    var success = await this.apiClient
      .put(`/amenities/${amenity.id}`, amenity, {
        headers,
      })
      .then(() => {
        return true;
      })
      .catch((err) => {
        console.log(err);
        return false;
      });
    return success;
  }

  async getAllAmenities() {
    const headers = usersService.setHeaders();
    var amenities = await this.apiClient
      .get("/amenities", {
        headers,
      })
      .then((response) => {
        return response.data;
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
