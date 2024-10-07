import genericService from "./genericService";

const path = '/produto';
const pathGet = '/produtos';

const criar = async(objeto)=>{

    const{data} = await genericService.criar(path, objeto);
    return data;

}

const atualizar = async(id, objeto)=>{
    
    const{data} = await genericService.atualizar(path, id, objeto);
    return data;

}

const apagar = async(id)=>{
    
    const{data} = await genericService.apagar(path, id);
    return data;

}

const listar = async( pageNumber = 1, pageSize = 10, direction = 'ASC', property = 'id')=>{
    
    const{data} =  await genericService.listar(pathGet, pageNumber, pageSize, direction, property);
    return data;

}
const buscar = async (txtBusca, pageNumber = 1, pageSize = 100000, direction = 'ASC', property = 'id')=>{
    
   const {data} = await genericService.buscar(pathGet, pageNumber, pageSize, direction, property, txtBusca);
   return data;
}

export default{
    criar,
    atualizar,
    apagar,
    listar,
    buscar
}

