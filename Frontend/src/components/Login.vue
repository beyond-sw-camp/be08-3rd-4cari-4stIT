<template>
  <div class="login-wrapper">
    <h2>Login</h2>
    <form @submit.prevent="handleSubmit" id="login-form">
      <input type="text" v-model="email" placeholder="Email" required />
      <input type="password" v-model="password" placeholder="Password" required />
      <input type="submit" value="Login" />
    </form>
  </div>
</template>

<script setup>
import axios from 'axios'; // axios import 추가
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useUserStore } from '@/store/user';

// Router
const router = useRouter();

// User Store
const userStore = useUserStore();

// Form fields
const email = ref('');
const password = ref('');

// Load user from storage on component mount
onMounted(() => {
  userStore.loadUserFromStorage();
});

const handleSubmit = async () => {
  try {
    const response = await axios.post('http://localhost:8080/api/login', {
      id: email.value,
      pwd: password.value,
    });

    const userData = response.data;
    userStore.login(userData);
    // userNo를 로컬 스토리지에 저장
    localStorage.setItem('userNo', userData.userNo);

    // 메인 페이지로 이동
    router.push('/');
  } catch (error) {
    console.error('로그인 실패:', error);
    alert('로그인에 실패했습니다. 아이디와 비밀번호를 확인해주세요.');
  }
};

// export default {
//   setup() {
//     const router = useRouter();
//     const userStore = useUserStore();

//     const email = ref('');
//     const password = ref('');
//     const rememberMe = ref(false);

//     const handleSubmit = async () => {
//       try {
//         const response = await axios.post('http://localhost:8080/api/login', {
//           id: email.value,
//           pwd: password.value,
//         });

//         if (response.status === 200) {
//           alert('로그인 성공');
//           userStore.setUser(response.data);
//           localStorage.setItem('isLoggedIn', true);
//           localStorage.setItem('user', JSON.stringify(response.data));
//           router.push('/'); // 로그인 후 메인 페이지로 리디렉션
//         } else {
//           alert('로그인에 실패했습니다. 아이디와 비밀번호를 확인하세요.');
//         }
//       } catch (error) {
//         console.error('로그인 중 오류 발생:', error);
//         alert('로그인 중 오류가 발생했습니다. 나중에 다시 시도해주세요.');
//       }
//     };

//     return {
//       email,
//       password,
//       rememberMe,
//       handleSubmit,
//     };
//   },
// };
</script>

<style scoped>
.login-wrapper {
  width: 400px;
  margin: 130px auto 80px;
  padding: 40px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 5px 4px 20px rgba(0, 0, 0, 0.3);
}

.login-wrapper > h2 {
  font-size: 24px;
  color: #6096b4;
  margin-bottom: 50px;
  text-align: center;
}

#login-form > input {
  width: 100%;
  height: 48px;
  padding: 0 10px;
  margin-bottom: 16px;
  border-radius: 6px;
  background-color: #f8f8f8;
  border: 1px solid #ddd;
  font-size: 14px;
}

#login-form > input::placeholder {
  color: #d2d2d2;
}

#login-form > input[type='submit'] {
  color: #fff;
  font-size: 16px;
  background-color: #6096b4;
  border: none;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

#login-form > input[type='submit']:hover {
  background-color: #5076a5;
}

#login-form > input[type='checkbox'] {
  display: none;
}

#login-form > label {
  color: #999999;
  display: flex;
  align-items: center;
  cursor: pointer;
}

#login-form input[type='checkbox'] + label {
  padding-left: 26px;
  background-image: url('checkbox.png');
  background-repeat: no-repeat;
  background-size: contain;
}

#login-form input[type='checkbox']:checked + label {
  background-image: url('checkbox-active.png');
}
</style>
