<template>
    <div class="my-page-container">
        <h2>마이페이지</h2>

        <!-- 개인 정보 수정 섹션 -->
        <section class="profile-info-section">
            <h3>개인 정보</h3>
            <div v-if="!editProfileMode">
                <p><strong>이름:</strong> {{ form.name }}</p>
                <p><strong>이메일:</strong> {{ form.email }}</p>
                <p><strong>전화번호:</strong> {{ form.phone }}</p>
                <p><strong>생년월일:</strong> {{ form.birth }}</p>
                <p><strong>성별:</strong> {{ form.gender === 'M' ? '남성' : '여성' }}</p>
                <button class="edit-button" @click="editProfileMode = true">수정</button>
            </div>

            <form v-else @submit.prevent="updateProfile">
                <div class="form-group">
                    <label for="name">이름</label>
                    <input type="text" v-model="form.name" id="name" required />
                </div>

                <div class="form-group">
                    <label for="email">이메일 주소</label>
                    <input type="email" v-model="form.email" id="email" required />
                </div>

                <div class="form-group">
                    <label for="phone">휴대전화번호</label>
                    <input type="tel" v-model="form.phone" id="phone" required />
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

                <button type="submit" class="save-button">정보 수정</button>
                <button type="button" class="cancel-button" @click="editProfileMode = false">취소</button>
            </form>
        </section>

        <!-- 비밀번호 수정 섹션 -->
        <section class="password-change-section">
            <h3>비밀번호 수정</h3>
            <form @submit.prevent="changePassword">
                <div class="form-group">
                    <label for="current-password">현재 비밀번호</label>
                    <input type="password" v-model="passwordForm.currentPassword" id="current-password" required />
                </div>

                <div class="form-group">
                    <label for="new-password">새 비밀번호</label>
                    <input type="password" v-model="passwordForm.newPassword" id="new-password" required />
                </div>

                <div class="form-group">
                    <label for="confirm-password">새 비밀번호 확인</label>
                    <input type="password" v-model="passwordForm.confirmPassword" id="confirm-password" required />
                </div>

                <button type="submit" class="save-button">비밀번호 변경</button>
            </form>
        </section>

        <!-- 관심사 수정 섹션 -->
        <section class="interest-edit-section">
            <h3>관심사 수정</h3>
            <div class="interest-buttons">
                <button 
                    v-for="category in categories" 
                    :key="category" 
                    @click="toggleInterest(category)" 
                    :class="{ 'selected': form.interest && form.interest.includes(category) }">
                    {{ category }}
                </button>
            </div>
        </section>
    </div>
</template>

<script>
import axios from 'axios';
import router from '@/router';

export default {
    name: 'MyPage',
    data() {
        return {
            editProfileMode: false,
            form: {
                id: '',
                name: '',
                email: '',
                phone: '',
                birth: '',
                gender: '',
                interest: [],
            },
            passwordForm: {
                currentPassword: '',
                newPassword: '',
                confirmPassword: '',
            },
            categories: ['Cybersecurity', 'Gadgets', 'IT News', 'Software Development', 'Technology'],
        };
    },
    methods: {
        async updateProfile() {
            try {
                const userId = this.form.id;
                const response = await axios.put(`http://localhost:8080/api/myinfo/update/${userId}`, this.form);
                localStorage.setItem('user', JSON.stringify(response.data));
                alert('개인 정보가 성공적으로 수정되었습니다.');
                this.editProfileMode = false;
            } catch (error) {
                console.error('개인 정보 수정 중 오류가 발생했습니다:', error);
                alert('정보 수정에 실패했습니다. 다시 시도해 주세요.');
            }
        },
        async updateInterests() {
            try {
                const userId = this.form.id;
                const response = await axios.put(`http://localhost:8080/api/myinfo/update/${userId}/interests`, {
                    interests: this.form.interest,
                });
                localStorage.setItem('user', JSON.stringify(response.data));
                alert('관심사가 성공적으로 수정되었습니다.');
            } catch (error) {
                console.error('관심사 수정 중 오류가 발생했습니다:', error);
                alert('관심사 수정에 실패했습니다. 다시 시도해 주세요.');
            }
        },
        async changePassword() {
            if (this.passwordForm.newPassword !== this.passwordForm.confirmPassword) {
                alert('새 비밀번호와 비밀번호 확인이 일치하지 않습니다.');
                return;
            }

            try {
                const userId = this.form.id;
                await axios.put(`http://localhost:8080/api/myinfo/update/${userId}/password`, {
                    currentPassword: this.passwordForm.currentPassword,
                    newPassword: this.passwordForm.newPassword,
                });
                alert('비밀번호가 성공적으로 변경되었습니다.');
            } catch (error) {
                console.error('비밀번호 변경 중 오류가 발생했습니다:', error);
                alert('비밀번호 변경에 실패했습니다. 다시 시도해 주세요.');
            }
        },
        toggleInterest(category) {
            if (!this.form.interest) {
                this.form.interest = [];
            }
            const index = this.form.interest.indexOf(category);
            if (index === -1) {
                this.form.interest.push(category);
            } else {
                this.form.interest.splice(index, 1);
            }
            this.updateInterests();
        },
    },
    created() {
        const storedUser = localStorage.getItem('user');
        if (storedUser) {
            try {
                this.form = JSON.parse(storedUser);
            } catch (error) {
                console.error('사용자 정보 파싱 중 오류가 발생했습니다:', error);
                alert('사용자 정보를 로드하는 데 실패했습니다. 다시 로그인해 주세요.');
                router.push('/login');
            }
        } else {
            console.error('사용자 정보가 로드되지 않았습니다.');
            alert('로그인이 필요합니다.');
            router.push('/login');
        }
    },
};
</script>

<style scoped>
.my-page-container {
    max-width: 800px;
    margin: 100px auto;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 10px;
}

h2,
h3 {
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
input[type='email'],
input[type='tel'],
input[type='date'],
input[type='password'],
select {
    width: 100%;
    padding: 8px;
    box-sizing: border-box;
}

button {
    width: 100%;
    padding: 10px;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    margin-top: 10px;
}

.save-button {
    background-color: #93bfcf;
}

.cancel-button {
    background-color: #f08080;
}

.edit-button {
    background-color: #93bfcf;
}

.profile-info-section,
.password-change-section,
.interest-edit-section {
    margin-top: 30px;
}

ul {
    list-style-type: none;
    padding-left: 0;
}

li {
    margin-bottom: 10px;
}

.interest-buttons {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
}

.interest-buttons button {
    flex: 1 1 30%;
    margin: 5px;
    background-color: #93bfcf;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    padding: 10px;
}

.interest-buttons button.selected {
    background-color: #6096B4;
}
</style>
