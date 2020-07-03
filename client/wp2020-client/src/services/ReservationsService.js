import axios from "axios";
import usersService from "./UserService";

class ReservationsService {
  constructor() {
    this.apiClient = axios.create({
      baseURL: "http://localhost:8080",
      headers: {
        Accept: "application/json",
        Authorization: "Bearer " + usersService.getToken(),
      },
    });
  }

  async placeReservation(data) {
    var success = this.apiClient
      .post("/reservations", data)
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
}

const reservationsService = new ReservationsService();

export default reservationsService;
