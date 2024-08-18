<template>
    <section class="pt-4 custom-section">
      <div class="container px-lg-5">
          <div class="col-lg-6 mb-4" v-for="news in newsList">
            <router-link 
              :to="`/detail/${news.newsId}`" 
              class="news-item-link">
              <div class="card bg-light border-0 h-100 custom-card1">
                <div class="card-body text-center p-4 p-lg-5 pt-0 pt-lg-0">
                  <div class="card-news1">
                    <img :src="news.image" alt="Image 4" class="card-image">
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
  </template>

  <script>
    import NewsService from '@/services/NewsService';

    export default{
      data() {
        return {
          newsList : []
        }
      },
      methods: {
        getNewsList(){
          NewsService.getNewsList().then((response) => {
              this.newsList = response.data;
          })
        }
      },
      created () {
        this.getNewsList();
      },
    };

  </script>
  
  <style scoped>
  .custom-section {
    margin-left: 20px; 
    padding-right: 20px; 
  }
  
  .custom-card1, .custom-card2 {
    width: 100%;
    height: 300px;
    margin-bottom: 20px;
  }
  
  .card-news1 img, .card-news2 img {
    width: 100%;
    height: 250px;
    object-fit: cover;
  }
  
  .card1-text, .card2-text {
    padding: 20px;
    text-align: center;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
  }
  
  .card1-text h2, .card2-text h2 {
    margin-top: 0;
  }
  
  .card1-text p, .card2-text p {
    margin-bottom: 0;
  }
  
  .row {
    margin-left: -15px;
    margin-right: -15px;
  }
  
  .col-lg-6 {
    padding-left: 15px;
    padding-right: 15px;
  }
  </style>
  