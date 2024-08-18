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
          <span class="views">조회수 {{ news.views }}</span>
            <button @click="statusBookmark" class="bookmark">
              {{ isBookmarked ? '북마크 삭제' : '북마크에 추가' }}
          </button>
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

    <!-- <div class="navigation-arrows">
        <div v-if="goBack"class="go-back">
            <img src="@/assets/go_back.png" @click="backTopage" class="goback_img" alt="뒤로 가기" width="20px"/>
        </div>
        <div class="go-forward">
            <img src="@/assets/go.png" class="go_img" alt="앞으로 가기" width="20px"/>
        </div>
      </div> 
    -->
      
      <div class="back-to-list">
        <router-link :to="backToList" class="back-button">목록으로 돌아가기</router-link>
      </div>

    </div>

</template>

<script>

import axios from 'axios';
import '@/components/newsdetails/Newsdetails.css'
import { useRouter, useRoute } from 'vue-router';
import { computed, ref, onMounted } from 'vue';
import { useUserStore } from '@/store/user';
// import { useNavigatorStore } from '@/store/navigator';
import { useBookmarkStore } from '@/store/bookmark';

export default {
  name: "DetailNews",
  setup(){
    const userStore = useUserStore();
    // const naviStore = useNavigatorStore();
    const bookmarkStore = useBookmarkStore();

    const router = useRouter();
    const route = useRoute();

    // const newsIdx = ref(-1);
    const curNewsNo = Number(route.params.newsNo);

    const bookmarkList = computed(() => bookmarkStore.bookmarkList);
    const isBookmarked = computed(() => bookmarkList.value.some(news => news.newsId === curNewsNo));

    const fetchBookmarkList = async () => {
      if (userStore.isLogIn) {
        try {
          const response = await axios.get(`http://localhost:8080/api/bookmark/${userStore.user.id}`);
          bookmarkStore.setBookmarkList(response.data);
        } catch (error) {
          console.error("북마크 로드 실패:", error);
        }
      }
    };

    const statusBookmark = async () => {
      if (!userStore.isLogIn) {
        alert('로그인이 필요합니다.');
        router.push('/login');
        
        return;
      };
      // console.log("isBookmarked: ", isBookmarked.value);
      try {
        if(isBookmarked.value){
        
          await axios.delete('http://localhost:8080/api/deletebookmark', {
            params: {
              userId: userStore.user.id,
              newsId: curNewsNo
            }
        });
        alert("북마크 삭제 완료");
        bookmarkStore.deleteBookmark(curNewsNo);

        
        }else{

        await axios.post('http://localhost:8080/api/createbookmark', {
          userId: userStore.user.id,
          newsId: curNewsNo     
        });
        alert("북마크 추가 완료");
        bookmarkStore.addBookmark(curNewsNo);

        }
      } catch (error) {
        if (error.response && error.response.status === 409) {
        alert('이미 추가된 뉴스입니다.');
      } else {
        console.error('북마크 추가 실패:', error);
      }
      }
    };

    onMounted(() => {
      fetchBookmarkList();
    });

    const backToList = computed(() => {
      return userStore.isLogIn ? '/bookmark' : '/';
    });

    return {
      isBookmarked,
      statusBookmark,
      backToList,
      bookmarkList,
      // newsIdx,
    };
  },

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
        console.log('News data:', response.data);
        this.news = response.data;
      } catch (error) {
        console.error("존재하지 않는 뉴스입니다. : ", error);
      }
    },

    getNewsById(id) {
          NewsService.getNewsById(id).then(response => {
            this.newsList = [response.data];
          }).catch(error => {
            console.error("There was an error!", error);
          });
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