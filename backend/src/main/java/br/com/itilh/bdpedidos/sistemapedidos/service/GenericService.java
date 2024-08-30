package br.com.itilh.bdpedidos.sistemapedidos.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class GenericService<T, D> {

    // T - "table" ou seja a Entidade
    // D - "Dados - Entrada e saída" ou seja DTO
    @Autowired
    private ModelMapper mapper;

    private Type t, d;

    public GenericService() {
        super();
        t = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        d = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];

    }

    protected D toDTO(T entity) {

        D dto = mapper.map(entity, d);
        return dto;
    }

    // Receber um Objeto MuncipioDTO to Municipio
    protected T toEntity(D dto) {
        T entity = mapper.map(dto, t);
        return entity;
    }

    protected Page<D> toPageDTO(Page<T> entities) {
        // Dever de Casa Estudar o ".stream" em java!!!!!!
        List<D> dtos = entities.stream().map(this::toDTO).collect(Collectors.toList());
        return new PageImpl<>(dtos, entities.getPageable(), entities.getTotalElements());
    }

}
