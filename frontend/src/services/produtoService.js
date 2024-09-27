import genericService from "./genericService";

//const path ='/produto';
const pathGet ='/produtos';


const buscar = async (txtBusca, pageNumber = 1, pageSize =100000, direction='ASC', property='id') =>{

    const {data} = await genericService.buscar(pathGet, txtBusca, pageNumber, pageSize, direction, property);
    return data;

}
export default{
    buscar
}