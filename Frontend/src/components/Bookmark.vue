  <template>
    <div>
      <div class="container">
        <h3>북마크한 뉴스</h3>
        <div v-if="bookmarkList.length === 0" class="no-bookmark">
          <p class="message">아직 북마크한 뉴스가 없습니다.</p>
          <p class="suggestion">관심 있는 뉴스를 북마크해보세요!</p>
          <router-link to="/news" class="explore-news">뉴스 보러 가기</router-link>
        </div>
        <div v-else class="news-grid">
          <div
            v-for="(item, index) in bookmarkList"
            :key="index"
            class="news-item"
          >
            <router-link :to="`/detail/${item.newsId}`">
              <img :src="item.newsImage" :alt="'뉴스 이미지 ' + (index + 1)" />
            </router-link>
            <div class="news-item-content">
              <h4>
                <router-link :to="`/detail/${item.newsId}`">{{ item.newsTitle }}</router-link>
              </h4>
              <p>{{ item.newsSummary }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </template>

  <script>
  import axios from "axios";
  import { useUserStore } from "@/store/user";

  export default {
    name: "Bookmark",
    data() {
      return {
        bookmarkList: [],
      };
    },
    methods: {
      async fetchBookmarkList() {
        try {
          const userStore = useUserStore();
          const userNo = userStore.user.userNo;
          const response = await axios.get(
            `http://localhost:8080/api/bookmark/${userNo}`
          );
          this.bookmarkList = response.data;
        } catch (error) {
          console.error("북마크를 가져오는 데 실패했습니다:", error);
        }
      },
    },
    created() {
      this.fetchBookmarkList();
    },
  };
  </script>

  <style scoped>
  html,
  body {
    height: 100%;
    margin: 0;
    padding: 0;
    background-color: #eee9da;
  }

  body {
    display: flex;
    flex-direction: column;
    align-items: center;
    background-color: #eee9da;
  }

  #app {
    display: flex;
    flex-direction: column;
    width: 100%;
  }

  .container {
    background-color: #bdcdd6;
    padding: 20px;
    border-radius: 10px;
    width: 100%;
    max-width: 1050px;
    margin: 0 auto;
    margin-bottom: 20px;
    margin-top: 80px;
  }

  h3 {
    color: #333333;
  }

  .no-bookmark {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  color: #555;
  margin: 50px auto;
  padding: 40px;
  border-radius: 10px;
  max-width: 800px;
  height: 400px; /* 원하는 높이로 조정 */
  }

  .no-bookmark .message {
    font-size: 1.5em;
    font-weight: bold;
    margin-bottom: 10px;
  }

  .no-bookmark .suggestion {
    font-size: 1.2em;
    margin-bottom: 20px;
  }

  .explore-news {
    display: inline-block;
    padding: 10px 20px;
    background-color: #6096b4;
    color: #fff;
    border-radius: 5px;
    text-decoration: none;
  }

  .explore-news:hover {
    background-color: #507d9a;
  }

  .news-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
    gap: 15px;
  }

  .news-item {
    background-color: #fff;
    border: 1px solid #bdc3c7;
    border-radius: 10px;
    padding: 10px;
    display: flex;
    flex-direction: column;
    box-sizing: border-box;
    transition: box-shadow 0.3s;
  }

  .news-item:hover {
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  }

  .news-item img {
    width: 100%;
    height: auto;
    border-radius: 10px;
    margin-bottom: 10px;
  }

  .news-item-content {
    flex: 1;
    display: flex;
    flex-direction: column;
  }

  .news-item h4 {
    margin: 0;
    font-size: 1.2em;
  }

  .news-item p {
    flex: 1;
    margin: 10px 0 0 0;
    color: #555;
  }

  .news-item a {
    color: #6096b4;
    text-decoration: none;
  }

  .news-item a:hover {
    text-decoration: underline;
  }
  </style>
