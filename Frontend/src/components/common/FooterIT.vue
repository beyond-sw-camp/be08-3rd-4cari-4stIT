<template>
    <footer class="custom-footer">
        <div class="container">
            <div class="d-flex flex-column align-items-center">
            <p class="footer-text" style="font-size: 15px">© {{ currentYear }} 4ST-IT All rights reserved.</p>
            <p class="footer-date-time" style="font-size: 15px">{{ currentDate }} || {{ currentTime }}</p>
            </div>
        </div>
    </footer>
</template>

<script>

export default {
    name: 'FooterIT',
    data() {
        return {
            currentTime: '',
            currentDate: '',
            currentYear: new Date().getFullYear()
        };
    },
    methods: {
        updateDateTime() {
            const now = new Date();
        
            // Formatting time
            const hours = String(now.getHours()).padStart(2, '0');
            const minutes = String(now.getMinutes()).padStart(2, '0');
            const seconds = String(now.getSeconds()).padStart(2, '0');
            this.currentTime = `${hours}:${minutes}:${seconds}`;
        
            // Formatting date
            const day = String(now.getDate()).padStart(2, '0');
            const month = String(now.getMonth() + 1).padStart(2, '0');
            const year = now.getFullYear();
            this.currentDate = `${year}-${month}-${day}`;
        }
    },
    mounted() {
        this.updateDateTime();
        this.timeInterval = setInterval(this.updateDateTime, 1000); // Update every second
    },
    beforeDestroy() {
        clearInterval(this.timeInterval); // Clean up the interval when component is destroyed
    }
}
</script>
