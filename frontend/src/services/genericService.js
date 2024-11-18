import axios from "axios";

//const mode = import.meta.env.MODE;
const url= import.meta.env.VITE_APP_URL_API;

const getConfig = (
    {
        headers: {
        'Authorization': 'Bearer ' + localStorage.getItem('token')
        }
    }
);

// post
const criar = async (path, objeto) =>{

    return await axios.post(url + path, objeto, getConfig );

}

// put
const atualizar = async (path, id, objeto) =>{

    return await axios.put(url + path + "/"+ id, objeto, getConfig );

}

//delete
const apagar = async (path, id) =>{

    return await axios.delete(url + path + "/"+ id,  getConfig );

}

//get
const listar = async (path, pageNumber, pageSize, direction, property) =>{
    try {
     const url = `http://localhost:8080/${path}?pageNumber=${pageNumber}&pageSize=${pageSize}&direction=${direction}&property=${property}`;
     const response = await axios.get(url);
     return response.data;
    }catch (error) { 
        console.error('Erro ao listar :', error);
        throw error; 
    }
};
export default{
    criar,
    atualizar,
    apagar,
    listar
}
