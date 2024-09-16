import generico from './generico.js';

const path = '/estado';
const pathGet = '/estados';

const criar = async (objeto) =>{

    const {data} = await generico.criar(path, objeto);
    return data.result;

};

const atualizar = async (id, objeto) =>{

    const {data} = await generico.atualizar(path, id, objeto);
    return data.result;

};

const apagar = async (id) =>{
    const {data} = await generico.apagar( path, id);
    return data.result;
}

const listar = async (pageNumber = 1, pageSize = 20, direction = "ASC", property = "id" ) =>{
    
    const {data} = await generico.listar(pathGet, pageNumber, pageSize, direction, property);
    return data;

}

export default {
    criar,
    atualizar,
    apagar,
    listar
};