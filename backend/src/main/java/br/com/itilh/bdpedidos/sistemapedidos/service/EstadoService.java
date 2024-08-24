package br.com.itilh.bdpedidos.sistemapedidos.service;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.itilh.bdpedidos.sistemapedidos.dto.EstadoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.EstadoDuplicadoException;
import br.com.itilh.bdpedidos.sistemapedidos.model.Estado;
import br.com.itilh.bdpedidos.sistemapedidos.repository.EstadoRepository;

@Service
public class EstadoService {

    @Autowired
    EstadoRepository repositorio;

    @Autowired
    ModelMapper mapper;

    public Page<EstadoDTO> getTodos(Pageable pageable ){
        return toPageDTO(repositorio.findAll(pageable));
    }

    public EstadoDTO getPorId(BigInteger id) throws Exception {
        return toDTO(repositorio.findById(id).orElseThrow(
            () -> new Exception("ID inválido.")));
    }  

    public EstadoDTO criarEstado(EstadoDTO entityDTO) throws Exception {           
        if(repositorio.existsByNome(entityDTO.getNome()))   
            throw new EstadoDuplicadoException(entityDTO.getNome());

        try{    
            return toDTO(repositorio.save(toEntity(entityDTO)));
        }catch(Exception e){
            throw new Exception("Erro ao salvar o estado.");
        }
    }

    public EstadoDTO alterarEstado(BigInteger id, 
                    EstadoDTO novosDados) throws Exception {

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


    private EstadoDTO toDTO(Estado estado){
        EstadoDTO dto = mapper.map(estado, EstadoDTO.class);
        return dto;
    }
    
    private Estado toEntity(EstadoDTO dto){
        Estado entity = mapper.map(dto, Estado.class);
        return entity;
    }

    private Page<EstadoDTO> toPageDTO(Page<Estado> estados){
        List<EstadoDTO> dtos = estados.stream().map(this::toDTO).collect(Collectors.toList());
        return new PageImpl<>(dtos,estados.getPageable(), estados.getTotalElements());
    }
}