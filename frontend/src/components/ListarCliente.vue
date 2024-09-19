<template>
    Template
    <div class="container">
        Container
        <div class="row">
            Row
            <div class="col-10">
                <h3>CLIENTES</h3>
            </div>


            <div class="col-2 d-flex justify-content-end">
                <button v-if="!formVisible" @click="flipFormVisible" class="btn btn-success">
                    <i class="bi bi-clipboard-plus"></i> Novo
                </button>
            </div>
            <div class="row">
                <div>
                    <FormCliente v-if="formVisible" @flip="flipFormVisible" />
                </div>
            </div>

            <table class="table table-dark table-striped" v-if="!formVisible">
                <thead>
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Nome</th>
                        <th scope="col">CNPJ</th>
                        <th scope="col">CPF</th>
                        <th scope="col">Telefone</th>
                        <th scope="col">Endereço</th>
                        <th scope="col">Bairro</th>
                        <th scope="col">CEP</th>
                        <th scope="col">E-mail</th>
                        <th scope="col">Informações</th>
                        <th scope="col">Ativo</th>
                        <th scope="col" class="d-flex justify-content-end">Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="cliente in listaClientes" :key="cliente.id" scope="row">
                        <th>
                            {{ cliente.id }}
                        </th>
                        <td>
                            {{ cliente.nomeRazaoSocial }}
                        </td>
                        <td>
                            {{ cliente.cnpj }}
                        </td>
                        <td>
                            {{ cliente.cpf }}
                        </td>
                        <td>
                            {{ cliente.telefone }}
                        </td>
                        <td>
                            {{ cliente.endereco }}
                        </td>
                        <td>
                            {{ cliente.bairro }}
                        </td>
                        <td>
                            {{ cliente.cep }}
                        </td>
                        <td>
                            {{ cliente.e - mail }}
                        </td>
                        <td>
                            {{ cliente.informacao }}
                        </td>
                        <td>
                            {{ cliente.ativo }}
                        </td>
                        <td class="d-flex justify-content-end">
                            <button class="btn btn-btn btn-primary m-2" @click="alterar()"><i
                                    class="bi bi-clipboard-pulse"></i> Alterar</button>
                            <button class="btn btn-btn btn-primary m-2" @click="excluir()"><i
                                    class="bi bi-clipboard2-minus"></i> Excluir</button>
                        </td>

                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

<script>
import FormCliente from './FormCliente.vue';
import clienteService from '@/services/clienteService';
export default {
    components: {
        FormCliente
    },
    data() {
        return {
            listaClientes: [],
            clienteEscolhido: null,
            formVisible: false,
            pageNumber: 1,
            pageSize: 10,
            direction: "ASC",
            property: "id",
            totalPages: 0,
        }
    },
    methods: {
        async listarClientes() {
            this.clienteEscolhido = null;
            this.formVisible = false;

            const response = await clienteService.listar(this.pageNumber, this.pageSize, this.direction, this.property);

            this.listaClientes = response.content;
            this.totalPages = response.totalPages;
            console.log(this.totalPages);
        },
        novoCliente() {
            alert("em construção")
        },
        alterar() {
            alert("wip alterar")
        },
        async excluir() {
            alert("wip excluir")
        },
        flipFormVisible() {
            this.formVisible = !this.formVisible
        }
    },
    mounted() {
        this.listarClientes();
    },
};
</script>
