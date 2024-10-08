package br.com.itilh.bdpedidos.sistemapedidos.service;

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

    public Page<ProdutoDTO> buscar(Pageable pageable, String txtBusca) {
        return toPageDTO(repositorio.findByDescricaoContainingIgnoreCase(pageable, txtBusca));
    }

    public ProdutoDTO buscarProdutoPorId(BigInteger id) throws Exception {
        return toDTO(repositorio.findById(id)
        .orElseThrow(()-> new IdInexistenteException("Produto", id)));
    }

    private void validar(ProdutoDTO origem ) throws Exception{

        if(repositorio.existsByDescricao(origem.getDescricao())){ // se existe o Produto 
            if(origem.getId() == null){    // e estou pedindo para criar com o mesmo nome
              throw new ProdutoDuplicadoException(origem.getDescricao()); //irá ocorrer um Produto Duplicado.
            
            }else{
                //produto já existe
                Produto p = repositorio.getReferenceById(origem.getId()); // estou alterando um produto e estou perguntando se
                if(!p.getDescricao().equalsIgnoreCase(origem.getDescricao())){ // a descrição ou o nome desse produto que ja está cadastrado no repositorio, é diferente da descrição do dto (fornecida para alterar)
                     throw new ProdutoDuplicadoException(origem.getDescricao()); //se for igual então dara um ProdutoDuplicadoException Porem se for diferente a alteração acontecera normalmente.
                }
            }
        }
        if(origem.getPrecoUnidadeAtual() == null || origem.getPrecoUnidadeAtual().floatValue() < 0.0)
            throw new ProdutoPrecoNegativoException(origem.getDescricao());
        
        if(origem.getQuantidadeEstoque() == null || origem.getQuantidadeEstoque().floatValue() < 0.0)
            throw new ProdutoEstoqueNegativoException(origem.getDescricao());
        
    } 

    public ProdutoDTO criarProduto(ProdutoDTO origem) throws Exception {    
        validar(origem);
        return toDTO(repositorio.save(toEntity(origem)));
    }

    public ProdutoDTO alterarProduto(BigInteger id, ProdutoDTO origem) throws Exception {
        validar(origem);
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
