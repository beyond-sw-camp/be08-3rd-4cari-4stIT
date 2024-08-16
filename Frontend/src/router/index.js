import { createRouter, createWebHistory } from 'vue-router'
// import HomeView from '../views/HomeView.vue'
import Bookmark from '@/components/Bookmark.vue'
import MainIT from '@/components/MainIT.vue'
import Newsdetails from '@/components/newsdetails/Newsdetails.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'main',
      component: MainIT
    },
    {
      path: '/bookmark',
      name: 'bookmark',
      component: Bookmark
    },
    {
      path: '/news/:newsNo',
      name: 'Newsdetails',
      component: Newsdetails
    },
    {
      path: '/:catchAll(.*)', // 와일드카드 라우트
      redirect: '/' // 없는 페이지로 접근 시 '/' 경로로 리디렉션
    }
  ]
})

export default router
