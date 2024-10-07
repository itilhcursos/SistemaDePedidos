<template>
    <div class="container">
        <h4 class="p-1 mb-1 bg-success text-white">{{ getAcao }} Cliente</h4>
        <hr />
        <form>
            <div v-if="id !== ''" class="col-md-1 mb-3">
            <label class="form-label">Id</label>
            <input class="form-control" type="text" v-model="id" :disabled="true" placeholder="Id cliente" />
        </div>
            <div class="mb-3">
                <label class="form-label">Cliente*</label>
                <input class="form-control" type="text" v-model="nomeRazaoSocial" placeholder="Nome ou Razão Social" />
            </div>
            <div class="row">
                <div class="col-md-6 mb-3" v-if="getCpf">
                    <label class="form-label">CPF*</label>
                    <input class="form-control" type="text" v-model="cpf" placeholder="CPF" />
                </div>
                <div class="col-md-6 mb-3" v-if="getCnpj">
                    <label class="form-label">CNPJ*</label>
                    <input class="form-control" type="text" v-model="cnpj" placeholder="CNPJ" />
                </div>
                <div class="mb-3">
                    <label class="form-label">Endereço</label>
                    <input class="form-control" type="text" v-model="endereco"
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
                    <label class="form-label">Município*</label>
                    <v-select class="meu-select" v-model="selectedMunicipio" :filterable="false"
                        :options="optionsMunicipio" @search="onSearchMunicipio">
                        <template v-slot:no-options>
                            Não encontrado.
                        </template>
                        <template v-slot:option="option">
                            {{ option.nome }}
                        </template>
                        <template v-slot:selected-option="option">
                            {{ option.nome }}
                        </template>
                    </v-select>
                </div>
                <div class="col-md-6 mb-3">
                    <label class="form-label">Ativo</label>
                    <select v-model="ativo" class="form-select">
                        <option :value="true">Sim</option>
                        <option :value="false">Não</option>
                    </select>
                </div>
            </div>
            <div class="mb-3">
                <label class="form-label">Informações</label>
                <input class="form-control" type="text" v-model="informacoes" placeholder="Informações Adicionais" />
            </div>
            <div class="mb-3">
                <label class="form-label">*Preenchimento obrigatório</label>
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
export default {
    props: {
        propsCliente: Object,
    },
    data() {
        return {
            id: "",
            nomeRazaoSocial: "",
            cnpj: "",
            cpf: "",
            endereco: "",
            bairro: "",
            cep: "",
            telefone: "",
            email: "",
            informacoes: "",
            selectedMunicipio: null,
            optionsMunicipio: [],
            ativo: "",
            isInvalido: false,
            mensagem: "",
        };
    },
    methods: {
        async onSearchMunicipio(search, loading) {
            if (search == "")
                return;
            loading(true);
            await municipioService.buscar(search).then((response) => {
                //console.log(response);
                this.optionsMunicipio = response.content;
                loading(false);
            });
        },
        getDados() {
            return {
                id: this.id,
                nomeRazaoSocial: this.nomeRazaoSocial,
                cnpj: this.cnpj,
                cpf: this.cpf,
                endereco: this.endereco,
                bairro: this.bairro,
                cep: this.cep,
                telefone: this.cep,
                email: this.email,
                informacoes: this.informacoes,
                municipioId: this.selectedMunicipio ? this.selectedMunicipio.id : null,
                ativo: this.ativo,
            };
        },
        async salvarCliente() {
            if (this.nomeRazaoSocial === "") {
                this.isInvalido = true;
                this.mensagem = "Nome do cliente ou razão social não pode ser vazio.";
                return;
            }
            if (!this.selectedMunicipio) {
                this.isInvalido = true;
                this.mensagem = "Município deve ser selecionado.";
                return;
            }
            if (this.cpf === "" && this.cnpj === "") {
                this.isInvalido = true;
                this.mensagem = "Número do CPF ou CNPJ deve ser preenchido.";
                return;
            }
            // if (this.cpf !== "" && this.cnpj !== "") {
            //     this.isInvalido = true;
            //     this.mensagem = "Preencha apenas o CPF ou o CNPJ, não ambos.";
            //     return;
            // }
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
            resetarCampos() {
                this.id = "";
                this.nomeRazaoSocial = "";
                this.cnpj = "";
                this.cpf = "";
                this.endereco = "";
                this.bairro = "";
                this.cep = "";
                this.telefone = "";
                this.email = "";
                this.informacoes = "";
                this.selectedMunicipio = null;
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
                this.nomeRazaoSocial = this.propsCliente.nomeRazaoSocial;
                this.cnpj = this.propsCliente.cnpj;
                this.cpf = this.propsCliente.cpf;
                this.endereco = this.propsCliente.endereco;
                this.bairro = this.propsCliente.bairro;
                this.cep = this.propsCliente.cep;
                this.telefone = this.propsCliente.telefone;
                this.email = this.propsCliente.email;
                this.informacoes = this.propsCliente.informacoes;
                this.selectedMunicipio = { id: this.propsCliente.municipioId, nome: this.propsCliente.municipioNome };
                this.ativo = this.propsCliente.ativo;

                if (this.cpf && this.cpf !== "") {
                    this.cnpj = "";
                } else if (this.cnpj && this.cnpj !== "") {
                    this.cpf = "";
                }
            }
        },
        computed: {
            getAcao() {
                return this.id === "" ? "Incluir" : "Alterar";
            },
            getCnpj() {
                return this.cpf === '' ? true : false;
            },
            getCpf() {
                return this.cnpj === '' ? true : false;
            }
        },
    };
</script>

<style>
.meu-select {
    width: 100%;
    font-size: 1.0em;
    color: #252525;
    background: #fbf4f4;
    border-radius: 0.375rem;
}
</style>