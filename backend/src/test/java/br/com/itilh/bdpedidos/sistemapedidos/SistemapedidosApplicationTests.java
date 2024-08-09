package br.com.itilh.bdpedidos.sistemapedidos;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class SistemapedidosApplicationTests {

	@Test
	 void contextLoads(ApplicationContext context) {
		assertThat(context).isNotNull();
	  }

}
