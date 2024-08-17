import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import axios from 'axios';
import store from './store/index';

import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';

import HeaderIT from './components/common/HeaderIT.vue';
import './assets/main.css';
import './assets/HFcss.css';

const app = createApp(App);

app.use(router);
app.use(store);
app.config.globalProperties.$axios = axios;
app.component('HeaderIT', HeaderIT);
app.mount('#app');

// store.commit('restoreUser');