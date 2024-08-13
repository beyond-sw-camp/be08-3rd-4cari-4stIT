<template>
  <header class="headerIT">
    <router-link to="/main" class="back-to-main">
      <img src="@/assets/logo.png" alt="logo" width="50px">
    </router-link>
    <div class="search-bar">
      <input type="text" placeholder="검색창">
      <button>검색</button>
    </div>
    <router-link to="/mypage" class="mypage">마이페이지</router-link>
  </header>
</template>

<script>
  export default {
    name: 'HeaderIT',
  };
</script>

<style scoped>
  .headerIT {
    position: fixed;
    z-index: 1000;
    top: 0;
    left: 0;
    width: 100%;
    height: 50px;
    background-color: #6096B4;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px;
  }
  .search-bar {
    display: flex;
    align-items: center;
  }

  .search-bar input {
    width: 800px;
    height: 25px;
    padding: 5px;
    border: 1px solid #BDCDD6;
    border-radius: 6px;
    font-size: 15px;
  }

  .search-bar button {
    height: 35px;
    width: 80px;
    font-size: 15px;
    margin-left: 5px;
    border: 1px solid #BDCDD6;
    border-radius: 6px;
    background-color: #fff;
    cursor: pointer;
  }

  .mypage {
    margin-right: 10px;
    color: #fff;
    font-size: 15px;
    margin-right: 50px;
  }

  .mypage:hover {
    text-decoration: underline;
  }
</style>