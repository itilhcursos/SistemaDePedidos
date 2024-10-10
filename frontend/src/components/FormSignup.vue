<template>
    <div class="container">
        <h4 class="p-1 mb-1 bg-success text-white">Cadastrar novo Usuário</h4>
        <hr />
        <form>
            <div class="mb-3">
                <label class="form-label">Nome do Usuário</label>
                <input
                class="form-control"
                type="text"
                v-model="login"
                placeholder="Usado em Login"
                />
            </div>
            <div class="mb-3">
                <label class="form-label">Senha</label>
                <input
                class="form-control"
                type="password"
                v-model="senha"
                placeholder="Crie uma senha"
                />
            </div>
            <div class="mb-3">
                <label class="form-label">Nível do Usuário (Define as permissões)</label>
                <select v-model="role" class="form-select">
                    <option value="USER">Usuário (USER)</option>
                    <option value="ADMIN">Administrador (ADMIN)</option>
                </select>
            </div>
            <div v-if="isInvalido" class="alert alert-danger d-flex align-items-center" role="alert">
                <i class="bi bi-exclamation-triangle-fill"></i>
                <div class="p-2">{{ mensagem }}</div>
            </div>
            <div class="mb-3 d-flex justify-content-end">
                <button class="btn btn-success m-2" type="submit" @click.prevent="cadastrar"><i class="bi bi-clipboard2-check"></i> Cadastrar </button>
                <button class="btn btn-warning m-2" type="submit" @click-prevent="cancelar"><i class="bi bi-clipboard2-x"></i> Cancelar </button>
            </div>
        </form>   
    </div>
</template>

<script>
import axios from 'axios';
export default {
    data() {
        return {
            login: "",
            senha: "",
            role: "",

            isInvalido: false,
            mensagem: "",
        }
    },

    methods: {
        async cadastrar(){
            if (this.login === "" || this.senha === "" || this.role === "") {
                this.isInvalido = true;
                this.mensagem = "Preencha todos os campos!";
                return;
            } 

            try {
                const response = await axios.post("http://localhost:8080/auth/registro", {
                login: this.login,
                senha: this.senha,
                role: this.role
            });

            const dados = response.data;
            console.log(dados);
            this.$emit("voltar");

            } catch(error) {
                this.isInvalido = true;
                this.mensagem = error.response.data.mensagem;
            }
        },
        cancelar(){
            this.login = "";
            this.senha = "";
            this.role = "";
            this.$emit("voltar");
        }
    }
}
</script>
