<template>
    <section class="main-section">
    <div class="container px-lg-5" style="margin-top: 80px;">
        <div class="p-4 p-lg-5 bg-light rounded-3 text-center">
            <swiper
                :autoplay="{
                    delay: 3000,
                    disableOnInteraction: false
                }"
                :navigation="true"
                :loop="true"
                :pagination="{
                    clickable: true
                }"
                :modules="modules"
                class="swiper"
            >
                <swiper-slide v-for="(news, index) in newsTop3" :key="index">
                    <router-link :to="`/detail/${news.newsId}`" class="news-item-link">
                    <div class="slide-content">
                        <img :src="news.image" class="main-news-image" />
                        <div class="slide-text">
                            <h2>{{ news.title }}</h2>
                            <p id="swp">{{ news.content }}</p>
                        </div>
                    </div>
                    </router-link>
                </swiper-slide>
            </swiper>
        </div>
    </div>
</section>
    <CardIT />
</template>

<script>
import { Swiper, SwiperSlide } from 'swiper/vue';
import { ref, onMounted } from 'vue';
import 'swiper/css';
import 'swiper/css/navigation';
import 'swiper/css/pagination';
import { Autoplay, Navigation, Pagination } from 'swiper/modules';
import NewsService from '@/services/NewsService';
import CardIT from './CardIT.vue';

export default {
    components: {
        Swiper,
        SwiperSlide,
        CardIT
    },
    setup() {
        const newsTop3 = ref([]);
        const autoplayOptions = {
            delay: 3000,
            disableOnInteraction: false
        };
        const modules = [Autoplay, Navigation, Pagination];

        const getTop3News = async () => {
            try {
                const response = await NewsService.getTop3News();
                newsTop3.value = response.data;
                console.log(newsTop3.value);
            } catch (error) {
                console.error('Error fetching news:', error);
            }
        };

        onMounted(() => {
            getTop3News();
        });

        return {
            modules,
            autoplayOptions,
            newsTop3
        };
    }
};
</script>

<style scoped>
.main-section {
    display: flex;
    justify-content: center;
    align-items: center;
}

.container {
    width: 1286px;
}

.swiper {
    width: 100%;
    height: auto;
}

/* . . . 아래로 띄움 */
#swp { 
    margin-bottom: 50px;
}

.swiper-slide {
    text-align: center;
    font-size: 18px;
    background: #fff;
    display: flex;
    justify-content: center;
    align-items: center;
    position: relative;
}

.slide-content {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100%;
    width: 100%;
    padding: 10px;
    box-sizing: border-box;
    margin-bottom: 50px;
}

.main-news-image {
    display: block;
    width: auto;
    width: 800px;
    height: 300px;
}

.slide-text {
    text-align: center;
    margin-top: 10px;
    width: 100%; 
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 3;
    overflow: hidden; 
    text-overflow: ellipsis;
}
</style>

