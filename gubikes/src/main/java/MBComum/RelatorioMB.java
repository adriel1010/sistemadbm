package MBComum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.hibernate.Session;

import util.ChamarRelatorio;
import util.ExibirMensagem;
import util.Mensagem;
import dao.GenericDAO;
import modeloConvenienciaGuinnesBeer.Abertura;
import modeloConvenienciaGuinnesBeer.Vendas;
 
 

@ViewScoped
@Named("relatorioMB")
public class RelatorioMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private String data;

	private String dataFechamento;

	@Inject
	private GenericDAO<Vendas> daoVenda;
	/*	
	@Inject
	private GenericDAO<Cliente> daoCliente;		
	
*/	@Inject
	private GenericDAO<Abertura> daoAbertura;	
/*	@Inject
	private GenericDAO<Produtos> daoProduto;
	
	@Inject
	private GenericDAO<Orcamento> daoOrcamento;
	
	private Cliente cliente;
	
	@Inject
	private GenericDAO<ParcelaVenda> daoParcelaVenda;
*/
	@Inject
	private EntityManager manager;

	@PostConstruct
	public void inicializar() {
		//cliente = new Cliente();
	}

	public void imprimirRelatorioDia() {

		System.out.println("data passada " + data);
		try {
			List<Vendas> relatorio = daoVenda.listar(Vendas.class, " dataVenda = '" + data + "'");
			if (relatorio.size() > 0) {

				HashMap parametro = new HashMap<>();
				parametro.put("DATAS", data);
				ChamarRelatorio ch = new ChamarRelatorio("vendasConvenienciaGuinnes.jasper", parametro, "relat�rio de vendas " + data);
				Session sessions = manager.unwrap(Session.class);
				sessions.doWork(ch);

			} else {
				ExibirMensagem.exibirMensagem(Mensagem.NADA_ENCONTRADO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
		}
	}
	
 
	
	
	/*public void imprimirRelatorioContasReceber() { 
		try {
			List<ParcelaVenda> relatorio = daoParcelaVenda.listar(ParcelaVenda.class, " situacao != 'PAGO'");
			if (relatorio.size() > 0) {

				HashMap parametro = new HashMap<>();
				
				ChamarRelatorio ch = new ChamarRelatorio("devendoContas.jasper", parametro, "relat�rio de contas a receber ");
				Session sessions = manager.unwrap(Session.class);
				sessions.doWork(ch);

			} else {
				ExibirMensagem.exibirMensagem(Mensagem.NADA_ENCONTRADO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
		}
	}
	
	
//	public void imprimirRelatorioValoresEstoque() { 
//		try {
//			List<Produto> relatorio = daoProduto.listar(Produto.class, " quantidade != 0 ");
//			if (relatorio.size() > 0) {
//
//				HashMap parametro = new HashMap<>();
//				
//				ChamarRelatorio ch = new ChamarRelatorio("produtosValor.jasper", parametro, "relat�rio valores em estoque");
//				Session sessions = manager.unwrap(Session.class);
//				sessions.doWork(ch);
//
//			} else {
//				ExibirMensagem.exibirMensagem(Mensagem.NADA_ENCONTRADO);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
//		}
//	}
	
	
	public void imprimirRelatorioReceber() { 
		try {
			List<ParcelaVenda> relatorio = daoParcelaVenda.listar(ParcelaVenda.class, " venda.status is true and status is true and situacao != 'PAGO'");
			
			for(ParcelaVenda p : relatorio){
				System.out.println("pessoas "+p.getVenda().getOrcamento().getCliente().getPessoa().getNome());
			}
			
			
			if (relatorio.size() > 0) {

				HashMap parametro = new HashMap<>();
				
				ChamarRelatorio ch = new ChamarRelatorio("contaRecebe.jasper", parametro, "relat�rio contas a receber");
				Session sessions = manager.unwrap(Session.class);
				sessions.doWork(ch);

			} else {
				ExibirMensagem.exibirMensagem(Mensagem.NADA_ENCONTRADO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
		}
	}
	
	public void imprimirRelatorioCliente() { 
		 
		try {
			List<Orcamento> relatorio = daoOrcamento.listar(Orcamento.class, " status is true and cliente='"+cliente.getId()+"'");
			if (relatorio.size() > 0) {

				HashMap parametro = new HashMap<>();
				parametro.put("DATAS", cliente.getId());
				ChamarRelatorio ch = new ChamarRelatorio("vendaClienteTodas.jasper", parametro, "relat�rio de Cliente "+cliente.getPessoa().getNome());
				Session sessions = manager.unwrap(Session.class);
				sessions.doWork(ch);

			} else {
				ExibirMensagem.exibirMensagem(Mensagem.NADA_ENCONTRADO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
		}
	}
//
//	public void imprimirRelatorioCliente(Venda venda) {
//
//		try {
//			HashMap parametro = new HashMap<>();
//			parametro.put("IDCLIENTE", venda.getId());
//			ChamarRelatorio ch = new ChamarRelatorio("vendasCliente.jasper", parametro,
//					"relat�rio de vendas Cliente :" + venda.getOrcamento().getCliente().getPessoa().getNome());
//			Session sessions = manager.unwrap(Session.class);
//			sessions.doWork(ch);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
//		}
//	}
//	
//	public void imprimirRelatorioClienteParcelado(Venda venda) {
//
//		try {
//			HashMap parametro = new HashMap<>();
//			parametro.put("IDCLIENTE", venda.getId());
//			ChamarRelatorio ch = new ChamarRelatorio("vendasParcela.jasper", parametro,
//					"relat�rio de par Cliente :" + venda.getOrcamento().getCliente().getPessoa().getNome());
//			Session sessions = manager.unwrap(Session.class);
//			sessions.doWork(ch);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
//		}
//	}

	public void imprimirRelatorioOrcamento(Orcamento orcamento) {

		try {
			HashMap parametro = new HashMap<>();
			parametro.put("IDORCAMENTO", orcamento.getId());
			ChamarRelatorio ch = new ChamarRelatorio("orcamentoCliente.jasper", parametro,
					"Or�amento Cliente :" + orcamento.getCliente().getPessoa().getNome());
			Session sessions = manager.unwrap(Session.class);
			sessions.doWork(ch);

		} catch (Exception e) {
			e.printStackTrace();
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
		}
	}
*/
	public void imprimirRelatorioFechamentoMes() {

		try {

			List<Abertura> relatorio = daoAbertura.listar(Abertura.class, " situacao = 'fechado' and mes = '" + dataFechamento.charAt(0) + "" + dataFechamento.charAt(1) + "' and ano = '"+dataFechamento.charAt(3) + "" + dataFechamento.charAt(4) + ""
					+ dataFechamento.charAt(5) + "" + dataFechamento.charAt(6)+"'");
			if (relatorio.size() > 0) {
				HashMap parametro = new HashMap<>();
				parametro.put("MES", dataFechamento.charAt(0) + "" + dataFechamento.charAt(1));
				parametro.put("ANO", dataFechamento.charAt(3) + "" + dataFechamento.charAt(4) + ""
						+ dataFechamento.charAt(5) + "" + dataFechamento.charAt(6));
				ChamarRelatorio ch = new ChamarRelatorio("fechamentoMesCokkjjknvenienciaGuinnes.jasper", parametro, "Fechamento de m�s ");
				Session sessions = manager.unwrap(Session.class);
				sessions.doWork(ch);
			} else {
				ExibirMensagem.exibirMensagem(Mensagem.NADA_ENCONTRADO);
			}

		} catch (Exception e) {
			e.printStackTrace();
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
		}
	}
	
	public void imprimirRelatorioFechamentoCaixa() {

		try {

			List<Abertura> relatorio = daoAbertura.listar(Abertura.class, " situacao = 'fechado' and dataAbertura = '" +data+"'");
			if (relatorio.size() > 0) {
				HashMap parametro = new HashMap<>();
				parametro.put("MES", data);
				parametro.put("ANO", data);
				ChamarRelatorio ch = new ChamarRelatorio("fechamentoCaixaGuinnes.jasper", parametro, "Fechamento de Caixa ");
				Session sessions = manager.unwrap(Session.class);
				sessions.doWork(ch);
			} else {
				ExibirMensagem.exibirMensagem(Mensagem.NADA_ENCONTRADO);
			}

		} catch (Exception e) {
			e.printStackTrace();
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
		}
	}
	
	
//	public void imprimirRelatorioFechamentoAno() {
//
//		String ano = dataFechamento.charAt(0) + "" + dataFechamento.charAt(1) + ""
//				+ dataFechamento.charAt(2) + "" + dataFechamento.charAt(3);
//		 
//		try {
//
//			List<Venda> relatorio = daoVenda.listar(Venda.class,  " ano = '"+dataFechamento.charAt(0) + "" + dataFechamento.charAt(1) + ""
//					+ dataFechamento.charAt(2) + "" + dataFechamento.charAt(3)+"'");
//			if (relatorio.size() > 0) {
//				HashMap parametro = new HashMap<>(); 
//				parametro.put("ANO", ano);
//				ChamarRelatorio ch = new ChamarRelatorio("fechamentoAno.jasper", parametro, "Fechamento de Ano ");
//				Session sessions = manager.unwrap(Session.class);
//				sessions.doWork(ch);
//			} else {
//				ExibirMensagem.exibirMensagem(Mensagem.NADA_ENCONTRADO);
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
//		}
//	}
	
	/*public List<Cliente> completarCliente(String str) {
		List<Cliente> pess = new ArrayList<>();
		pess = daoCliente.listarCodicaoLivre(Cliente.class, "statusCliente is true");
		List<Cliente> pessoasSelecionados = new ArrayList<>();
		for (Cliente at : pess) {
			if (at.getPessoa().getNome().toLowerCase().startsWith(str)) {
				pessoasSelecionados.add(at);
			}
		}
		return pessoasSelecionados;
	}*/

	public String getData() {
		return data;
	}
	
	
/*
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}*/

	public void setData(String data) {
		this.data = data;
	}

	public String getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(String dataFechamento) {
		this.dataFechamento = dataFechamento;
	}
	
	

}
