<template>
    <div class="main-container">

      <div class="article-title-section">
        <h1>{{news.title}}</h1>
      </div>

      <div class="meta-container">
        <div class="left">
          <span class="date">작성 {{news.createdDate}}</span>
          <span class="author">{{news.author}} 기자</span>
        </div>
        <div class="right">
          <span class="views">조회수</span>
          <button class="bookmark">북마크에 추가</button>
        </div>
      </div>


      <div class="content-wrapper">
        <div class="image-section">
          <img :src="news.img" alt="기사 이미지" />
        </div>

        <div class="article-content-section">
          <p v-for="(sentence, index) in splitContent" :key="index">
            {{ sentence }}
          </p>
        </div>
      </div>
      <br>

    <div class="navigation-arrows">
        <div class="go-back">
          <router-link to="/main" class="go-back-link">
            <img src="@/assets/go_back.png" class="goback_img" alt="뒤로 가기" width="20px"/>
          </router-link>
        </div>
        <div class="go-forward">
          <router-link to="/main" class="go-forward-link">
            <img src="@/assets/go.png" class="go_img" alt="앞으로 가기" width="20px"/>
          </router-link>
        </div>
      </div>
      
      <div class="back-to-list">
        <router-link to="/main" class="back-button">목록으로 돌아가기</router-link>
      </div>

    </div>


</template>

<script>

import axios from 'axios';
import '@/components/newsdetails/Newsdetails.css'

export default {
  name: "DetailNews",
  data() {
    return {
      news: {}// api로부터 받은 데이터 저장
    };
  },
  created() {
    this.fetchNewsDetail();
  },
  methods: {
    async fetchNewsDetail() {
      try {
        const newsNo = this.$route.params.newsNo;
        const response = await axios.get(`http://localhost:8080/api/detail/${newsNo}`);
        this.news = response.data;
      } catch (error) {
        console.error("존재하지 않는 뉴스입니다. : ", error);
      }
    }
  },
  computed: {
    splitContent() {
      // 문장마다 나누기
      return this.news.content ? this.news.content.split(/(?<=[.])[ ]+/) : [];
      // 문단마다 나누기
      // return this.news.content ? this.news.content.split('\n\n') : [];
    },
  },
};
</script>