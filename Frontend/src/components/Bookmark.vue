<template>
    <div>
        <div class="container">
            <h3>북마크한 뉴스</h3>
            <div class="news-grid">
                <div v-for="(item, index) in bookmarkList" :key="index" class="news-item">
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
import { useUserStore } from '@/store/user';

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
                const userNo = userStore.user.id;
                const response = await axios.get(`http://localhost:8080/api/bookmark/${userNo}`);
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
