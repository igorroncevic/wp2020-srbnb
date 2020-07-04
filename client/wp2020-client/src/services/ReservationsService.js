import axios from "axios";
import usersService from "./UserService";

class ReservationsService {
  constructor() {
    this.apiClient = axios.create({
      baseURL: "http://localhost:8080",
    });
  }

  async placeReservation(data) {
    const headers = usersService.setHeaders();
    console.log(data);
    var success = this.apiClient
      .post("/reservations", data, {
        headers,
      })
      .then((response) => {
        console.log(response);
        return true;
      })
      .catch((err) => {
        console.log(err);
        return false;
      });
    return success;
  }

  async getReservations(params) {
    const headers = usersService.setHeaders();
    if (params == null) params = {};
    var reservations = this.apiClient
      .get("/reservations", {
        params,
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
    return reservations;
  }

  async acceptReservation(reservation) {
    const headers = usersService.setHeaders();
    var success = this.apiClient
      .put(`/reservations/${reservation.id}/accept`, reservation, {
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

  async declineReservation(reservation) {
    const headers = usersService.setHeaders();
    var success = this.apiClient
      .put(`/reservations/${reservation.id}/decline`, reservation, {
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

  async completeReservation(reservation) {
    const headers = usersService.setHeaders();
    var success = this.apiClient
      .put(`/reservations/${reservation.id}/complete`, reservation, {
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

  async cancelReservation(reservation) {
   const headers = usersService.setHeaders();
   var success = this.apiClient
     .put(`/reservations/${reservation.id}/cancel`, reservation, {
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

const reservationsService = new ReservationsService();

export default reservationsService;
