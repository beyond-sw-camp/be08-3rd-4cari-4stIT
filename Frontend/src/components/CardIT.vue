<template>
  <div>
    <HeaderIT @search="handleSearchResults" />
    <section class="pt-4 custom-section">
      <div class="container px-lg-5">
        <div class="col-lg-6 mb-4" v-for="news in newsList" :key="news.newsId">
          <router-link :to="`/detail/${news.newsId}`" class="news-item-link">
            <div class="card bg-light border-0 h-100 custom-card1">
              <div class="card-body text-center p-4 p-lg-5 pt-0 pt-lg-0">
                <div class="card-news1">
                  <img :src="news.image" alt="Image 4" class="card-image" />
                  <div class="card1-text">
                    <h2>{{ news.title }}</h2>
                    <p>{{ news.content }}</p>
                  </div>
                </div>
              </div>
            </div>
          </router-link>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import { defineComponent, ref, onMounted, watch } from 'vue';
import NewsService from '@/services/NewsService';
import HeaderIT from './common/HeaderIT.vue';
import { useRoute } from 'vue-router';
import { useUserStore } from '@/store/user';

export default defineComponent({
  components: {
    HeaderIT,
  },
  setup() {
    const userStore = useUserStore();
    const newsList = ref([]);
    const route = useRoute();

    // Function to fetch news based on search query
    const searchNews = async (query) => {
      try {
        if (userStore.isLogIn) {
          const response = await NewsService.getNewsById(userStore.user.id);
          newsList.value = response.data;
        } else {
          if (query) {
            const response = await NewsService.searchNews(query);
            newsList.value = response.data;
          } else {
            const response = await NewsService.getNewsList();
            newsList.value = response.data;
          }
        }
      } catch (error) {
        console.error('Error fetching news:', error);
      }
    };

    // Fetch news based on the initial query or default to full list
    const searchQuery = ref(route.query.search || '');
    onMounted(() => {
      searchNews(searchQuery.value);
    });

    // Watch for changes in the search query to re-fetch search results
    watch(
      () => route.query.search,
      (newSearchQuery) => {
        searchQuery.value = newSearchQuery || '';
        searchNews(searchQuery.value);
      }
    );

    return {
      newsList,
    };
  },
});
</script>

<style scoped>
.custom-section {
  margin-left: 20px;
  padding-right: 20px;
}

.custom-card1,
.custom-card2 {
  width: 100%;
  height: 300px;
  margin-bottom: 20px;
}

.card-news1 img,
.card-news2 img {
  width: 100%;
  height: 250px;
  object-fit: cover;
}

.card1-text,
.card2-text {
  padding: 20px;
  text-align: center;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.card1-text h2,
.card2-text h2 {
  margin-top: 0;
}

.card1-text p,
.card2-text p {
  margin-bottom: 0;
}

.nav-tabs .nav-link {
  border: none;
}

.nav-tabs .nav-link.active {
  border-bottom: 2px solid #007bff;
}
</style>
