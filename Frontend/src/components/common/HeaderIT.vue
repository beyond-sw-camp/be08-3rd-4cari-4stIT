<template>
    <nav class="navbar navbar-expand-lg bg-navbar fixed-top">
        <div class="container px-lg-5">
            <RouterLink class="navbar-brand" :to="{ name: 'main' }" style="font-size: 20px;">4ST-IT</RouterLink>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
                aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <div class="d-flex justify-content-center flex-grow-1">
                    <form class="search-box" @submit.prevent="onSearch">
                        <input class="search-txt" v-model="searchQuery" type="text" placeholder="검색어를 입력하세요." />
                        <button class="search-button" type="submit">🔍</button>
                    </form>
                </div>
                <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                    <li v-if="!isLoggedIn" class="nav-item">
                        <RouterLink class="nav-link" :to="{ name: 'JoinView' }" style="font-size: 15px;">회원가입</RouterLink>
                    </li>

                    <!--
                    li v-if="!isLoggedIn" class="nav-item">
                    -->

                    <!-- <li v-if="!isLoggedIn" class="nav-item">
                    <a class="nav-link" href="#!" style="font-size: 15px;" @click="showLoginModal">로그인</a>
                    </li>
                    <li v-if="isLoggedIn" class="nav-item">
                    <RouterLink class="nav-link" :to="{ name: 'MyPageView' }" style="font-size: 15px;">마이페이지</RouterLink>
                    </li>
                    <li v-if="isLoggedIn" class="nav-item">
                    <a class="nav-link" href="#!" style="font-size: 15px;" @click="logout">로그아웃</a> -->

                    <li class="nav-item">

                        <RouterLink class="nav-link" :to="{ name: 'login' }" style="font-size: 15px;">로그인</RouterLink>
                    </li>
                    <li v-if="isLoggedIn" class="nav-item">
                        <RouterLink class="nav-link" :to="{ name: 'MyPageView' }" style="font-size: 15px;">마이페이지</RouterLink>
                    </li>
                    <li v-if="isLoggedIn" class="nav-item">
                        <a class="nav-link" href="#!" style="font-size: 15px;" @click="logout">로그아웃</a>
                    </li>
                </ul>
            </div>
            <RouterLink class="nav-link" :to="{ name: 'bookmark' }" style="font-size: 15px;">북마크로 이동</RouterLink>
        </div>
    </nav>
</template>

<script>
import { defineComponent, ref, computed, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useUserStore } from "@/store/user";

export default defineComponent({
    name: "HeaderIT",
    setup() {
        const userStore = useUserStore();
        const router = useRouter();
        const searchQuery = ref("");

        // 로그인 상태를 Pinia의 상태로부터 계산
        const isLoggedIn = computed(() => userStore.isLogIn);

        const onSearch = async () => {
            try {
                await router.push({
                    name: "main",
                    query: { search: searchQuery.value },
                });
            } catch (error) {
                console.error("검색 중 오류가 발생했습니다:", error);
            }
        };

        const logout = () => {
            if (confirm("로그아웃 하시겠습니까?")) {
                userStore.logout(); // Pinia 스토어의 logout 메서드 호출
                router.push('/login'); // 로그아웃 후 로그인 페이지로 리디렉션
            }
        };

        return {
            searchQuery,
            isLoggedIn,
            onSearch,
            logout,
        };
    },
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

.search-box {
    display: flex;
    align-items: center;
}

.search-txt {
    padding: 5px;
    font-size: 14px;
}

.search-button {
    background: none;
    border: none;
    cursor: pointer;
}
</style>
