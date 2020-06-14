package com.rafaeldeda.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rafaeldeda.cursomc.domain.Categoria;
import com.rafaeldeda.cursomc.domain.Cidade;
import com.rafaeldeda.cursomc.domain.Cliente;
import com.rafaeldeda.cursomc.domain.Endereco;
import com.rafaeldeda.cursomc.domain.Estado;
import com.rafaeldeda.cursomc.domain.ItemPedido;
import com.rafaeldeda.cursomc.domain.Pagamento;
import com.rafaeldeda.cursomc.domain.PagamentoComBoleto;
import com.rafaeldeda.cursomc.domain.PagamentoComCartao;
import com.rafaeldeda.cursomc.domain.Pedido;
import com.rafaeldeda.cursomc.domain.Produto;
import com.rafaeldeda.cursomc.domain.enums.EstadoPagamento;
import com.rafaeldeda.cursomc.domain.enums.TipoCliente;
import com.rafaeldeda.cursomc.repositories.CategoriaRepository;
import com.rafaeldeda.cursomc.repositories.CidadeRepository;
import com.rafaeldeda.cursomc.repositories.ClienteRepository;
import com.rafaeldeda.cursomc.repositories.EnderecoRepository;
import com.rafaeldeda.cursomc.repositories.EstadoRepository;
import com.rafaeldeda.cursomc.repositories.ItemPedidoRepository;
import com.rafaeldeda.cursomc.repositories.PagamentoRepository;
import com.rafaeldeda.cursomc.repositories.PedidoRepository;
import com.rafaeldeda.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama, mesa e banho");
		Categoria cat4 = new Categoria(null, "Jardinagem");
		Categoria cat5 = new Categoria(null, "Perfumaria");
		Categoria cat6 = new Categoria(null, "Eletrônico");
		Categoria cat7 = new Categoria(null, "Papelaria");
		
		Produto p1 = new Produto(null, "Computador", 2000.0);
		Produto p2 = new Produto(null, "Impressora", 400.0);
		Produto p3 = new Produto(null, "Mouse", 80.0);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "009.257.385-14", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("999988889", "32312540"));

		Endereco e1 = new Endereco(null, "Rua Flores", "123", "CASA", "Grageru", "49015110", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "321", "COMERCIO", "Centro", "88777000", cli1, c2);

		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 11:30"), cli1, e2);

		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);

		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"),
				null);
		ped2.setPagamento(pagto2);

		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.0, 1, 2000.0);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.0, 2, 80.0);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.0, 1, 800.0);
		
		ped1.getItens().addAll(Arrays.asList(ip1,ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));

		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));

		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));

	}

}
