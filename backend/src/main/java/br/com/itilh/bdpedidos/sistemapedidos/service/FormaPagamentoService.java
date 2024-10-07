package br.com.itilh.bdpedidos.sistemapedidos.service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.itilh.bdpedidos.sistemapedidos.dto.FormaPagamentoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.FormaPagamentoDuplicadoException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.model.FormaPagamento;
import br.com.itilh.bdpedidos.sistemapedidos.repository.FormaPagamentoRepository;

@Service
public class FormaPagamentoService extends GenericService<FormaPagamento,FormaPagamentoDTO>{

    @Autowired
    private FormaPagamentoRepository repositorio;


    public Page<FormaPagamentoDTO> listarFormasPagamento(Pageable pageable) {
        return toPageDTO(repositorio.findAll(pageable));
    }

    public Page<FormaPagamentoDTO> buscar(Pageable pageable, String txtBusca) {
        return toPageDTO(repositorio.findByDescricaoContainingIgnoreCase(pageable, txtBusca));
    }


    public FormaPagamentoDTO buscarFormaPagamentoPorId(BigInteger id) throws Exception {
        return toDTO(repositorio.findById(id)
        .orElseThrow(()-> new IdInexistenteException("Forma de Pagamento", id)));
    }

    private void validar(FormaPagamentoDTO origem){
        if( repositorio.existsByDescricao(origem.getDescricao())) 
            throw new FormaPagamentoDuplicadoException(origem.getDescricao());
        
    }

    public FormaPagamentoDTO criarFormaPagamento(FormaPagamentoDTO origem) throws Exception {    
        validar(origem);
        return toDTO(repositorio.save(toEntity(origem)));
    }

    public FormaPagamentoDTO alterarFormaPagamento(BigInteger id, FormaPagamentoDTO origem) throws Exception {
        validar(origem);
        return toDTO(repositorio.save(toEntity(origem)));
    }

    public String excluirFormaPagamento(BigInteger id) throws Exception{
        try{ 
            repositorio.deleteById(id);
             return "Excluído";
        }catch (Exception ex){
            throw new Exception("Não foi possível excluir o id informado." + ex.getMessage());
        }
    }


}
