<template>
    <div  class="container">
        <div class="row">
            <div class="col-10">
                <h3>MUNICIPIOS</h3>
            </div>
            <div class="col-2 d-flex justify-content-end">
                <button v-if="!formVisible" @click="novoMunicipio" class="btn btn-success">
                   <i class="bi bi-clipboard-plus"></i> Novo
                </button>
            </div>
            <div class="row">
                <div>
                    <FormMunicipio
                        v-if="formVisible"
                        :propsMunicipio="municipioEscolhido"
                        @cancelar="limpar"
                        @salvar_municipio="buscarMunicipios"
                    />
                </div>
            </div>
        </div>

        <table v-if="!formVisible" class="table table-dark table-striped">
            <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Nome</th>
                    <th scope="col">Entrega</th>
                    <th scope="col">Estado(ID)</th>
                    <th scope="col">Estado(Nome)</th>
                    <th scope="col" class="d-flex justify-content-end">Ações</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="municipio in listaMunicipios" :key="municipio.id" scope="row">
                    <th>{{ municipio.id }}</th>
                    <td>{{ municipio.nome }}</td>
                    <td>{{ formatarLogico(municipio.entrega) }}</td>
                    <td>{{ municipio.estadoId }}</td>
                    <td>{{ municipio.estadoNome }}</td>
                    <td
                    class="d-flex justify-content-end"
                    >
                        <button
                            class="btn btn-btn btn-primary m-2"
                            @click="alterarMunicipio(municipio)"
                        >
                        <i class="bi bi-clipboard-pulse"></i>Alterar 
                        </button>
                        <button
                            class="btn btn-outline-danger m-2"
                            @click="excluirMunicipio(municipio.id)"
                        >
                         <i class="bi bi-clipboard2-minus"></i>  Excluir
                        </button>
                    </td>
                </tr> 
            </tbody>
        </table>
    </div>
    <div v-if="!formVisible" >
        <hr>
        <div  class="container">
            <div class="row d-flex justify-content-center">
                <div class="col-auto">
                    <button
                        v-for="pagina in totalPages"
                        :key="pagina"
                        @click.prevent="irPara(pagina)"
                         class="btn btn-light ms-1"
                    >
                    <!-- v-on: click === @click -->
                        {{ pagina }}
                    </button>
                </div>
                <div class="col-auto">
                    <input type="text"
                    v-model="pageNumber"
                    placeholder="Número da pagina"
                    class="form-control w-25"
                    />
                </div>
                <div class="col-auto">
                    <select v-model="pageSize" class="form-select">
                        <option value="2">2</option>
                        <option value="5">5</option>
                        <option value="15">15</option>
                        <option value="30">30</option>
                    </select>
                </div> 
                <div class="col-auto">
                    <select v-model="property" class="form-select">
                        <option value="id">ID</option>
                        <option value="nome">Nome</option>
                        <option value="entrega">Entrega</option>
                        <option value="estadoId">Estado_ID</option>
                        <option value="estadoNome">Estado_Nome</option>
                    </select>
                </div>
                <div class="col-auto">
                    <select v-model="direction" class="form-select">
                        <option value="ASC">Crescente</option>
                        <option value="DESC">Decrescente</option>
                    </select>
                </div>
                <div class="col-auto">
                    <button
                        @click.prevent="buscarMunicipios"
                        class="btn btn-success"
                    >
                        <i class="bi bi-binoculars"></i>
                        Buscar
                    </button>
                </div>
            </div> 
        </div>
    </div>
</template>

<script>
import FormMunicipio from './FormMunicipio.vue';
import municipioService from '@/services/municipioService';
import Logico from '@/utils/Logico';
export default {
    components:{
      FormMunicipio,
    },
    data(){
        return{
            listaMunicipios:[],
            municipioEscolhido: null,
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
        async buscarMunicipios(){
            this.municipioEscolhido = null;
            this.formVisible = false;
            const response = await municipioService.listar(
                this.pageNumber, this.pageSize, this.direction, this.property
            );
           
            this.listaMunicipios = response.content;
            this.totalPages = response.totalPages;
           
        },
        novoMunicipio(){
            this.formVisible = !this.formVisible;
        },
        alterarMunicipio(municipio){
            this.municipioEscolhido = municipio;
            this.formVisible = true;
        },
        async excluirMunicipio(id){
            //    let config ={
            //         headers:{
            //         'Authorization': 'Bearer ' +localStorage.getItem('token')
            //         }
            //    }
            try{
                const response = await municipioService.apagar(id);
                console.log(response.data);
            }catch(error){
                console.log(error.response);
                if(error.response.status === 403){    // o erro 403 é quando esqueço de fazer a autenticação    
                alert( "Usuário não identificado! Faça o login!!!");
            }else if(error.response.status === 400){ 
                
                alert(error.response.data.mensagem);
            }else{
                alert( error.message);
            }
            }
           
            this.buscarMunicipios();
        },
        limpar(){
            this.municipioEscolhido = null;
            this.formVisible = !this.formVisible;
        },
        irPara(pagina){
            this.pageNumber = pagina;
            this.buscarMunicipios();
        },
        formatarLogico(valor){
            return Logico.toSimNao(valor);
        }
        
    },
    mounted(){
        this.buscarMunicipios();
    }
}
</script>