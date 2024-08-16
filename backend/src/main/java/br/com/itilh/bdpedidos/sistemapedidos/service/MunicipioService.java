package br.com.itilh.bdpedidos.sistemapedidos.service;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
<<<<<<< HEAD

import br.com.itilh.bdpedidos.sistemapedidos.dto.MunicipioDTO;
import br.com.itilh.bdpedidos.sistemapedidos.model.Municipio;
import br.com.itilh.bdpedidos.sistemapedidos.repository.MunicipioRepository;

public class MunicipioService {

=======
import org.springframework.stereotype.Service;

import br.com.itilh.bdpedidos.sistemapedidos.dto.MunicipioDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.model.Municipio;
import br.com.itilh.bdpedidos.sistemapedidos.repository.MunicipioRepository;

@Service
public class MunicipioService {

// todo:  implementar teste desta service

>>>>>>> develop
    @Autowired
    private MunicipioRepository repository;

    @Autowired
    private ModelMapper mapper;

    public Page<MunicipioDTO> listarMunicipios(Pageable pageable) {
        return toPageDTO(repository.findAll(pageable));
    }

    public Page<MunicipioDTO> listarMunicipiosPorEstadoId(BigInteger id, Pageable pageable) {
        return toPageDTO(repository.findByEstadoId(id, pageable));
    }

    public Page<MunicipioDTO> listarMunicipiosPorEstadoNome(String nome, Pageable pageable) {
        return toPageDTO(repository.findByEstadoNomeIgnoreCase(nome, pageable));
    }

    public MunicipioDTO buscarMunicipioPorId(BigInteger id) throws Exception {
        return toDTO(repository.findById(id)
<<<<<<< HEAD
        .orElseThrow(()-> new Exception("Id não encontrado.")));
=======
        .orElseThrow(()-> new IdInexistenteException("Município", id)));
>>>>>>> develop
    }

    public MunicipioDTO criarMunicipio(MunicipioDTO origem) throws Exception {    
        try{ 
            return toDTO(repository.save(toEntity(origem)));
        }catch (Exception ex){
            throw new Exception("Não foi possível criar o município." + ex.getMessage());
        }
    }

    public MunicipioDTO alterarMunicipio(BigInteger id, MunicipioDTO origem) throws Exception {
        try{ 
            return toDTO(repository.save(toEntity(origem)));
        }catch (Exception ex){
            throw new Exception("Não foi possível alterar o município." + ex.getMessage());
        }
    }

    public String excluirMunicipio(BigInteger id) throws Exception{
        try{ 
            repository.deleteById(id);
             return "Excluído";
        }catch (Exception ex){
            throw new Exception("Não foi possível excluir o id informado." + ex.getMessage());
        }
    }

<<<<<<< HEAD
=======

>>>>>>> develop
    // Receber um Objeto Municipio e criar um MunicipioDTO
    private MunicipioDTO toDTO(Municipio municipio){

        MunicipioDTO dto = mapper.map(municipio, MunicipioDTO.class);

        // MunicipioDTO dto = new MunicipioDTO();
        // dto.setId(municipio.getId());
        // dto.setNome(municipio.getNome());
        // dto.setEntrega(municipio.getEntrega());
        // dto.setEstadoId(municipio.getEstado().getId());
        // dto.setEstadoNome(municipio.getEstado().getNome().toString());


        return dto;
    }
<<<<<<< HEAD

=======
    
>>>>>>> develop
    // Receber um Objeto MuncipioDTO to Municipio
    private Municipio toEntity(MunicipioDTO dto){
        Municipio entity = mapper.map(dto, Municipio.class);
        return entity;
    }

    private Page<MunicipioDTO> toPageDTO(Page<Municipio> municipios){
        // Dever de Casa Estudar o ".stream" em java!!!!!!
        List<MunicipioDTO> dtos = municipios.stream().map(this::toDTO).collect(Collectors.toList());
        return new PageImpl<>(dtos,municipios.getPageable(), municipios.getTotalElements());
    }
<<<<<<< HEAD

}
=======
}
>>>>>>> develop
