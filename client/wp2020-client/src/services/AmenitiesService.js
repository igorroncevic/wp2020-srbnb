import axios from "axios";
import usersService from "./UserService";

class AmenitiesService {
  constructor() {
    this.apiClient = axios.create({
      baseURL: "http://localhost:8080",
      headers: {
        Accept: "application/json",
        Authorization: "Bearer " + usersService.getToken(),
      },
    });
  }

  async getAllAmenities() {
    var amenities = await this.apiClient
      .get("/amenities")
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
   var amenities = await this.apiClient
     .get(`/apartment/${id}/amenities`)
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
