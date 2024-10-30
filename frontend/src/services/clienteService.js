import genericService from "./genericService";

const pathGet = '/clientes';
const pathPostPut = '/cliente';

const buscar = async (txtBusca, pageNumber = 1, pageSize = 10000000, direction = 'ASC', property = 'id') => {
    const { data } = await genericService.buscar(pathGet, pageNumber, pageSize, direction, property, txtBusca);
    return data;
};

const criar = async (cliente) => {
    const { data } = await genericService.post(pathPostPut, cliente);
    return data;
};

const alterar = async (id, cliente) => {
    const { data } = await genericService.put(`${pathPostPut}/${id}`, cliente);
    return data;
};

export default {
    buscar,
    criar,
    alterar,
};
