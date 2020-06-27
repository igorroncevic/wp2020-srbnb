import axios from "axios";

class UsersService {
  constructor() {
    this.apiClient = axios.create({
      //baseURL: "http://localhost:8080/rest",
      baseURL: "https://jsonplaceholder.typicode.com",
      headers: { Accept: "aplication/json" },
    });
  }

  //async login(userInfo){
  /* await this.apiClient.post('/login', {
     {
      name: userInfo.name,
    }
   })
   .then(data => {
      ...
   })
   }
  */
}

const usersService = new UsersService();

export default usersService;
