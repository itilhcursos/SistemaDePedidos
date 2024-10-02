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
      registry() {
        localStorage.setItem('login', "");
        localStorage.setItem('senha', "");
        localStorage.setItem('role', "");
        this.$router.push({path:'/'}).then(()=>{this.$router.go(0)});
      },
      logout() {
        localStorage.setItem('token', "");
        localStorage.removeItem('token', "");
        localStorage.setItem('login', "");
        localStorage.removeItem('login', "");
        this.$router.push({path:'/'}).then(()=>{this.$router.go(0)});
      },
    },
    mounted(){
      this.registry();
      this.logout();
    }
 };
</script>