import { defineStore } from 'pinia';

export const useBookmarkStore = defineStore('bookmark', {
    state: () => ({
        bookmarkList: [], // 북마크 리스트를 저장
    }),
    actions: {
        setBookmarkList(bookmarks) {
            this.bookmarkList = bookmarks;
        },
    },
});
