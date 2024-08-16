
const toSimNao =(valor) =>{
   return valor? "Sim":"Não";
}

const toAtivoInativo = (valor) =>{
    return valor? "Ativo":"Inativo"
}

export default{
    toSimNao,
    toAtivoInativo
}