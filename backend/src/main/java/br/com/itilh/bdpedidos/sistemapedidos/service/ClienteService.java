package br.com.itilh.bdpedidos.sistemapedidos.service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ClienteDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ClienteDuplicadoException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.model.Cliente;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ClienteRepository;

@Service
public class ClienteService extends GenericService<Cliente,ClienteDTO> {

    @Autowired
    ClienteRepository repositorio;

    public Page<ClienteDTO> listarClientes(Pageable pageable){
        return toPageDTO(repositorio.findAll(pageable));
    }

    public Page<ClienteDTO> listarClientesPorMunicipioId(BigInteger id, Pageable pageable){
        return toPageDTO(repositorio.findByMunicipioId(id, pageable));
    }

    public Page<ClienteDTO> listarClientesPorMunicipioNome(String nome, Pageable pageable){
        return toPageDTO(repositorio.findByMunicipioNomeIgnoreCase(nome, pageable));
    }

    public ClienteDTO buscarClientePorId(BigInteger id) throws Exception {
        return toDTO(repositorio.findById(id)
        .orElseThrow(() -> new IdInexistenteException("Cliente", id)));
    }

    public ClienteDTO criarCliente(ClienteDTO dto) throws Exception {
        validar(dto);
        return toDTO(repositorio.save(toEntity(dto)));
    }

    public ClienteDTO alterarCliente(BigInteger id, ClienteDTO dto) throws Exception {
        validar(dto);
        return toDTO(repositorio.save(toEntity(dto)));
    }

    public String excluirCliente(BigInteger id) throws Exception {
        try {
            repositorio.deleteById(id);
            return "O registro do cliente foi excluído!";
        } catch (Exception ex) {
            throw new Exception("Não foi possível excluir o registro informado. " + ex.getMessage());
        }
    }

    private void validar(ClienteDTO dto) {
        if (dto.getCpf() != null && repositorio.existsByCpf(dto.getCpf())) {
            throw new ClienteDuplicadoException("CPF", dto.getCpf());
        }
        if (dto.getCnpj() != null && repositorio.existsByCnpj(dto.getCnpj())) {
            throw new ClienteDuplicadoException("CNPJ", dto.getCnpj());
        }
        if (repositorio.existsByNomeRazaoSocialAndMunicipioId(dto.getNomeRazaoSocial(), dto.getMunicipioId())) {
            throw new ClienteDuplicadoException("Nome/Razão Social", dto.getNomeRazaoSocial());
        }
    }
}