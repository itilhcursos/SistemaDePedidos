package br.com.itilh.bdpedidos.sistemapedidos.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ProdutoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ProdutoQuantidadeEstoqueException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ProdutoPrecoUnidadeAtualException;
import br.com.itilh.bdpedidos.sistemapedidos.model.Produto;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repositorio;

    @Autowired
    private ModelMapper mapper;

    public Page<ProdutoDTO> listarProdutos(Pageable pageable) {
        return toPageDTO(repositorio.findAll(pageable));
    }

    public ProdutoDTO buscarProdutoPorId(BigInteger id) throws Exception {
        return toDTO(repositorio.findById(id)
        .orElseThrow(()-> new IdInexistenteException("Produto", id)));
    }

    public ProdutoDTO criarProduto(ProdutoDTO origem) throws Exception {  
        validarPreco(origem);
        return toDTO(repositorio.save(toEntity(origem)));
    }

    public ProdutoDTO alterarProduto(BigInteger id, ProdutoDTO origem) throws Exception {
        validarEstoque(origem);
        validarPreco(origem);
        return toDTO(repositorio.save(toEntity(origem)));
    }

    public String excluirProduto(BigInteger id) throws Exception{
        try{ 
            repositorio.deleteById(id);
             return "Excluído";
        }catch (Exception ex){
            throw new Exception("Não foi possível excluir o id informado." + ex.getMessage());
        }
    }

    // erro de código
    private void validarEstoque(ProdutoDTO origem) {
        if (origem.getQuantidadeEstoque()!=null && origem.getQuantidadeEstoque() < 0) {
            throw new ProdutoQuantidadeEstoqueException(origem.getQuantidadeEstoque());
        }
    }


    //erro de código
    private void validarPreco(ProdutoDTO origem) {
        if (origem.getPrecoUnidadeAtual() != null && origem.getPrecoUnidadeAtual().compareTo(BigDecimal.ZERO) < 0) {
            throw new ProdutoPrecoUnidadeAtualException(origem.getPrecoUnidadeAtual());
        }
    }

    private ProdutoDTO toDTO(Produto produto){
        ProdutoDTO dto = mapper.map(produto, ProdutoDTO.class);
        return dto;
    }

    private Produto toEntity(ProdutoDTO dto){
        Produto entity = mapper.map(dto, Produto.class);
        return entity;
    }

    private Page<ProdutoDTO> toPageDTO(Page<Produto> entities){
        List<ProdutoDTO> dtos = entities.stream().map(this::toDTO).collect(Collectors.toList());
        return new PageImpl<>(dtos,entities.getPageable(), entities.getTotalElements());
    }
}
