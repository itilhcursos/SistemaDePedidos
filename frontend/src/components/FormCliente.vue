<template>
    <form>
        <div>
            O FORMulario que o usuario preenche para criar ou alterar clientes da LISTA.
        </div>
        <button v-on:click.prevent="flip">Mostrar Lista</button>
    </form>
</template>

<script>
    export default {
        methods:{
            flip(){
                this.$emit("flip", true);
            }
        }
    }
</script>