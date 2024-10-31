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
    private FormaPagamentoRepository formaPagamentoRepository;

// LISTAGENS (GET) //

    // Listar todas as formas de pagamento
    public Page<FormaPagamentoDTO> listarFormasPagamento(Pageable pageable) {
        return toPageDTO(formaPagamentoRepository.findAll(pageable));
    }

    // Listar formas de pagamento filtrando por texto
    public Page<FormaPagamentoDTO> buscar(Pageable pageable, String txtBusca) {
        return toPageDTO(formaPagamentoRepository.findByDescricaoContainingIgnoreCase(pageable, txtBusca));
    }

    // Listar formas de pagamento por ID
    public FormaPagamentoDTO buscarFormaPagamentoPorId(BigInteger id) throws Exception {
        return toDTO(formaPagamentoRepository.findById(id).orElseThrow(() -> new IdInexistenteException("Forma de Pagamento", id)));
    }

// POST - PUT - DELETE //

    // Criar registro de forma de pagamento
    public FormaPagamentoDTO criarFormaPagamento(FormaPagamentoDTO origem) throws Exception {    
        validar(origem);
        return toDTO(formaPagamentoRepository.save(toEntity(origem)));
    }

    // Atualizar registro de forma de pagamento
    public FormaPagamentoDTO alterarFormaPagamento(BigInteger id, FormaPagamentoDTO origem) throws Exception {
        validar(origem);
        return toDTO(formaPagamentoRepository.save(toEntity(origem)));
    }

    // Excluir registro de forma de pagamento
    public String excluirFormaPagamento(BigInteger id) throws Exception{
        try{ 
            formaPagamentoRepository.deleteById(id);
            return "Forma de Pagamento excluída!";
        }catch (Exception ex){
            throw new Exception("Não foi possível excluir o ID informado." + ex.getMessage());
        }
    }

// VALIDAÇÕES //

    // Validação de duplicidade da Forma de Pagamento
    private void validar(FormaPagamentoDTO dto) throws Exception {
        if (formaPagamentoRepository.existsByDescricao(dto.getDescricao())) {
            if (dto.getId() == null) {
                throw new FormaPagamentoDuplicadoException(dto.getDescricao());
            } else {
                FormaPagamento f = formaPagamentoRepository.getReferenceById(dto.getId());
                if (!f.getDescricao().equalsIgnoreCase(dto.getDescricao())) {
                    throw new FormaPagamentoDuplicadoException(dto.getDescricao());
                }
            }
        }

    }




}
