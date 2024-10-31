package br.com.itilh.bdpedidos.sistemapedidos.service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.itilh.bdpedidos.sistemapedidos.dto.EstadoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.EstadoDuplicadoException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.NomeEstadoInvalidoException;
import br.com.itilh.bdpedidos.sistemapedidos.model.Estado;
import br.com.itilh.bdpedidos.sistemapedidos.repository.EstadoRepository;

@Service
public class EstadoService extends GenericService<Estado,EstadoDTO>{

    @Autowired
    EstadoRepository estadoRepository;

// LISTAGENS (GET) //

    // Listar todos os estados
    public Page<EstadoDTO> getTodos(Pageable pageable ){
        return toPageDTO(estadoRepository.findAll(pageable));
    }

    // Listar estado pelo ID
    public EstadoDTO getPorId(BigInteger id) throws Exception {
        return toDTO(estadoRepository.findById(id).orElseThrow(() -> new Exception("ID inválido.")));
    } 

// POST - PUT - DELETE //

    // Criar registro de estado
    public EstadoDTO criarEstado(EstadoDTO entityDTO) throws Exception {  
        validar(entityDTO);  
        try{    
            return toDTO(estadoRepository.save(toEntity(entityDTO)));
        }catch(Exception e){
            throw new Exception("Erro ao salvar o estado.");
        }
    }

    // Atualizar registro de estado
    public EstadoDTO alterarEstado(BigInteger id, EstadoDTO novosDados) throws Exception {
        validar(novosDados);
        if(estadoRepository.existsByNome(novosDados.getNome())) throw new EstadoDuplicadoException(novosDados.getNome());
        try{     
            return toDTO(estadoRepository.save(toEntity(novosDados)));
        }catch(Exception e){
            throw new Exception("Alteração não foi realizada.");
        }                                   
    }
    // Excluir registro de estado
    public String deletePorId(BigInteger id) throws Exception {
        estadoRepository.deleteById(id);
        return "Excluído";
    }  

// VALIDAÇÕES //

    // Validação de duplicidade e nome para estado
    private void validar (EstadoDTO dto) throws Exception {
        if(dto.getNome().length() < 3 || dto.getNome().length() > 50)
            throw new NomeEstadoInvalidoException(dto.getNome());
        
        if(estadoRepository.existsByNome(dto.getNome()))   
            throw new EstadoDuplicadoException(dto.getNome());
    }
    
}
