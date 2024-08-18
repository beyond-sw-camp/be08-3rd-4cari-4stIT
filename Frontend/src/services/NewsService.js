import axios from "axios";

const NEWS_API_BASE_URL = 'http://localhost:8080/api/main'

class NewsService{
    getNewsList(){
        return axios.get(NEWS_API_BASE_URL);
    }
    getNewsById(id) {
        return axios.get(`${NEWS_API_BASE_URL}/${id}`);
    }
}

export default new NewsService()