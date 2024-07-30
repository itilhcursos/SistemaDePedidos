package br.com.itilh.bdpedidos.sistemapedidos.controller;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import br.com.itilh.bdpedidos.sistemapedidos.model.FormaPagamento;
import br.com.itilh.bdpedidos.sistemapedidos.repository.FormaPagamentoRepository;



public class FormaPagamentoController {

 private final FormaPagamentoRepository repositorio;

 public FormaPagamentoController(FormaPagamentoRepository repositorio){
    this.repositorio = repositorio;
 }

 @GetMapping("/formas-pagamento")
    public List<FormaPagamento> getTodos() {
        return  (List<FormaPagamento>) repositorio.findAll();
}



}