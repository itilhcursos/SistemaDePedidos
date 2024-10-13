package br.com.itilh.bdpedidos.sistemapedidos.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import br.com.itilh.bdpedidos.sistemapedidos.repository.FormaPagamentoRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FormaPagamentoControllerTest {

      @Autowired
      MockMvc mockMvc;

      @Autowired
      FormaPagamentoRepository formaPagamentoRepository;

      @Test
      @DisplayName("Teste do path /formaPagamento")
      void testGetFormaPagamento() throws Exception{
        mockMvc.perform(get("/formaPagamento")).andExpect(status().isOk())
        .andExpect(content().string(containsString("totalElements")));
      }

      @Test
      @DisplayName("Teste de path inexistente")
      void testGetFormaPagamentoInexistente() throws Exception{
        mockMvc.perform(get("/formaPagamento")).andExpect(status().isMethodNotAllowed());
      }

}
