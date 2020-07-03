import axios from "axios";
import usersService from "./UserService";

class CommentsService {
  constructor() {
    this.apiClient = axios.create({
      baseURL: "http://localhost:8080",
      headers: {
        Accept: "application/json",
        Authorization: "Bearer " + usersService.getToken(),
      },
    });
  }

  async getCommentsForApartment(id) {
    var comments = await this.apiClient
      .get(`/apartments/${id}/comments`)
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
    return comments;
  }

  async postComment(comment) {
   var success = await this.apiClient
     .post(`/comments`, comment)
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

const commentsService = new CommentsService();

export default commentsService;
