<template>
    <div class="login-wrapper">
      <h2>Login</h2>
      <form @submit.prevent="handleSubmit" id="login-form">
        <input
          type="text"
          v-model="email"
          placeholder="Email"
          required
        />
        <input
          type="password"
          v-model="password"
          placeholder="Password"
          required
        />
        <label for="remember-check">
          <input
            type="checkbox"
            id="remember-check"
            v-model="rememberMe"
          />
          아이디 저장하기
        </label>
        <input type="submit" value="Login" />
      </form>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        email: '',
        password: '',
        rememberMe: false,
      };
    },
    methods: {
      handleSubmit() {
        // 로그인 처리 로직
        console.log('Email:', this.email);
        console.log('Password:', this.password);
        console.log('Remember Me:', this.rememberMe);
      }
    }
  };
  </script>
  
  <style scoped>
    .login-wrapper {
        width: 400px;
        margin: 130px auto 80px; /* 헤더 높이만큼 마진을 주어 내용이 헤더에 가려지지 않도록 함 */
        padding: 40px;
        background-color: #fff;
        border-radius: 8px;
        box-shadow: 5px 4px 20px rgba(0, 0, 0, 0.3);
    }
    
    .login-wrapper > h2 {
        font-size: 24px;
        color: #6096B4;
        margin-bottom: 50px;
        text-align: center;
    }
    
    #login-form > input {
        width: 100%;
        height: 48px;
        padding: 0 10px;
        margin-bottom: 16px;
        border-radius: 6px;
        background-color: #F8F8F8;
        border: 1px solid #ddd;
        font-size: 14px;
    }
    
    #login-form > input::placeholder {
        color: #D2D2D2;
    }
    
    #login-form > input[type="submit"] {
        color: #fff;
        font-size: 16px;
        background-color: #6096B4;
        border: none;
        cursor: pointer;
        transition: background-color 0.3s ease;
    }
    
    #login-form > input[type="submit"]:hover {
        background-color: #5076a5;
    }
    
    #login-form > input[type="checkbox"] {
        display: none;
    }
    
    #login-form > label {
        color: #999999;
        display: flex;
        align-items: center;
        cursor: pointer;
    }
    
    #login-form input[type="checkbox"] + label {
        padding-left: 26px;
        background-image: url("checkbox.png");
        background-repeat: no-repeat;
        background-size: contain;
    }
    
    #login-form input[type="checkbox"]:checked + label {
        background-image: url("checkbox-active.png");
    }
  </style>
  