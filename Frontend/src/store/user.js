import { defineStore } from "pinia";

export const useUserStore = defineStore("user", {
    state: () => ({
        user: JSON.parse(localStorage.getItem('user')) || null,
    }),
    getters: {
        isLogIn: (state) => !!state.user,
    },
    actions: {
        setUser(user) {
        this.user = user;
        localStorage.setItem('user', JSON.stringify(user)); // 로컬 스토리지에 사용자 정보 저장
        localStorage.setItem('isLoggedIn', true); // 로그인 상태 저장
        },
        logout() {
        this.user = null;
        localStorage.removeItem('user');
        localStorage.removeItem('isLoggedIn');
        },
        loadUser() {
        const storedUser = localStorage.getItem('user');
        if (storedUser) {
            this.user = JSON.parse(storedUser);
        }
        },
    },
});
