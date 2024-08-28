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
import br.com.itilh.bdpedidos.sistemapedidos.dto.ProdutoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ProdutoDuplicadoException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ProdutoEstoqueNegativoException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ProdutoValorNegativoException;
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

    private void validarEstoqueNegativo(ProdutoDTO prodOrigem) {       
        if(prodOrigem.getQuantidadeEstoque() < 0){
          throw new ProdutoEstoqueNegativoException(prodOrigem.getDescricao());
        }
    }

    private void validarVlrNegativo(ProdutoDTO prodOrigem) {       
        if(prodOrigem.getPrecoUnidadeAtual().floatValue() < 0){
          throw new ProdutoValorNegativoException(prodOrigem.getDescricao());
        }
    }  
    
    private void validarDuplicado(ProdutoDTO prodOrigem) {
        if(repositorio.existsByDescricao(prodOrigem.getDescricao()))
          throw new ProdutoDuplicadoException(prodOrigem.getDescricao());
    }    

    public ProdutoDTO criarProduto(ProdutoDTO prodOrigem) throws Exception {  
        validarDuplicado(prodOrigem);
        validarEstoqueNegativo(prodOrigem);  
        validarVlrNegativo(prodOrigem);
        return toDTO(repositorio.save(toEntity(prodOrigem)));
    }

    public ProdutoDTO alterarProduto(BigInteger id, ProdutoDTO prodOrigem) throws Exception {
        validarDuplicado(prodOrigem);
        validarEstoqueNegativo(prodOrigem);  
        validarVlrNegativo(prodOrigem);
        return toDTO(repositorio.save(toEntity(prodOrigem)));
    }

    public String excluirProduto(BigInteger id) throws Exception{
        try{ 
            repositorio.deleteById(id);
             return "Excluído";
        }catch (Exception ex){
            throw new Exception("Não foi possível excluir o id informado." + ex.getMessage());
        }
    }

    private ProdutoDTO toDTO(Produto prodOrigem){
        ProdutoDTO dto = mapper.map(prodOrigem, ProdutoDTO.class);
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
