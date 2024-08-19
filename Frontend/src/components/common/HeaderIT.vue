<template>
    <nav class="navbar navbar-expand-lg bg-navbar fixed-top">
        <div class="container px-lg-5">
            <RouterLink class="navbar-brand" :to="{ name: 'main' }" style="font-size: 20px;">4ST-IT</RouterLink>
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
                    <li v-if="!isLoggedIn" class="nav-item">
                        <RouterLink class="nav-link" :to="{ name: 'JoinView' }" style="font-size: 15px;">회원가입</RouterLink>
                    </li>

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
                </ul>
            </div>
        </div>

        <!-- 로그인 모달 -->
        <div v-if="showModal" class="modal">
            <div class="modal-content">
                <span class="close" @click="closeLoginModal">&times;</span>
                <h2>로그인</h2>
                <form @submit.prevent="login">
                    <div class="form-group">
                        <label for="userId">아이디</label>
                        <input type="text" v-model="loginForm.id" id="userId" required />
                    </div>
                    <div class="form-group">
                        <label for="password">비밀번호</label>
                        <input type="password" v-model="loginForm.pwd" id="password" required />
                    </div>
                    <button type="submit" class="login-button">로그인</button>
                </form>
            </div>
        </div>
    </nav>
</template>

<script>
import { defineComponent, ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

export default defineComponent({
    name: 'HeaderIT',
    setup() {
        const isLoggedIn = ref(false);
        const searchQuery = ref('');
        const router = useRouter();
        const showModal = ref(false);
        const loginForm = ref({
            id: '',  // 'userId'를 'id'로 변경
            pwd: ''  // 'password'를 'pwd'로 변경
        });

        const checkLoginStatus = () => {
            const storedLoginStatus = localStorage.getItem('isLoggedIn');
            isLoggedIn.value = storedLoginStatus === 'true';
        };

        onMounted(() => {
            checkLoginStatus();
        });

        const onSearch = async () => {
            try {
                await router.push({ name: 'main', query: { search: searchQuery.value } });
            } catch (error) {
                console.error('검색 중 오류가 발생했습니다:', error);
            }
        };

        const showLoginModal = () => {
            showModal.value = true;
        };

        const closeLoginModal = () => {
            showModal.value = false;
        };

        const login = async () => {
            try {
                const response = await axios.post('http://localhost:8080/api/login', {
                    id: loginForm.value.id,  // 'userId'를 'id'로 변경
                    pwd: loginForm.value.pwd // 'password'를 'pwd'로 변경
                });

                if (response.status === 200) {
                    isLoggedIn.value = true;
                    localStorage.setItem('isLoggedIn', 'true');
                    localStorage.setItem('user', JSON.stringify(response.data));
                    closeLoginModal();  
                } else {
                    alert('로그인에 실패했습니다. 아이디와 비밀번호를 확인하세요.');
                }
            } catch (error) {
                console.error('로그인 중 오류가 발생했습니다:', error);
                alert('로그인 중 오류가 발생했습니다. 다시 시도해 주세요.');
            }
        };

        const logout = () => {
            if (confirm('로그아웃 하시겠습니까?')) {
                isLoggedIn.value = false;
                localStorage.removeItem('isLoggedIn');
                localStorage.removeItem('user');
            }
        };

        return {
            searchQuery,
            isLoggedIn,
            onSearch,
            loginForm,
            showModal,
            showLoginModal,
            closeLoginModal,
            login,
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

.modal {
    display: block;
    position: fixed;
    z-index: 1;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    overflow: auto;
    background-color: rgb(0,0,0);
    background-color: rgba(0,0,0,0.4);
    padding-top: 60px;
}
.modal-content {
    background-color: #fefefe;
    margin: 5% auto;
    padding: 20px;
    border: 1px solid #888;
    width: 80%;
    max-width: 400px;
    border-radius: 10px;
}
.close {
    color: #aaa;
    float: right;
    font-size: 28px;
    font-weight: bold;
}
.close:hover,
.close:focus {
    color: black;
    text-decoration: none;
    cursor: pointer;
}
.form-group {
    margin-bottom: 15px;
}
label {
    display: block;
    margin-bottom: 5px;
}
input[type='text'],
input[type='password'] {
    width: 100%;
    padding: 8px;
    box-sizing: border-box;
}
button.login-button {
    background-color: #93bfcf;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    width: 100%;
    padding: 10px;
    margin-top: 15px;
}
</style>
