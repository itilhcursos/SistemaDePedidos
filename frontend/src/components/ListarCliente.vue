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
                    <th scope="col">CNPJ</th>
                    <th scope="col">CPF</th>
                    <th scope="col">Telefone</th> 
                    <th scope="col">Endereço</th>
                    <th scope="col">Bairro</th> 
                    <!-- <th scope="col">Email</th> -->
                    <!-- <th scope="col">Ativo</th> -->
                    <th scope="col" class="d-flex justify-content-end">Ações</th>
                </tr>
            </thead>
            <tbody >
                <tr v-for="cliente in listaClientes" :key="cliente.id" scope="row">
                    <th>
                        {{ cliente.id }} <!--diferença entre td e th -->
                    </th>
                    <td>
                        {{ cliente.nomeRazaoSocial}}
                    </td>
                    <td>
                        {{ cliente.cnpj}}
                    </td>
                    <td>
                        {{ cliente.cpf}}
                    </td>
                    <td>
                        {{ cliente.telefone}}
                    </td> 
                    <td>
                        {{ cliente.endereco}}
                    </td>
    
                     <td>
                        {{ cliente.bairro}}
                    </td> 
                    <!-- <td>
                        {{ cliente.email}} 
                    </td> -->
                    <!-- <td>
                        {{ cliente.ativo}}
                    </td> -->

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

    <div v-if="!formVisible">
    <hr />
    <div class="container">
      <div class="row d-flex justify-content-center">
        <div class="col-auto">

          <button
            v-for="pagina in totalPages"
            :key="pagina"
            @click.prevent="irPara(pagina)"
            class="btn btn-light ms-1"
          >
            {{ pagina }}
          </button>
        </div>

        <div class="col-auto">
          <input
            type="text"
            v-model="pageNumber"
            placeholder="Número da pagina"
            class="form-control w-25"
          />
        </div>

        <div class="col-auto">
          <select v-model="pageSize" class="form-select">
            <option value="2">2</option>
            <option value="10">10</option>
            <option value="20">20</option>
            <option value="50">50</option>
          </select>
        </div>

        <div class="col-auto">
          <select v-model="property" class="form-select">
            <option value="id">ID</option>
            <option value="nome">Nome</option>
          </select>
        </div>
        <div class="col-auto">
          <select v-model="direction" class="form-select">
            <option value="ASC">Crescente</option>
            <option value="DESC">Decrescente</option>
          </select>
        </div>
        <div class="col-auto">
          <button @click.prevent="buscarClientes" class="btn btn-success">
            <i class="bi bi-binoculars"></i>
            Buscar
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
    import FormCliente from './FormCliente.vue';
    import clienteService from '@/services/clienteService';
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
                const response = await clienteService.listar(  
                   this.pageNumber, this.pageSize, this.direction, this.property 
                );
                
                this.listaClientes = response.content;
                this.totalPages = response.totalPages;
              
            },
            novoCliente(){
                this.formVisible = !this.formVisible;
            },
            alterarCliente(cliente){
                this.clienteEscolhido = cliente;
                this.formVisible = true;
            },
            async excluirCliente(id){
               
                try{
                    const response = await clienteService.apagar(id);
                    console.log(response.data);
                }catch(error){
                    if(error.response.status === 403){    // o erro 403 é quando esqueço de fazer a autenticação    
                        alert( "Usuário não identificado! Faça o login!!!");
                    }else if(error.response.status === 400){ 
                        alert(error.response.data.mensagem);
                    }else{
                        alert( error.message);
                    }
                }
                
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
           
        },
        mounted(){
            this.buscarClientes();
        },
    };
</script>