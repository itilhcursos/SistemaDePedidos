<template>
    <div>
        <div>
            <div>
                <h3>Produtos</h3>
            </div>
            <div>
                <button>
                    <i></i>Novo
                </button>
            </div>
            <div>
                <div>

                </div>
            </div>
        </div>
        <table class="table table-dark table-striped" v-if="!formVisible">
            <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Descrição</th>
                    <th scope="col">Quantidade</th>
                    <th scope="col">Preço/Unidade</th>
                    <th scope="col">Ativo</th>
                    <th scope="col" class="d-flex justify-content-end">Ações</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="produto in listaProdutos" :key="produto.id" scope="row">
                    <th>
                        {{ produto.id }}
                    </th>
                    <td>

                    </td>
                    <td>

                    </td>
                    <td>

                    </td>
                    <td>

                    </td>
                        <td>
                        <button>
                            Alterar
                        </button>
                        <button>
                            Excluir
                        </button>   
                    </td>
                    
                </tr>
            </tbody>
        </table>
    </div>
    <div>
        <hr>
        <div>
            <div>
                <div>
                    <button>
                        
                    </button>
                </div>
                <div>
                    <input 
                        type="text"
                        placeholder="Número da Pagina"
                    />
                </div>
                <div>
                    <select name="" id="">
                        <option value="2">2</option>
                        <option value="10">10</option>
                        <option value="20">15</option>
                        <option value="50">30</option>
                    </select>
                </div>
                <div>
                    <select name="" id="">
                        <option value="id">ID</option>
                        <option value="descricao">Descrição</option>
                        <option value="quantidadeEstoque">Quantidade</option>
                        <option value="precoUnidadeAtual">Preço/Unidade</option>
                        <option value="ativo">Ativo</option>
                    </select>
                </div>
                <div>
                    <select name="" id="">
                        <option value="ASC">Crescente</option>
                        <option value="DESC">Decrecente</option>
                    </select>
                </div>
                <div>
                    <button>
                        Buscar
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>

import axios from 'axios';

export default {
    components:{
        
    },
    data() {
        return{
            listaProdutos:[],
            produtoEscolhido:null,
            formVisible:false,
            mode: import.meta.env.MODE,
            url: import.meta.env.VITE_APP_URL_API,
            pageNumber:1,
            pageSize:10,
            direction: "ASC",
            property: "id",
            totalPage:0,  
        };
    },
    methods:{
        async buscarProdutos(){
            this.produtoEscolhido = null;
            this.formVisible = false;
            const response = await axios.get(
                `http://localhost:8080/produtos?pageNumber=${this.pageNumber}&pageSize=${this.pageSize}&direction=${this.direction}&property=${this.property}`
            );
            console.log(response.data);
            this.listaProdutos = response.data.content;
            

        }
    }
}
</script>
