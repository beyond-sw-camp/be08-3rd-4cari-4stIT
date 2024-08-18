<template>
    <nav class="navbar navbar-expand-lg bg-navbar fixed-top">
        <div class="container px-lg-5">
            <RouterLink class="navbar-brand" :to="{name: 'main'}" assets style="font-size: 20px;">4ST-IT</RouterLink>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <div class="d-flex justify-content-center flex-grow-1">
                    <form class="search-box" @submit.prevent="onSearch">
                        <input class="search-txt" v-model="searchQuery" type="text" placeholder="검색어를 입력하세요.">
                        <button class="search-button" type="submit">
                            🔍
                        </button>
                    </form>
                </div>
                <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <RouterLink class="nav-link" :to="{ name: 'JoinView' }" style="font-size: 15px;">회원가입</RouterLink>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#!" style="font-size: 15px;">로그인</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</template>

<script>
import { defineComponent, ref } from 'vue';
import { useRouter } from 'vue-router';
import NewsService from '@/services/NewsService';

export default defineComponent({
    name: 'HeaderIT',
    setup(props, { emit }) {
        const searchQuery = ref('');
        const router = useRouter();

        const onSearch = async () => {
            try {
                await router.push({ name: 'main', query: { search: searchQuery.value } });
            } catch (error) {
                console.error("Error during search redirection:", error);
            }
        };

        return {
            searchQuery,
            onSearch
        };
    }
});
</script>

<style scoped>
.navbar-brand {
    display: flex;
    align-items: center; 
    height: 100%; 
    margin-bottom: 0; 
    padding: 0; 
}
</style>
