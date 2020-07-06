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

  async deleteApartment(id) {
   const headers = usersService.setHeaders();
   var success = await this.apiClient
     .delete(`/apartments/${id}`, {
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

  async addApartment(data) {
    console.log(data);
    const headers = usersService.setHeaders();
    var success = await this.apiClient
      .post(`/apartments`, data, {
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

  async updateApartment(data) {
    const headers = usersService.setHeaders();
    var success = await this.apiClient
      .put(`/apartments/${data.id}`, data, {
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
}

const apartmentsService = new ApartmentsService();

export default apartmentsService;
