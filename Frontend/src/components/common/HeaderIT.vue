<template>
    <nav class="navbar navbar-expand-lg bg-navbar fixed-top" >
        <div class="container px-lg-5">
            <RouterLink class="navbar-brand" :to="{name: 'main'}" assets>4ST-IT</RouterLink>
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
                <a class="nav-link" aria-current="page" href="#!">회원가입</a>
                </li>
                <li class="nav-item">
                <a class="nav-link" href="#!">로그인</a>
                </li>
            </ul>
            </div>
        </div>
    </nav>
</template>

<script>

export default {
    name: 'HeaderIT',
    data() {
        return {
            searchQuery: ''
        };
    },
    methods: {
        onSearch() {
            console.log('Search query:', this.searchQuery);
        }
    }
}
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