import genericService from "./genericService";

const path = '/item-pedido';
//const pathGet = '/itens-pedido';

const criar = async(objeto)=>{

    const{data} = await genericService.criar(path, objeto);
    return data;
}

const apagar = async(id)=>{
    
    const{data} = await genericService.apagar(path, id);
    return data;

}



export default{
    criar,
    apagar,
}

