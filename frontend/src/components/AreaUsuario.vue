<template>
    <div class="container">
        <div>
            <h3>Área do Usuário</h3> <hr/>
        </div>
        <div class="container">
            <div class="row" style="justify-content: start">
                <button style="width: 350px"
                v-if="!formVisibleSignup && !formVisibleLogin" @click="signup" class="btn btn-success"><i class="bi bi-clipboard2-check"></i> Cadastrar novo Usuário </button>
            </div> 
            <div class="row" style="justify-content: start; padding-top: 20px">
                <button style="width: 350px"
                v-if="!formVisibleSignup && !formVisibleLogin && !logado" @click="login" class="btn btn-primary"><i class="bi bi-clipboard2-check"></i> Iniciar Sessão </button>
            </div>
            <div class="row" style="justify-content: start">
                <button style="width: 350px"
                v-if="!formVisibleSignup && !formVisibleLogin && logado" @click="logout" class="btn btn-danger m-2"><i class="bi bi-x-octagon"></i> Encerrar Sessão </button>
            </div>
        </div>
        <div class="row" style="width: 55%">
            <div>
                <FormSignup
                v-if="formVisibleSignup"
                @voltar="cancelar"
                />
                <FormLogin
                v-if="formVisibleLogin"
                @voltar="cancelar"
                />
            </div>
        </div>
    </div>
</template>

<script>
import FormSignup from './FormSignup.vue';
import FormLogin from './FormLogin.vue';

export default {
    components:{
        FormSignup,
        FormLogin,
    },

    data(){
        return {
            formVisibleSignup: false,
            formVisibleLogin: false,
        }
    },

    methods: {
        signup() {
            this.formVisibleSignup = true;
            this.formVisibleLogin = false;
        },
        login() {          
            this.formVisibleSignup = false;
            this.formVisibleLogin = true;
        },
        cancelar() {
            this.formVisibleSignup = false;
            this.formVisibleLogin = false;
        },
        logout() {
            localStorage.setItem('token', "");
            localStorage.removeItem('token', "");
            localStorage.setItem('login', "");
            localStorage.removeItem('login', "");
            this.$router.push({path:'/'}).then(()=>{this.$router.go(0)});
        }
    },

    computed: {
        logado() {
            const token = localStorage.getItem('token');
            const login = localStorage.getItem('login');
            if (token !== null && login !== null) {
                return true
            } else return false;
        }
    }
}
</script>