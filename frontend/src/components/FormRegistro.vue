<template >
    <div class="container">
        <div class="row">
            <h4 class="p-1 mb-1 bg-success text-white">Cadastrar</h4>
        </div>
        <hr>
        <div>
            <label class="form-label">Login</label>
            <input 
            class="form-control"
            type="text"
            placeholder="Ex: marioSantos"
            />
        </div>
        <div>
            <label class="form-label">Senha</label>
            <input 
            class="form-control"
            type="text"
            placeholder="senha"
            />
        </div>
        <div>
            <label class="form-label">Role</label>
            <input 
            class="form-control"
            type="text"
            placeholder="role"
            />
        </div>
        <div v-if="isInvalido" class="alert alert-danger d-flex align-items-center" role="alert">
            <i class="bi bi-exclamation-triangle-fill"></i>
            <div class="p-2">{{ mensagem }}</div>
        </div>
        <div class="mb-3 d-flex justify-content-end">
            <button
                class="btn btn-primary m-2"
            >
                Salvar
            </button>
            <button
                class="btn btn-warning m-2"
            >
                Cancelar
            </button>
        </div>

    </div>
    
   
    
</template>
<script>
    export default{
        props:{
            propsRegistro:Object,
        },
        data(){
            return{

            }
        }
    }

</script>