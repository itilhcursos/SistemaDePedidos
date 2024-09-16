<template>
  <div class="container">
    <h4 class="p-1 mb-1 bg-success text-white">Login</h4>
    <hr />
    <form>
      <div class="mb-3">
        <label class="form-label">Login</label>
        <input
          class="form-control"
          type="text"
          v-model="login"
          placeholder="login"
        />
      </div>
      <div class="mb-3">
        <label class="form-label">Senha</label>
        <input
          class="form-control"
          type="password"
          v-model="senha"
          placeholder="senha"
        />
      </div>
      <div v-if="isInvalido" class="alert alert-danger d-flex align-items-center" role="alert">
        <i class="bi bi-exclamation-triangle-fill"></i>
        <div class="p-2">{{erroMensagem}}</div>
      </div>
      <div class="mb-3 d-flex justify-content-end">
        <button
          class="btn btn-primary m-2"
          type="submit"
          v-on:click.prevent="logar"
        >
        <i class="bi bi-clipboard2-check"></i>
          Logar
       </button>
        <button
          class="btn btn-warning m-2"
          type="submit"
          v-on:click.prevent="cancelar"
        >
        <i class="bi bi-clipboard2-x"></i>
          Cancelar
        </button>
        <button
          class="btn btn-danger m-2"
          type="submit"
          v-on:click.prevent="logout"
        >
        <i class="bi bi-x-octagon"></i>
          Logout
        </button>
      </div>
    </form>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      login: "",
      senha: "",
      token: "",
      isInvalido : false,
      erroMensagem: "",
    };
  },
  methods: {
    async logar() {
      if (this.login === "" || this.senha ==="") {
        return;
      }

      try{
        const response = await axios.post("http://localhost:8080/auth/login", {
            login: this.login,
            senha: this.senha,
          });
        const dados = response.data;
        console.log(dados);
        localStorage.setItem('token', dados.token);
<<<<<<< HEAD
        localStorage.setItem('login', dados.login);        
=======
        localStorage.setItem('login', dados.login);
>>>>>>> master
        this.$router.push({path:'/'}).then(()=>{this.$router.go(0)});
     
      }catch(error){
        this.isInvalido = true;
        this.erroMensagem = error.response.data.mensagem;
      }

      this.id = "";
      this.nome = "";
    },
    cancelar() {
      this.login = "";
      this.senha = "";
    },
    logout() {
      localStorage.setItem('token', "");
      localStorage.removeItem('token', "");
      localStorage.setItem('login', "");
<<<<<<< HEAD
      localStorage.removeItem('login', "");      
      this.$router
          .push({ path: '/' })
          .then(() => { this.$router.go(0) })
=======
      localStorage.removeItem('login', "");
      this.$router.push({path:'/'}).then(()=>{this.$router.go(0)});
>>>>>>> master
    },
  },

  
};
</script>

