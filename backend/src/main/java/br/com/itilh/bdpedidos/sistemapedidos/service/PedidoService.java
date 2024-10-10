package br.com.itilh.bdpedidos.sistemapedidos.service;

import java.math.BigInteger;

import org.apache.el.lang.ELArithmetic.BigIntegerDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.itilh.bdpedidos.sistemapedidos.dto.PedidoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.model.Pedido;
import br.com.itilh.bdpedidos.sistemapedidos.repository.PedidoRepository;

@Service
public class PedidoService extends GenericService<Pedido, PedidoDTO> {

    @Autowired
    PedidoRepository repositorio;

    public Page<PedidoDTO> getTodos(Pageable pageable) {
        return toPageDTO(repositorio.findAll(pageable));
    }

    public PedidoDTO getPorId(BigInteger id) throws Exception {
        return toDTO(repositorio.findById(id).orElseThrow(
                () -> new Exception("ID inválido.")));
    }

    private void validar(PedidoDTO dto) throws Exception {

    }

    public PedidoDTO criar(PedidoDTO entityDTO) throws Exception {

        validar(entityDTO);
        try {
            return toDTO(repositorio.save(toEntity(entityDTO)));
        } catch (Exception e) {
            throw new Exception("Erro ao salvar o Pedido.");
        }
    }

    public PedidoDTO alterar(BigInteger id, PedidoDTO novosDados) throws Exception {

        validar(novosDados);
        try {
            return toDTO(repositorio.save(toEntity(novosDados)));
        } catch (Exception e) {
            throw new Exception("Alteração não foi realizada.");
        }
    }

    public String deletePorId(BigInteger id) throws Exception {
        repositorio.deleteById(id);
        return "Excluído";
    }

    public PedidoDTO criarPedido(PedidoDTO entityDTO) {

        throw new UnsupportedOperationException("Unimplemented method 'criarPedido'");
    }

    public PedidoDTO alterarPedido(BigIntegerDelegate id, PedidoDTO origem) {

        throw new UnsupportedOperationException("Unimplemented method 'alterarPedido'");
    }

    @SuppressWarnings("hiding")
    public <BigInteger> String deletePorId(BigInteger id) {

        throw new UnsupportedOperationException("Unimplemented method 'deletePorId'");
    }

}
