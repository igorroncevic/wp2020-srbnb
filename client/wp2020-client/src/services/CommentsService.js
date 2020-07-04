import axios from "axios";
import usersService from "./UserService";

class CommentsService {
  constructor() {
    this.apiClient = axios.create({
      baseURL: "http://localhost:8080",
    });
  }

  async getCommentsForApartment(id) {
    const headers = usersService.setHeaders();
    var comments = await this.apiClient
      .get(`/apartments/${id}/comments`, {
        headers,
      })
      .then((response) => {
        return response.data;
      })
      .catch((err) => {
        console.log(err);
        return [];
      });
    return comments;
  }

  async postComment(comment) {
    const headers = usersService.setHeaders();
    var success = await this.apiClient
      .post(`/comments`, comment, {
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
}

const commentsService = new CommentsService();

export default commentsService;
