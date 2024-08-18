<template>
  <div class="signup-container">
    <h2>회원가입</h2>
    <form @submit.prevent="submitForm">
      <div class="form-group">
        <label for="id">아이디</label>
        <input type="text" v-model="form.id" id="id" required :disabled="usernameChecked" />
        <button id="btn-idChk" type="button" @click="checkUsername">중복 확인</button>
        <span v-if="usernameCheckMessage" :class="{ 'text-success': !isUsernameDuplicate, 'text-danger': isUsernameDuplicate }">
          {{ usernameCheckMessage }}
        </span>
      </div>

      <div class="form-group">
        <label for="pwd">비밀번호</label>
        <input type="password" v-model="form.pwd" id="pwd" required />
      </div>

      <div class="form-group">
        <label for="email">이메일 주소</label>
        <input type="email" v-model="form.email" id="email" required />
      </div>

      <div class="form-group">
        <label for="name">이름</label>
        <input type="text" v-model="form.name" id="name" required />
      </div>

      <div class="form-group">
        <label for="birth">생년월일</label>
        <input type="date" v-model="form.birth" id="birth" required />
      </div>

      <div class="form-group">
        <label>성별</label>
        <div>
          <label>
            <input type="radio" v-model="form.gender" value="M" required />
            남성
          </label>
          <label>
            <input type="radio" v-model="form.gender" value="F" required />
            여성
          </label>
        </div>
      </div>

      <div class="form-group">
        <label for="phone">휴대전화번호</label>
        <input type="tel" v-model="form.phone" id="phone" required />
      </div>

      <!-- <div class="form-group">
        <label for="interest">관심사</label>
        <select name="interest" v-model="form.interest" id="interest" multiple size="3">
          <option value="web">웹개발</option>
          <option value="game">게임개발</option>
          <option value="data">데이터사이언스</option>
          <option value="ai">인공지능</option>
          <option value="security">보안</option>
          <option value="network">네트워크</option>
        </select>
      </div> -->
      <div class="form-group">
        <label for="interest">관심사</label>
        <select name="interest" v-model="form.interest" id="interest" multiple size="5">
          <option v-for="category in categories" :key="category.categoryId" :value="category.categoryId">
            {{ category.name }}
          </option>
        </select>
      </div>

      <button type="submit" :disabled="!usernameChecked">회원가입</button>
    </form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      form: {
        id: '',
        pwd: '',
        email: '',
        name: '',
        birth: '',
        gender: '',
        phone: '',
        interest: [],
      },
      categories: [],
      isUsernameDuplicate: false, // 아이디 중복 여부
      usernameCheckMessage: '', // 중복 확인 메시지
      usernameChecked: false, // 중복 확인 완료 여부
    };
  },
  created() {
    this.fetchCategories();
  },
  methods: {
    async fetchCategories() {
      try {
        const response = await axios.get('http://localhost:8080/api/categories');
        this.categories = response.data;
      } catch (error) {
        console.error('카테고리 데이터를 불러오는 중 오류가 발생했습니다:', error);
      }
    },
    async checkUsername() {
      if (!this.form.id) {
        this.usernameCheckMessage = '아이디를 입력해주세요.';
        return;
      }
      try {
        const response = await axios.get('http://localhost:8080/api/user/check-userid', {
          params: { userId: this.form.id },
        });
        this.isUsernameDuplicate = response.data;

        if (this.isUsernameDuplicate) {
          this.usernameCheckMessage = '이미 사용 중인 아이디입니다.';
          this.usernameChecked = false;
        } else {
          this.usernameCheckMessage = '사용 가능한 아이디입니다.';
          this.usernameChecked = true;
        }
      } catch (error) {
        console.error('아이디 중복 확인 중 오류가 발생했습니다:', error);
        this.usernameCheckMessage = '오류가 발생했습니다. 다시 시도해 주세요.';
      }
      console.log(this.form.id + this.usernameCheckMessage + '작동' + this.usernameChecked);
    },
    async submitForm() {
      if (!this.usernameChecked) {
        alert('아이디 중복 확인을 해주세요.');
        return;
      }

      try {
        const response = await axios.post('http://localhost:8080/api/join', this.form);

        if (response.status === 201) {
          alert('회원가입이 완료되었습니다!');
          this.$router.push('/login');
        }
      } catch (error) {
        console.error('회원가입 중 오류가 발생했습니다:', error);
        alert('회원가입에 실패했습니다. 다시 시도해 주세요.');
      }
    },
  },
};
</script>

<style scoped>
.signup-container {
  max-width: 800px;
  min-width: 400px;
  margin: 100px auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 10px;
}

h2 {
  text-align: center;
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
}

input[type='text'],
input[type='password'],
input[type='email'],
input[type='date'],
input[type='tel'] {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
}

button[type='submit'] {
  width: 100%;
  padding: 10px;
  background-color: #93bfcf;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

#id {
  width: 80%;
  padding: 8px;
  box-sizing: border-box;
  margin-right: 10px;
}

#btn-idChk {
  width: 15%;
  height: 30px;
  background-color: #93bfcf;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button[type='submit']:hover,
button[type='button']:hover {
  background-color: #bdcdd6;
}

.text-success {
  color: green;
}

.text-danger {
  color: red;
}
</style>
