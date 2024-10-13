package br.com.itilh.bdpedidos.sistemapedidos.service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ClienteDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ClienteDuplicadoException;
import br.com.itilh.bdpedidos.sistemapedidos.model.Cliente;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ClienteRepository;
import br.com.itilh.bdpedidos.sistemapedidos.util.Validador;

@Service
public class ClienteService extends GenericService<Cliente,ClienteDTO> {

    @Autowired
    ClienteRepository repositorio;

    public Page<ClienteDTO> getTodos(Pageable pageable){
        return toPageDTO(repositorio.findAll(pageable));
    }

    public Page<ClienteDTO> buscar(Pageable pageable, String txtBusca){
        return toPageDTO(repositorio.findByNomeRazaoSocialContainingIgnoreCase(pageable, txtBusca));
    }

    public ClienteDTO getPorId(BigInteger id) throws Exception {
        return toDTO(repositorio.findById(id)
        
        .orElseThrow(() -> new Exception("ID Inválido!")));
    }

    private void validarDoc(ClienteDTO dto) throws Exception{
        if (!dto.getCpf().equals("")){ // se NÃO for string vazia
            if (Validador.isCpf(dto.getCpf()) == false){
                throw new Exception("CPF Inválido! Use um CPF Válido para cadastrar. (https://www.4devs.com.br/gerador_de_cpf)");
            }
        }

        if (!dto.getCnpj().equals("")){
            if (Validador.isCnpj(dto.getCnpj()) == false){
                throw new Exception("CNPJ Inválido! Use um CNPJ Válido para cadastrar. (https://www.4devs.com.br/gerador_de_cnpj)");
            }
        }
    }

    private void validar(ClienteDTO dto) throws Exception{
        validarDoc(dto);

        if (repositorio.existsByCnpjOrCpf(dto.getCnpj(), dto.getCpf())) {
            if (dto.getId() == null) {     
                if (!dto.getCnpj().isEmpty() && repositorio.existsByCnpj(dto.getCnpj())) {
                    throw new ClienteDuplicadoException("CNPJ", dto.getCnpj());
                    
                } else if (!dto.getCpf().isEmpty() && repositorio.existsByCpf(dto.getCpf())) {

                    throw new ClienteDuplicadoException("CPF", dto.getCpf());
                }
            } else {          
                Cliente c = repositorio.getReferenceById(dto.getId());
        
                if (!dto.getCnpj().isEmpty() && !c.getCnpj().equalsIgnoreCase(dto.getCnpj()) && repositorio.existsByCnpj(dto.getCnpj())) {
                    throw new ClienteDuplicadoException("CNPJ", dto.getCnpj());

                } else if (!dto.getCpf().isEmpty() && !c.getCpf().equalsIgnoreCase(dto.getCpf()) && repositorio.existsByCpf(dto.getCpf())) {
                    throw new ClienteDuplicadoException("CPF", dto.getCpf());
                }
            }
        }
    }

    public ClienteDTO criar(ClienteDTO dto) throws Exception {
        validar(dto);
        return toDTO(repositorio.save(toEntity(dto)));
    }

    public ClienteDTO alterar(BigInteger id, ClienteDTO dto) throws Exception {
        validar(dto);
        return toDTO(repositorio.save(toEntity(dto)));
        
    }

    public String apagar(BigInteger id) throws Exception {
        repositorio.deleteById(id);
        return ("Cliente apagado!");
    }
}
