import genericService from "./genericService";

//const path ='/produto';
const pathGet ='/produtos';

const buscar = async (txtBusca, pageNumber = 1, pageSize = 10000000, direction = 'ASC', property ='id') =>{

    const {data} = await genericService.buscar(pathGet, pageNumber, pageSize, direction, property, txtBusca);
    return data;

}

export default{
    buscar,
}