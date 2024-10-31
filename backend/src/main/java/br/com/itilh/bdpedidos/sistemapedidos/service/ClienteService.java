package br.com.itilh.bdpedidos.sistemapedidos.service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ClienteDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ClienteCnpjDuplicadoException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ClienteCpfDuplicadoException;
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

// LISTAGENS (GET) //

    // Listar todos os clientes com paginação
    public Page<ClienteDTO> listarClientes(Pageable pageable) {
        return toPageDTO(clienteRepository.findAll(pageable));
    }

    // Listar clientes filtrando pelo ID do município
    public Page<ClienteDTO> listarClientesPorMunicipioId(BigInteger id, Pageable pageable){
        return toPageDTO(clienteRepository.findByMunicipioId(id, pageable));
    }

    // Listar clientes filtrando pelo nome do município
    public Page<ClienteDTO> listarClientesPorMunicipioNome(String nome, Pageable pageable){
        return toPageDTO(clienteRepository.findByMunicipioNomeIgnoreCase(nome, pageable));
    }

    // Listar clientes filtrando por texto
    public Page<ClienteDTO> buscar(Pageable pageable, String txtBusca) {
        return toPageDTO(clienteRepository.findByNomeRazaoSocialContainingIgnoreCase(pageable, txtBusca));
    }

    // Listar cliente filtrando por ID
    public ClienteDTO buscarClientePorId(BigInteger id) throws Exception {
        return toDTO(clienteRepository.findById(id).orElseThrow(() -> new IdInexistenteException("Cliente", id)));
    }

// POST - PUT - DELETE //

    // Criar registro de cliente (POST)
    public ClienteDTO criarCliente(ClienteDTO origem) throws Exception {
        validar(origem);
        Cliente cliente = toEntity(origem);
        cliente.setMunicipio(buscarMunicipio(origem.getMunicipioId()));
        return toDTO(clienteRepository.save(cliente));
    }

    // Atualizar registro de cliente (PUT)
    public ClienteDTO alterarCliente(BigInteger id, ClienteDTO origem) throws Exception {
        validar(origem);
        Cliente cliente = toEntity(origem);
        cliente.setMunicipio(buscarMunicipio(origem.getMunicipioId()));
        return toDTO(clienteRepository.save(cliente));
    }

    // Excluir registro de cliente por ID (DELETE)
    public String excluirCliente(BigInteger id) throws Exception {
        try {
            clienteRepository.deleteById(id);
            return "Cliente excluído com sucesso";
        } catch (Exception ex) {
            throw new Exception("Não foi possível excluir o cliente com o id informado. " + ex.getMessage());
        }
    }

// VALIDAÇÕES //

    // Validação de duplicidade de CPF e/ou CNPJ
    private void validar(ClienteDTO dto) throws Exception {
        Cliente clienteExistente = buscarClienteExistente(dto);
        verificarDuplicidadeCpf(dto, clienteExistente);
        verificarDuplicidadeCnpj(dto, clienteExistente);
        verificarCpfECnpjPreenchidos(dto);
    }

    // Lista o cliente duplicado pelo ID
    private Cliente buscarClienteExistente(ClienteDTO dto) {
        if (dto.getId() != null) {
            return clienteRepository.findById(dto.getId()).orElse(null);
        }
        return null;
    }

    // Validação de duplicidade de CPF
    private void verificarDuplicidadeCpf(ClienteDTO dto, Cliente clienteExistente) throws ClienteCpfDuplicadoException {
        if (dto.getCpf() != null && !dto.getCpf().isEmpty() &&
                (clienteExistente == null || !dto.getCpf().equals(clienteExistente.getCpf()))) {
            if (clienteRepository.existsByCpf(dto.getCpf())) {
                throw new ClienteCpfDuplicadoException(dto.getCpf());
            }
        }
    }

    // Validação de duplicidade de CNPJ
    private void verificarDuplicidadeCnpj(ClienteDTO dto, Cliente clienteExistente) throws ClienteCnpjDuplicadoException {
        if (dto.getCnpj() != null && !dto.getCnpj().isEmpty() &&
                (clienteExistente == null || !dto.getCnpj().equals(clienteExistente.getCnpj()))) {
            if (clienteRepository.existsByCnpj(dto.getCnpj())) {
                throw new ClienteCnpjDuplicadoException(dto.getCnpj());
            }
        }
    }

    // Validação de preenchimento de CPF e CNPJ
    private void verificarCpfECnpjPreenchidos(ClienteDTO dto) throws Exception {
        if (dto.getCpf() != null && 
            !dto.getCpf().isEmpty() &&  
            dto.getCnpj() != null && 
            !dto.getCnpj().isEmpty()) {
            throw new Exception("Não é permitido ter CPF e CNPJ preenchidos simultaneamente.");
        }
    }

// CONVERSÕES //

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

    // Buscar o município por ID
    private Municipio buscarMunicipio(BigInteger municipioId) throws IdInexistenteException {
        return municipioRepository.findById(municipioId).orElseThrow(() -> new IdInexistenteException("Município", municipioId));
    }
}

