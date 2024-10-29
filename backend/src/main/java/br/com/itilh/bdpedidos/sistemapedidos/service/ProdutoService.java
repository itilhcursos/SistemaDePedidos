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
public class ProdutoService extends GenericService<Produto, ProdutoDTO> {

    @Autowired
    private ProdutoRepository repositorio;

    public Page<ProdutoDTO> listarProdutos(Pageable pageable) {
        return toPageDTO(repositorio.findAll(pageable));
    }

    public Page<ProdutoDTO> buscar(Pageable pageable, String txtBusca) {
        return toPageDTO(repositorio.findByDescricaoContainingIgnoreCase(pageable, txtBusca));
    }

    public ProdutoDTO buscarProdutoPorId(BigInteger id) throws Exception {
        return toDTO(repositorio.findById(id).orElseThrow(() -> new IdInexistenteException("Produto", id)));
    }

    public ProdutoDTO criarProduto(ProdutoDTO dto) throws Exception {
        validar(dto);
        return toDTO(repositorio.save(toEntity(dto)));
    }

    public ProdutoDTO alterarProduto(BigInteger id, ProdutoDTO dto) throws Exception {
        validar(dto);
        return toDTO(repositorio.save(toEntity(dto)));
    }

    private void validar(ProdutoDTO dto) throws Exception {

        if (repositorio.existsByDescricao(dto.getDescricao())){
            if(dto.getId() == null){
                throw new ProdutoDuplicadoException(dto.getDescricao());
            } else {
                Produto p = repositorio.getReferenceById(dto.getId());
                if(!p.getDescricao().equalsIgnoreCase(dto.getDescricao())){
                    throw new ProdutoDuplicadoException(dto.getDescricao());
                }
            }            
        }

        if (dto.getPrecoUnidadeAtual() == null || dto.getPrecoUnidadeAtual().floatValue() < 0.0)
            throw new ProdutoPrecoNegativoException(dto.getDescricao());

        if (dto.getQuantidadeEstoque() == null || dto.getQuantidadeEstoque().floatValue() < 0.0)
            throw new ProdutoEstoqueNegativoException(dto.getDescricao());
    }

    public String excluirProduto(BigInteger id) throws Exception {
        try {
            repositorio.deleteById(id);
            return "O registro foi excluído";
        } catch (Exception ex) {
            throw new Exception("Não foi possível excluir o registro informado." + ex.getMessage());
        }
    }

}
