<template>
    <div class="container">
      <h4 class="p-1 mb-1 bg-success text-white">Cadastro</h4>
      <hr />
      <form>
        <div class="mb-3">
          <label class="form-label">Cadastro</label>
          <input
            class="form-control"
            type="text"
            v-model="login"
            placeholder="cadastrar usuário"
          />
        </div>
        <div class="mb-3">
          <label class="form-label">Senha</label>
          <input
            class="form-control"
            type="password"
            v-model="senha"
            placeholder="registrar senha"
          />
        </div>
        <div class="mb-3">
        <label class="form-label">Usuário</label>
        <select v-model="role" class="form-select">
          <option :value="true">ADMIN</option>
          <option :value="false">USER</option>
        </select>
      </div>
        <div v-if="isInvalido" class="alert alert-danger d-flex align-items-center" role="alert">
          <i class="bi bi-exclamation-triangle-fill"></i>
          <div class="p-2">{{erroMensagem}}</div>
        </div>
        <div class="mb-3 d-flex justify-content-end">
          <button
            class="btn btn-primary m-2"
            type="submit"
            v-on:click.prevent="registrar"
          >
          <i class="bi bi-clipboard2-check"></i>
            Registrar
         </button>
          <button
            class="btn btn-warning m-2"
            type="submit"
            v-on:click.prevent="cancelar"
          >
          <i class="bi bi-clipboard2-x"></i>
            Cancelar
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
        role: "",
        isInvalido : false,
        erroMensagem: "",
      };
    },
    methods: {
      async registrar() {
        if (this.login === "" || this.senha === "" || this.role === "") {
          return;
        }
  
        try{
          const response = await axios.post("http://localhost:8080/auth/registro", {
              login: this.login,
              senha: this.senha,
              role: this.role,
            });
          const dados = response.data;
          console.log(dados);
          localStorage.setItem('login', dados.login);
          localStorage.setItem('senha', dados.senha);
          localStorage.setItem('role', dados.role);
          this.$router.push({path:'/'}).then(()=>{this.$router.go(0)});
       
        }catch(error){
          this.isInvalido = true;
          this.erroMensagem = "Cadastro não adicinado";
        }
  
        this.id = "";
        this.nome = "";
      },
      Registry() {
        localStorage.setItem('login', "");
        localStorage.setItem('senha', "");
          localStorage.setItem('role', "");
        this.$router.push({path:'/'}).then(()=>{this.$router.go(0)});
      },
      cancelar() {
        this.login = "";
        this.senha = "";
        this.role = "";
      },
    },
  };
</script>  