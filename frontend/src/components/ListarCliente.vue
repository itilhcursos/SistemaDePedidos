<template>
    <div>
        <div class="row">
            <div class="col-10">
                <h3>CLIENTES</h3>
            </div>
            <div class="col-2 d-flex justify-content-end">
                <button v-if="!formVisible" @click="novoCliente" class="btn btn-success">
                   <i class="bi bi-clipboard-plus"></i> Novo
                </button>
            </div>
            <div class="row">
                <div>
                    <FormCliente
                        v-if="formVisible"
                        :propsCliente="clienteEscolhido"
                        @cancelar="limpar"
                        @salvar_cliente="buscarClientes"
                    />
                </div>
            </div>
        </div>
        <table v-if="!formVisible" class="table table-dark table-striped">
            <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Nome</th>
                    <th scope="col">Cliente id</th>
                    <th scope="col">CNPJ</th>
                    <th scope="col">CPF</th>
                    <th scope="col">Telefone</th>
                    <th scope="col">Endereço</th>
                    <th scope="col">Bairro</th>
                    <th scope="col">Cep</th>
                    <th scope="col">Email</th>
                    <th scope="col">Ativo</th>
                    <th scope="col">Informação</th>
                    <th scope="col" class="d-flex justify-content-end">Ações</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="cliente in listaClientes" :key="cliente.id" scope="row">
                    <td class="d-flex justify-content-end">
                        <button
                        class="btn btn-btn btn-primary m-2"
                        @click="alterarCliente(cliente)"
                        >
                        <i class="bi bi-clipboard-pulse"></i> Alterar
                        </button>

                        <button
                        class="btn btn-outline-danger m-2"
                        @click="excluirCliente(cliente.id)"
                        >
                        <i class="bi bi-clipboard2-minus"></i> Excluir
                        </button>
                    </td>
                </tr>  
            </tbody>

        </table>
    </div>
</template>
<script>
    import FormCliente from './FormCliente.vue';
    import axios from 'axios';
    export default{
        components:{
            FormCliente,
        },
        data(){
            return{
                listaClientes: [],
                clienteEscolhido: null,
                formVisible: false,
                mode: import.meta.env.MODE,
                url: import.meta.env.VITE_APP_URL_API,
                pageNumber: 1,
                pageSize: 10,
                direction: "ASC",
                property: "id",
                totalPages: 0,
            };
        },
        methods:{
            async buscarClientes(){
                this.clienteEscolhido = null;
                this.formVisible = false;
                const response = await axios.get(  
                    `http://localhost:8080/clientes?pageNumber=${this.pageNumber}&pageSize=${this.pageSize}&direction=${this.direction}&property=${this.property}` 
                );
                console.log(response.data);
                this.listaClientes = response.data.content;
                this.totalPages = response.data.totalPages;
                console.log(this.totalPages);
            },
            novoCliente(){
                this.formVisible = !this.formVisible;
            },
            alterarCliente(cliente){
                this.clienteEscolhido = cliente;
                this.formVisible = true;
            },
            async excluirCliente(id){
                let config = {
                    headers:{
                        'Authorization': 'Bearer ' +localStorage.getItem('token')
                    }
                }
                const response = await axios.delete(
                    `http://localhost:8080/cliente/${id}`
                    , config
                );
                console.log(response.data);
                this.buscarClientes();
            },
            limpar(){
                this.clienteEscolhido = null;
                this.formVisible = !this.formVisible;
            },
            irPara(pagina){
                this.pageNumber = pagina;
                this.buscarClientes();
            },
            mounted(){
                this.buscarClientes();
            }
        }
    }
</script>