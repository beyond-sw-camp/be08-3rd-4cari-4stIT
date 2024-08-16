<template>
    <div>
        <div class="container">
            <h3>북마크한 뉴스</h3>
            <div v-for="(item, index) in bookmarkList" :key="index" class="news-item">
            <a :href="item.link">
                <img :src="item.newsImage" :alt="'뉴스 이미지 ' + (index + 1)" />
            </a>
            <div class="news-item-content">
                <h4>
                <a :href="item.link">{{ item.newsTitle }}</a>
                </h4>
                <p>{{ item.newsSummary }}</p>
            </div>
            </div>
        </div>
    </div>
</template>

<script>
import FooterIT from "./common/FooterIT.vue";
import HeaderIT from "./common/HeaderIT.vue";
import axios from "axios";

export default {
    name: "Bookmark",
    components: {
    HeaderIT,
    FooterIT,
    },
    data() {
        return {
            bookmarkList: [],
            searchQuery: "",
        };
    },
    methods: {
    handleSearch() {
        console.log(this.searchQuery);
    },
    async fetchBookmarkList() {
        try{
            const userNo = 1; // 예를 들어, 현재 로그인한 사용자 ID를 1로 가정
            const response = await axios.get(`http://localhost:8080/api/bookmark/${userNo}`);
            this.bookmarkList = response.data;
        } catch (error) {
            console.error('북마크를 가져오는 데 실패했습니다:', error);
        }
    }
    },
    created() {
        this.fetchBookmarkList();
    }
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
    height: 850px;
    overflow-y: auto;
    margin: 0 auto; /* 좌우 중앙 정렬 */
    margin-bottom: 20px;
    margin-top: 80px;
}

h3 {
    color: #333333;
}

.news-item {
    background-color: #fff;
    border: 1px solid #bdc3c7;
    border-radius: 5px;
    padding: 10px;
    margin-bottom: 10px;
    display: flex;
    flex-wrap: nowrap;
    width: 100%;
    box-sizing: border-box;
}

.news-item img {
    max-width: 150px;
    height: auto;
    margin-right: 10px;
    border-radius: 5px;
}

.news-item-content {
    flex: 1;
}

.news-item h4 {
    margin-top: 0;
}

.news-item a {
    color: #6096b4;
    text-decoration: none;
}

.news-item a:hover {
    text-decoration: underline;
}

</style>
