package br.com.itilh.bdpedidos.sistemapedidos.service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ClienteDTO;
//import br.com.itilh.bdpedidos.sistemapedidos.exception.ClienteCnpjDuplicadoException;
//import br.com.itilh.bdpedidos.sistemapedidos.exception.ClienteCpfDuplicadoException;
//import br.com.itilh.bdpedidos.sistemapedidos.exception.ClienteDuplicadoException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.model.Cliente;
import br.com.itilh.bdpedidos.sistemapedidos.model.Municipio;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ClienteRepository;
import br.com.itilh.bdpedidos.sistemapedidos.repository.MunicipioRepository;

@Service
public class ClienteService extends GenericService<Cliente, ClienteDTO> {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private MunicipioRepository municipioRepository;

    // Listar todos os clientes com paginação
    public Page<ClienteDTO> listarClientes(Pageable pageable) {
        return toPageDTO(clienteRepository.findAll(pageable));
    }

    public Page<ClienteDTO> buscar(Pageable pageable, String txtBusca) {
        return toPageDTO(clienteRepository.findByNomeRazaoSocialContainingIgnoreCase(pageable, txtBusca));
     }

    // Buscar cliente por ID
    public ClienteDTO buscarClientePorId(BigInteger id) throws Exception {
        return toDTO(clienteRepository.findById(id)
                .orElseThrow(() -> new IdInexistenteException("Cliente", id)));
    }

    // Criar um novo cliente
    public ClienteDTO criarCliente(ClienteDTO origem) throws Exception {
       // validar(origem);
        Cliente cliente = toEntity(origem);
        cliente.setMunicipio(buscarMunicipio(origem.getMunicipioId()));  // Associa o município
        return toDTO(clienteRepository.save(cliente));
    }

    // Atualizar cliente existente
    public ClienteDTO alterarCliente(BigInteger id, ClienteDTO origem) throws Exception {
       // validar(origem);
        Cliente cliente = toEntity(origem);
        cliente.setMunicipio(buscarMunicipio(origem.getMunicipioId()));  // Associa o município
        return toDTO(clienteRepository.save(cliente));
    }

    // Excluir cliente por ID
    public String excluirCliente(BigInteger id) throws Exception {
        try {
            clienteRepository.deleteById(id);
            return "Cliente excluído com sucesso";
        } catch (Exception ex) {
            throw new Exception("Não foi possível excluir o cliente com o id informado. " + ex.getMessage());
        }
    }

    //Validação de CPF e CNPJ
    // private void validar(ClienteDTO dto) throws Exception {
    
    // if (dto.getCpf() != null && !dto.getCpf().isEmpty()) {
    //     if (clienteRepository.existsByCpf(dto.getCpf())) {
    //         throw new ClienteCpfDuplicadoException(dto.getCpf());
    //     }
    // }

    
    // if (dto.getCnpj() != null && !dto.getCnpj().isEmpty()) {
    //     if (clienteRepository.existsByCnpj(dto.getCnpj())) {
    //         throw new ClienteCnpjDuplicadoException(dto.getCnpj());
    //     }
    // }
    // }

    // Conversão de Cliente para ClienteDTO, incluindo município
    @Override
    protected ClienteDTO toDTO(Cliente cliente) {
        ClienteDTO dto = super.toDTO(cliente);
        if (cliente.getMunicipio() != null) {
            dto.setMunicipioId(cliente.getMunicipio().getId());
            dto.setMunicipioNome(cliente.getMunicipio().getNome());
        }
        return dto;
    }

    // Conversão de ClienteDTO para Cliente
    @Override
    protected Cliente toEntity(ClienteDTO dto) {
        Cliente cliente = super.toEntity(dto);
        if (dto.getMunicipioId() != null) {
            cliente.setMunicipio(buscarMunicipio(dto.getMunicipioId()));
        }
        return cliente;
    }

    // Método auxiliar para buscar o município por ID
    private Municipio buscarMunicipio(BigInteger municipioId) throws IdInexistenteException {
        return municipioRepository.findById(municipioId)
                .orElseThrow(() -> new IdInexistenteException("Município", municipioId));
    }
}