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

// LISTAGENS (GET) //

    // Listar todos os produtos
    public Page<ProdutoDTO> listarProdutos(Pageable pageable) {
        return toPageDTO(repositorio.findAll(pageable));
    }

    // Listar produto pelo texto
    public Page<ProdutoDTO> buscar(Pageable pageable, String txtBusca) {
        return toPageDTO(repositorio.findByDescricaoContainingIgnoreCase(pageable, txtBusca));
    }

    // Listar produtos pelo ID  
    public ProdutoDTO buscarProdutoPorId(BigInteger id) throws Exception {
        return toDTO(repositorio.findById(id).orElseThrow(() -> new IdInexistenteException("Produto", id)));
    }

// POST - PUT - DELETE //

    // Criar registro de produto
    public ProdutoDTO criarProduto(ProdutoDTO origem) throws Exception {
        validar(origem);
        return toDTO(repositorio.save(toEntity(origem)));
    }

    // Atualizar registro de produto
    public ProdutoDTO alterarProduto(BigInteger id, ProdutoDTO origem) throws Exception {
        validar(origem);
        return toDTO(repositorio.save(toEntity(origem)));
    }

    // Excluir registro de produto
    public String excluirProduto(BigInteger id) throws Exception{
        try{ 
            repositorio.deleteById(id);
             return "Excluído";
        }catch (Exception ex){
            throw new Exception("Não foi possível excluir o id informado." + ex.getMessage());
        }
    }

// VALIDAÇÕES //

    // Valida a duplicidade de produto
    private void validar(ProdutoDTO dto) throws Exception {
        verificarDuplicidadeProduto(dto);
        verificarQuantidadeEstoque(dto);
        verificarPrecoUnidadeAtual(dto);
    }

    // Valida a duplicidade do produto pela descrição
    private void verificarDuplicidadeProduto(ProdutoDTO dto) throws ProdutoDuplicadoException {
        if (repositorio.existsByDescricao(dto.getDescricao())) {
            if (dto.getId() == null) { 
                throw new ProdutoDuplicadoException(dto.getDescricao());
            } else {
                Produto p = repositorio.getReferenceById(dto.getId());
                if (!p.getDescricao().equalsIgnoreCase(dto.getDescricao())) {
                    throw new ProdutoDuplicadoException(dto.getDescricao());
                }
            }
        }
    }

    // Valida se a quantidade em estoque é válida
    private void verificarQuantidadeEstoque(ProdutoDTO dto) throws ProdutoEstoqueNegativoException {
        if (dto.getQuantidadeEstoque() == null || dto.getQuantidadeEstoque().floatValue() < 0.0) {
            throw new ProdutoEstoqueNegativoException(dto.getDescricao());
        }
    }

    // Valida se o preço por unidade é válido
    private void verificarPrecoUnidadeAtual(ProdutoDTO dto) throws ProdutoPrecoNegativoException {
        if (dto.getPrecoUnidadeAtual() == null || dto.getPrecoUnidadeAtual().floatValue() < 0.0) {
            throw new ProdutoPrecoNegativoException(dto.getDescricao());
        }
    }
}
