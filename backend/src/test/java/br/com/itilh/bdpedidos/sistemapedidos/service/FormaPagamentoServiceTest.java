package br.com.itilh.bdpedidos.sistemapedidos.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigInteger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.itilh.bdpedidos.sistemapedidos.dto.FormaPagamentoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.FormaPagamentoDuplicadaException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.model.FormaPagamento;
import br.com.itilh.bdpedidos.sistemapedidos.repository.FormaPagamentoRepository;

@SpringBootTest
@ActiveProfiles("test")
public class FormaPagamentoServiceTest {

  @Autowired
  FormaPagamentoService formaPagamentoService;

  @Autowired
  FormaPagamentoRepository formaPagamentoRepository;

  void setUpFormaPagamento(){
  FormaPagamento formaPagamento = new FormaPagamento(BigInteger.ONE, "Forma de pagamento teste", true);
  formaPagamentoRepository.save(formaPagamento);
  }

  @Test
  @DisplayName("Teste de busca por ID existente")
  void testBuscarFormaPagamentoPorIdExistente() throws Exception {
      FormaPagamentoDTO dto = new FormaPagamentoDTO(BigInteger.ONE, "Forma de pagamento existente", true);
      FormaPagamentoDTO dtoNovo = formaPagamentoService.criarFormaPagamento(dto);
      FormaPagamentoDTO dtoBuscado = formaPagamentoService.buscarFormaPagamentoPorId(dtoNovo.getId());
      assertEquals(dtoNovo.getDescricao(), dtoBuscado.getDescricao());
  }

  @Test
  @DisplayName("Teste de busca por ID inexistente")
  void testBuscarFormaPagamentoPorIdInexistente() {
      assertThrows(IdInexistenteException.class,
              () -> formaPagamentoService.buscarFormaPagamentoPorId(BigInteger.valueOf(999999L)));
  }

  @Test
  @DisplayName("Teste de criação de forma de pagamento")
  void testCriarFormaPagamento() throws Exception {
    setUpFormaPagamento();
    FormaPagamentoDTO dto = new FormaPagamentoDTO(BigInteger.ONE, "Forma de pagamento", true);
    FormaPagamentoDTO dtoRetorno = formaPagamentoService.criarFormaPagamento(dto);
    assertNotNull(dtoRetorno.getId());
  }

  @Test
  @DisplayName("Teste de criação de forma de pagamento duplicada")
  void testCriarFormaPagamentoDuplicado() throws Exception {
    setUpFormaPagamento();
    FormaPagamentoDTO dto = new FormaPagamentoDTO(BigInteger.ONE, "Forma de pagamento", true);
    formaPagamentoService.criarFormaPagamento(dto);
    assertThrows(FormaPagamentoDuplicadaException.class, () -> formaPagamentoService.criarFormaPagamento(dto));
  }

  @Test
  @DisplayName("Teste de alteração de forma de pagamento")
  void testAlterarFormaPagamento() throws Exception {
      FormaPagamentoDTO dtoOriginal = new FormaPagamentoDTO(BigInteger.ONE, "Forma de pagamento original", true);
      FormaPagamentoDTO dtoRetorno = formaPagamentoService.criarFormaPagamento(dtoOriginal);

      FormaPagamentoDTO dtoAlterado = new FormaPagamentoDTO(dtoRetorno.getId(), "Forma de pagamento alterada", true);
      dtoAlterado = formaPagamentoService.alterarFormaPagamento(dtoRetorno.getId(), dtoAlterado);
      assertEquals("Forma de pagamento alterada", dtoAlterado.getDescricao());
  }

  @Test
  @DisplayName("Teste de exclusão de forma de pagamento")
  void testExcluirFormaPagamento() throws Exception {
      FormaPagamentoDTO dto = new FormaPagamentoDTO(BigInteger.ONE, "Forma de pagamento a excluir", true);
      FormaPagamentoDTO dtoRetorno = formaPagamentoService.criarFormaPagamento(dto);

      String resultado = formaPagamentoService.excluirFormaPagamento(dtoRetorno.getId());
      assertEquals("Excluído", resultado);
  }


}
