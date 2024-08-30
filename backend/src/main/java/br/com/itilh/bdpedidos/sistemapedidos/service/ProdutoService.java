package br.com.itilh.bdpedidos.sistemapedidos.service;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ProdutoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ProdutoDuplicadoException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ProdutoEstoqueNegativoException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ProdutoPrecoNegativoException;
import br.com.itilh.bdpedidos.sistemapedidos.model.Produto;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ProdutoRepository;

@Service
public class ProdutoService extends GenericService<Produto,ProdutoDTO> {

    @Autowired
    private ProdutoRepository repositorio;

    public Page<ProdutoDTO> listarProdutos(Pageable pageable) {
        return toPageDTO(repositorio.findAll(pageable));
    }

    public ProdutoDTO buscarProdutoPorId(BigInteger id) throws Exception {
        return toDTO(repositorio.findById(id)
        .orElseThrow(()-> new IdInexistenteException("Produto", id)));
    }

    public ProdutoDTO criarProduto(ProdutoDTO origem) throws Exception {    
        validarDuplicidade(origem);
        validarEstoqueNegativo(origem);
        validarPrecoNegativo(origem);
        return toDTO(repositorio.save(toEntity(origem)));
    }

    private void validarDuplicidade(ProdutoDTO origem) {
      if(repositorio.existsByDescricao(origem.getDescricao()))
        throw new ProdutoDuplicadoException(origem.getDescricao());
    }

    private void validarEstoqueNegativo(ProdutoDTO origem) {
        if (origem.getQuantidadeEstoque() < 0)
          throw new ProdutoEstoqueNegativoException(origem.getQuantidadeEstoque());
    }

    private void validarPrecoNegativo(ProdutoDTO origem) {
     BigDecimal preco = origem.getPrecoUnidadeAtual();
      if (preco != null && preco.compareTo(BigDecimal.ZERO) < 0) 
        throw new ProdutoPrecoNegativoException(preco);
    }

    public ProdutoDTO alterarProduto(BigInteger id, ProdutoDTO origem) throws Exception {
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

}
