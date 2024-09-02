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
import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.model.Estado;
import br.com.itilh.bdpedidos.sistemapedidos.repository.EstadoRepository;

@Service
public class EstadoService {

    // quando colocamos o @AutoWirede esta automaticamento criando um construtor padrão onde o estado repository irá ser passado como parametro.
    // originalmente deveria ter no controler uma definição de variavel e o construtor padrão do estado controler passando o estado repository,
    //porem com a criação do service e incluindo o AutoWirede ele faz isso automaticamente.
    @Autowired 
    EstadoRepository repositorio;

    @Autowired
    ModelMapper mapper;

    public Page<EstadoDTO> getTodos(Pageable pageable){
        return toPageDTO( repositorio.findAll(pageable));
    }

    public EstadoDTO getPorId( BigInteger id) throws Exception {
        return toDTO(repositorio.findById(id)
        .orElseThrow(() -> new IdInexistenteException("Estado", id))
        );
    } 

    public EstadoDTO criarEstado( EstadoDTO entityDTO) throws Exception { 
        try{               
            return toDTO(repositorio.save(toEntity(entityDTO)));
        }catch(Exception e){
            throw new Exception("Erro ao salvar o estado.");
        }
    }

    public EstadoDTO alterarEstado( BigInteger id, 
                                    EstadoDTO novosDados) throws Exception {
        try{               
            return toDTO(repositorio.save(toEntity(novosDados)));
        }catch(Exception e){
            throw new Exception("Alteração não foi realizada.");
        }
        
    }


    public String deletePorId( BigInteger id) throws Exception {
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
        return new PageImpl<>(dtos, estados.getPageable(), estados.getTotalElements());
    }

    
}
     

