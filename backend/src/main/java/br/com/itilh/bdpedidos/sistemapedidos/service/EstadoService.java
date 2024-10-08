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
    EstadoRepository repositorio;

    public Page<EstadoDTO> getTodos(Pageable pageable ){
        return toPageDTO(repositorio.findAll(pageable));
    }

    public EstadoDTO getPorId(BigInteger id) throws Exception {
        return toDTO(repositorio.findById(id).orElseThrow(
            () -> new Exception("ID inválido.")));
    }  

    private void validar (EstadoDTO dto) throws Exception {
        dto.validarDados();
        if(dto.getNome().length() < 3 || dto.getNome().length() > 50)
            throw new NomeEstadoInvalidoException(dto.getNome());
        
        if(repositorio.existsByNome(dto.getNome()))   
            throw new EstadoDuplicadoException(dto.getNome());

    }

    public EstadoDTO criarEstado(EstadoDTO entityDTO) throws Exception {  
        
        validar(entityDTO);  
        try{    
            return toDTO(repositorio.save(toEntity(entityDTO)));
        }catch(Exception e){
            throw new Exception("Erro ao salvar o estado.");
        }
    }

    public EstadoDTO alterarEstado(BigInteger id, EstadoDTO novosDados) throws Exception {

        validar(novosDados);
        if(repositorio.existsByNome(novosDados.getNome()))   
            throw new EstadoDuplicadoException(novosDados.getNome());
            
        try{     
         return toDTO(repositorio.save(toEntity(novosDados)));
        }catch(Exception e){
            throw new Exception("Alteração não foi realizada.");
        }                                   
    }

    public String deletePorId(BigInteger id) throws Exception {
        repositorio.deleteById(id);
        return "Excluído";
    }  
    
}
