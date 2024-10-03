<template>
    <div class="container">
        <h4 class="p-1 mb-1 bg-success text-white">{{ getAcao }} Cliente</h4>
        <hr />
        <form>
            <!-- <div class="mb-3">
                <label class="form-label">Id</label>
                <input class="form-control" type="text" v-model="id" :disabled="true" placeholder="Id cliente" />
            </div> -->
            <div class="mb-3">
                <label class="form-label">Cliente</label>
                <input class="form-control" type="text" v-model="nomeRazaoSocial" placeholder="Nome ou Razão Social" />
            </div>
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label class="form-label">CNPJ</label>
                    <input class="form-control" type="text" v-model="cnpj" placeholder="CNPJ" />
                </div>
                <div class="col-md-6 mb-3">
                    <label class="form-label">CPF</label>
                    <input class="form-control" type="text" v-model="cpf" placeholder="CPF" />
                </div>
                <div class="mb-3">
                    <label class="form-label">Endereço</label>
                    <input class="form-control" type="text" v-model="endereço"
                        placeholder="Endereço (Rua, Avenida, Alameda, etc)" />
                </div>
                <div class="col-md-6 mb-3">
                    <label class="form-label">Bairro</label>
                    <input class="form-control" type="text" v-model="bairro" placeholder="Bairro" />
                </div>
                <div class="col-md-6 mb-3">
                    <label class="form-label">CEP</label>
                    <input class="form-control" type="text" v-model="cep" placeholder="CEP" />
                </div>
                <div class="col-md-6 mb-3">
                    <label class="form-label">Telefone</label>
                    <input class="form-control" type="text" v-model="telefone" placeholder="Telefone (com DDD)" />
                </div>
                <div class="col-md-6 mb-3">
                    <label class="form-label">Email</label>
                    <input class="form-control" type="text" v-model="email" placeholder="nome@exemplo.com" />
                </div>
                <div class="col-md-6 mb-3">
                    <label class="form-label">Estado</label>
                    <select v-model="estadoSelected" class="form-select">
                        <option v-for="estado in estados" :key="estado.id" :value="estado.id">
                            {{ estado.nome }}
                        </option>
                    </select>
                </div>
                <div class="col-md-6 mb-3">
                    <label class="form-label">Município</label>
                    <select v-model="municipioSelected" class="form-select">
                        <option v-for="municipio in municipios" :key="municipio.id" :value="municipio.id">
                            {{ municipio.nome }}
                        </option>
                    </select>
                </div>
                <div class="mb-3">
                    <label class="form-label">Informações</label>
                    <input class="form-control" type="text" v-model="informacoes"
                    placeholder="Informações Adicionais" />
                </div>
                
                <div class="col-md-6 mb-3">
                    <label class="form-label">Ativo</label>
                    <select v-model="ativo" class="form-select">
                        <option :value="true">Sim</option>
                        <option :value="false">Não</option>
                    </select>
                </div>
            </div>
            <div v-if="isInvalido" class="alert alert-danger d-flex align-items-center" role="alert">
                <i class="bi bi-exclamation-triangle-fill"></i>
                <div class="p-2">{{ mensagem }}</div>
            </div>
            <div class="mb-3 d-flex justify-content-end">
                <button class="btn btn-primary m-2" type="submit" v-on:click.prevent="salvarCliente">
                    <i class="bi bi-clipboard2-check"></i>
                    {{ getAcao }}
                </button>
                <button class="btn btn-warning m-2" type="submit" v-on:click.prevent="cancelar">
                    <i class="bi bi-clipboard2-x"></i>
                    Cancelar
                </button>
            </div>
        </form>
    </div>
</template>

<script>
import clienteService from '@/services/clienteService';
import municipioService from '@/services/municipioService';
import estadoService from '@/services/estadoService';
export default {
    props: {
        propsCliente: Object,
    },
    data() {
        return {
            id: "",
            nome: "",
            cnpj: "",
            cpf: "",
            endereço: "",
            bairro: "",
            cep: "",
            telefone: "",
            email: "",
            informacoes: "",
            estadoSelected: null,
            estados: [],
            municipioSelected: null,
            municipios: [],
            ativo: "",
            isInvalido: false,
            mensagem: "",
        };
    },
    methods: {
        getDados() {
            return {
                id: this.id,
                nome: this.nome,
                cnpj: this.cnpj,
                cpf: this.cpf,
                endereço: this.endereco,
                bairro: this.bairro,
                cep: this.cep,
                telefone: this.cep,
                email: this.email,
                informacoes: this.informacoes,
                estadoId: this.estadoSelected,
                municipioId: this.municipioSelected,
                ativo: this.ativo,
            };
        },
        async salvarCliente() {
            if (this.nome === "" || this.municipioSelected === null || this.estadoSelected === null) {
                this.isInvalido = true;
                this.mensagem = "Nome do cliente ou razão social, município e estado não pode ser vazio.";
                return;
            }
            this.isInvalido = false;
            try {
                if (this.id === "") {
                    const response = await clienteService.criar(this.getDados());
                    this.listaClientes = response;
                } else {
                    const response = await clienteService.atualizar(this.id, this.getDados());
                    this.listaClientes = response;
                }
                this.$emit("salvar_cliente", this.getDados());

                // Resetar campos
                this.resetarCampos();
            } catch (error) {
                this.isInvalido = true;
                this.mensagem = this.getMensagemErro(error);
            }
        },
        cancelar() {
            this.resetarCampos();
            this.$emit("cancelar", true);
        },
        async carregarEstados() {
            try {
                const response = await estadoService.listar(1, 100, 'ASC', 'id');
                this.estados = response.content;
            } catch (error) {
                console.error("Erro ao carregar estados:", error);
            }
        },
        async carregarMunicipios() {
            try {
                const response = await municipioService.listar(1, 10000, 'ASC', 'id');
                this.municipios = response.content;
            } catch (error) {
                console.error("Erro ao carregar municipios:", error);
            }
        },
        resetarCampos() {
            this.id = "";
            this.nome = "";
            this.cnpj = "";
            this.cpf = "";
            this.endereço = "";
            this.bairro = "";
            this.cep = "";
            this.telefone = "";
            this.email = "";
            this.informacoes = "";
            this.estadoSelected = null;
            this.municipioSelected = null;
            this.ativo = "";
        },
        getMensagemErro(error) {
            if (error.response && error.response.status === 403) {
                return "Usuário não identificado! Faça o login!!!";
            } else if (error.response && error.response.status === 400) {
                return error.response.data.mensagem;
            } else {
                return error.message;
            }
        }
    },
    mounted() {
        if (this.propsCliente) {
            this.id = this.propsCliente.id;
            this.nome = this.propsCliente.nome;
            this.cnpj = this.propsCliente.cnpj;
            this.cpf = this.propsCliente.cpf;
            this.endereço = this.propsCliente.endereco;
            this.bairro = this.propsCliente.bairro;
            this.cep = this.propsCliente.cep;
            this.telefone = this.propsCliente.telefone;
            this.email = this.propsCliente.email;
            this.informacoes = this.propsCliente.informacoes;
            this.municipioSelectd = this.propsCliente.municipioId; 
            this.estadoSelectd = this.propsCliente.estadoId; 
            this.ativo = this.propsCliente.ativo;
        }
        this.carregarEstados();
        this.carregarMunicipios();
    },
    computed: {
        getAcao() {
            return this.id === "" ? "Incluir" : "Alterar";
        },
    },
};
</script>
