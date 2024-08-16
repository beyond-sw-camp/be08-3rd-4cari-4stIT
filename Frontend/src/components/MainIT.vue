<template>
    <div class="container px-lg-5" style="margin-top: 80px;">
        <div class="p-4 p-lg-5 bg-light rounded-3 text-center">
            <swiper :autoplay="{
                delay: 3000,
                disableOnInteraction: false,
            }" :navigation="true" :loop="true" :pagination="{
        clickable: true,
    }" :modules="modules" class="mySwiper">
                <swiper-slide>
                    <div class="slide-content">
                        <img src="../image/image1.jpg" alt="Image 1" class="slide-image">
                        <div class="slide-text">
                            <h2>Title 1</h2>
                            <p id="swp">This is the description for the first slide. It can be as long or short as you need.</p>
                        </div>
                    </div>
                </swiper-slide>
                <swiper-slide>
                    <div class="slide-content">
                        <img src="../image/image2.jpg" alt="Image 2" class="slide-image">
                        <div class="slide-text">
                            <h2>Title 1</h2>
                            <p>This is the description for the first slide. It can be as long or short as you need.</p>
                        </div>
                    </div>
                </swiper-slide>
                <swiper-slide>
                    <div class="slide-content">
                        <img src="../image/image3.jpg" alt="Image 3" class="slide-image">
                        <div class="slide-text">
                            <h2>Title 1</h2>
                            <p>This is the description for the first slide. It can be as long or short as you need.</p>
                        </div>
                    </div>
                </swiper-slide>
            </swiper>
        </div>
    </div>
    <CardIT/>
</template>

<script>
import { Swiper, SwiperSlide } from 'swiper/vue';
import CardIT from './CardIT.vue';

import 'swiper/css';
import 'swiper/css/navigation';
import 'swiper/css/pagination';

import { Autoplay, Navigation, Pagination } from 'swiper/modules';

export default {
    components: {
        Swiper,
        SwiperSlide,
        CardIT
    },
    setup() {
        return {
            modules: [Navigation, Pagination, Autoplay],
        };
    },
};

</script>

<style scoped>
.swiper {
    width: 100%;
    height: 100%;
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
}

.swiper-slide img {
    display: block;
    width: 1050px;
    height: 500px;
    object-fit: cover;
}

</style>
