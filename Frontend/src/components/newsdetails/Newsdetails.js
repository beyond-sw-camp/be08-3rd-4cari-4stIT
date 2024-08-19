
import axios from 'axios';
import '@/components/newsdetails/Newsdetails.css'
import { useRouter, useRoute } from 'vue-router';
import { computed, onMounted } from 'vue';
import { useUserStore } from '@/store/user';
import { useBookmarkStore } from '@/store/bookmark';
import { useNavigatorStore } from '@/store/navigator';

export default function useNewsdetails() {
    const userStore = useUserStore();
    const bookmarkStore = useBookmarkStore();
    const naviStore = useNavigatorStore();

    const router = useRouter();
    const route = useRoute();

    const curNewsNo = Number(route.params.newsNo);

    const bookmarkList = computed(() => bookmarkStore.bookmarkList);
    const isBookmarked = computed(() => bookmarkList.value.some(news => news.newsId === curNewsNo));
    
    router.beforeEach((to, from) => {
        // console.log("to", to.name, "from, ", from.name);
            naviStore.setPrev(from.name);
    });

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
        // console.log("naviStore.prev, ", naviStore.prev);
        if(naviStore.prev === 'bookmark'){// 이전 페이지가 북마크면
            return '/bookmark';
        }
        else{
            return '/';
        }
    });

    return {
        isBookmarked,
        statusBookmark,
        backToList,
        bookmarkList,
    };
}