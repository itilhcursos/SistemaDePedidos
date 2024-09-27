import axios from "axios";

// const mode= import.meta.env.MODE;
const url= import.meta.env.VITE_APP_URL_API;

const getConfig = (
    {
        headers: {
        'Authorization': 'Bearer ' + localStorage.getItem('token')
        }
    }
);

// método post - criar
const criar = async (path, objeto) => {
    return await axios.post(url + path, objeto, getConfig);
};

// método put - alterar
const alterar = async (path, id, objeto) => {
    return await axios.put(url + path + "/" + id, objeto, getConfig);
};

// método delete - apagar
const apagar = async (path, id) => {
    return await axios.delete(url + path + "/" + id, getConfig);
};

// método get - listar
const listar = async (path, pageNumber, pageSize, direction, property) => {

    return await axios.get(url + path +`?pageNumber=${pageNumber}&pageSize=${pageSize}&direction=${direction}&property=${property}`);

};

export default{
    criar,
    alterar,
    apagar,
    listar
}