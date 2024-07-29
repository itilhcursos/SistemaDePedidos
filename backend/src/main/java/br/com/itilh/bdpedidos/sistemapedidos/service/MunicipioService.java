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

import br.com.itilh.bdpedidos.sistemapedidos.dto.MunicipioDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.model.Municipio;
import br.com.itilh.bdpedidos.sistemapedidos.repository.MunicipioRepository;

@Service
public class MunicipioService {

    @Autowired
    private  MunicipioRepository repositorio;

    @Autowired
    private ModelMapper modelMapper;


    public Page<MunicipioDTO> getMunicipios(Pageable pageable) {
        return toPageDTO(repositorio.findAll(pageable));
    }

    public Page<MunicipioDTO> getMunicipiosPorEstadoId(BigInteger id, Pageable pageable) {
        return toPageDTO(repositorio.findByEstadoId(id, pageable));
    }

    public Page<MunicipioDTO> getMunicipiosPorEstadoNome(String nome, Pageable pageable) {
        return toPageDTO (repositorio.findByEstadoNomeIgnoreCase(nome, pageable));
    }

    public MunicipioDTO getMunicipioPorId(BigInteger id) throws Exception {
        return toDTO(repositorio.findById(id)
                .orElseThrow(() -> new IdInexistenteException("Município", id)));
    }

    public MunicipioDTO salvarMunicipio(MunicipioDTO dto) throws Exception {
        try {
            return toDTO(repositorio.save(toEntity(dto)));
        } catch (Exception ex) {
            throw new Exception("Não foi possível criar o município." + ex.getMessage());
        }
    }

    public MunicipioDTO alterarMunicipio(BigInteger id, MunicipioDTO dto) throws Exception {
        try {
            return toDTO(repositorio.save(toEntity(dto)));
        } catch (Exception ex) {
            throw new Exception("Não foi possível alterar o município." + ex.getMessage());
        }
    }

    public String apagarMunicipio(BigInteger id) throws Exception {
        try {
            repositorio.deleteById(id);
            return "Excluído";
        } catch (Exception ex) {
            throw new Exception("Não foi possível excluir o id informado." + ex.getMessage());
        }
    }

    private Page<MunicipioDTO> toPageDTO(Page<Municipio> municipios){
        List<MunicipioDTO> dtos = municipios.stream().map(this::toDTO).collect(Collectors.toList());
        return new PageImpl<>(dtos, municipios.getPageable(), municipios.getTotalElements());
    } 

    private MunicipioDTO toDTO (Municipio entity) {
        MunicipioDTO dto = modelMapper.map(entity,MunicipioDTO.class);
        return dto;
    }

    private Municipio toEntity(MunicipioDTO dto) {
        Municipio entity = modelMapper.map(dto, Municipio.class);
        return entity;
    }


}
