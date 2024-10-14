<template>
    <div class="container">
        <div class="row">
            <h4 class="p-1 mb-1 bg-success text-white">cadastrar</h4>
        </div>
        <hr>
        <div>
            <label class="form-label">Usuário</label>
            <input class="form-control" type="text" v-model="login" placeholder="Insira seu nome de usuario" />
        </div>
        <div>
            <label class="form-label">Senha</label>
            <input class="form-control" type="password" v-model="senha" placeholder="senha" />
        </div>
        <div>
            <label class="form-label">Permissões do Usuário</label>
            <select v-model="role" class="form-select">
                <option value="0">ADMIN</option>
                <option value="1">USER</option>
            </select>
        </div>

        <div v-if="isInvalido" class="alert alert-danger d-flex align-items-center" role="alert">
            <i class="bi bi-exclamation-triangle-fill"></i>
            <div class="p-2">{{ erroMensagem }}</div>
        </div>

        <div class="mb-3 d-flex justify-content-end">
            <button class="btn btn-primary m-2" type="submit" v-on:click.prevent="registrar">
                <i class="bi bi-clipboard2-check"></i> Cadastrar
            </button>
            <button class="btn btn-warning m-2" type="submit" v-on:click.prevent="cancelar">
                <i class="bi bi-clipboard2-x"></i>Cancelar
            </button>
        </div>
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
            erroMensagem: ""
        };
    },
    methods: {
        async registrar() {
            // Validação de campos vazios
            if (this.login === "" || this.senha === "" || this.role === "") {
                this.isInvalido = true;
                this.erroMensagem = "Todos os campos são obrigatórios.";
                return;
            }

            try {
                // Enviando os dados para o servidor
                const response = await axios.post("http://localhost:8080/registro", {
                    login: this.login,
                    senha: this.senha,
                    role: this.role,
                });

                const dados = response.data;
                console.log(dados);

                // Armazenando dados
                localStorage.setItem('login', dados.login);
                localStorage.setItem('role', dados.role);

                // Redireciona para pagina principal
                this.$router.push({ path: '/' }).then(() => { this.$router.go(0) });

            } catch (error) {
                
                this.isInvalido = true;
                this.erroMensagem = error.response?.data?.mensagem || "Erro ao registrar o usuário.";
            }
        },

        cancelar() {
            // limpar campos
            this.login = "";
            this.senha = "";
            this.role = "";
            this.isInvalido = false;
            this.erroMensagem = "";
        }
    }
};
</script>
