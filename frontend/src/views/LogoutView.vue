<template>
    <main>

    </main>
</template>
<script>
    export default{
        data(){
            return{};
        },
        methods:{
            logout(){
                localStorage.setItem('token', "");
                localStorage.removeItem('token', "");
                localStorage.setItem('login', "");
                localStorage.removeItem('login', "");
                this.$router.push({path:'/'}).then(()=>{this.$router.go(0)});
                
            },
        },
        mounted(){
            this.logout();
        }
    };
</script>