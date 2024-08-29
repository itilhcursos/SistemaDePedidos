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

import br.com.itilh.bdpedidos.sistemapedidos.dto.FormaPagamentoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.FormaPagamentoDuplicadoException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.model.FormaPagamento;
import br.com.itilh.bdpedidos.sistemapedidos.repository.FormaPagamentoRepository;

@Service
public class FormaPagamentoService {

    @Autowired
    private FormaPagamentoRepository repositorio;

    @Autowired
    private ModelMapper mapper;

    public Page<FormaPagamentoDTO> listarFormasPagamento(Pageable pageable) {
        return toPageDTO(repositorio.findAll(pageable));
    }

    public FormaPagamentoDTO buscarFormaPagamentoPorId(BigInteger id) throws Exception {
        return toDTO(repositorio.findById(id)
                .orElseThrow(() -> new IdInexistenteException("Forma de Pagamento", id)));
    }


    public FormaPagamentoDTO criarFormaPagamento(FormaPagamentoDTO origem) throws Exception {
       if (repositorio.existsByDescricao(origem.getDescricao()))
           throw new FormaPagamentoDuplicadoException(origem.getDescricao());

        try {
            return toDTO(repositorio.save(toEntity(origem)));
        } catch (Exception e) {
            throw new Exception("Erro ao salvar a Forma de Pagamento.", e);
        }
    }

    
    public FormaPagamentoDTO alterarFormaPagamento(BigInteger id, FormaPagamentoDTO origem) throws Exception {
        if (repositorio.existsByDescricao(origem.getDescricao()))
        throw new FormaPagamentoDuplicadoException(origem.getDescricao());
        try {
            return toDTO(repositorio.save(toEntity(origem)));
        } catch (Exception e) {
            throw new Exception("Alteração não foi realizada.");
        }
    }

    public String excluirFormaPagamento(BigInteger id) throws Exception {
        try {
            repositorio.deleteById(id);
            return "Excluído";
        } catch (Exception ex) {
            throw new Exception("Não foi possível excluir o id informado." + ex.getMessage());
        }
    }

    private FormaPagamentoDTO toDTO(FormaPagamento formaPagamento) {
        FormaPagamentoDTO dto = mapper.map(formaPagamento, FormaPagamentoDTO.class);
        return dto;
    }

    private FormaPagamento toEntity(FormaPagamentoDTO dto) {
        FormaPagamento entity = mapper.map(dto, FormaPagamento.class);
        return entity;
    }

    private Page<FormaPagamentoDTO> toPageDTO(Page<FormaPagamento> entities) {
        List<FormaPagamentoDTO> dtos = entities.stream().map(this::toDTO).collect(Collectors.toList());
        return new PageImpl<>(dtos, entities.getPageable(), entities.getTotalElements());
    }

}
