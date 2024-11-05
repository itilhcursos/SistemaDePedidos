import axios from 'axios';

const urlBase = 'http://localhost:8080'; // Ajuste para a URL do seu servidor

// Método para criar um recurso
const criar = async (path, objeto) => {
    return await axios.post(`${urlBase}${path}`, objeto);
};

// Método para atualizar um recurso
const atualizar = async (path, id, objeto) => {
    return await axios.put(`${urlBase}${path}/${id}`, objeto);
};

// Método para apagar um recurso
const apagar = async (path, id) => {
    return await axios.delete(`${urlBase}${path}/${id}`);
};

// Método para listar recursos com paginação e ordenação
const listar = async (path, pageNumber = 1, pageSize = 10, direction = 'ASC', property = 'id') => {
    return await axios.get(`${urlBase}${path}`, {
        params: {
            pageNumber,
            pageSize,
            direction,
            property
        }
    });
};

// Exportando todos os métodos para uso
export default {
    criar,
    atualizar,
    apagar,
    listar
};
