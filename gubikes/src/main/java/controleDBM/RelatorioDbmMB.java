package controleDBM;

import java.io.Serializable;
import java.math.BigDecimal;
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
import modeloConvenienciaGuinnesBeer.VendasCartao;
import modeloDBM.AberturaDBM;
import modeloDBM.UsuarioDBM;
import modeloDBM.VendasDBM;

@ViewScoped
@Named("relatorioDbmMB")
public class RelatorioDbmMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private String data;

	private BigDecimal valorTotal;

	private String dataFechamento;

	@Inject
	private GenericDAO<VendasDBM> daoVenda;

	@Inject
	private GenericDAO<UsuarioDBM> daoUsuarioDBM;

	@Inject
	private GenericDAO<VendasCartao> daoVendasCartao;

	private List<VendasDBM> listVendasDBM;

	private UsuarioDBM usuarioSistema;

	/*
	 * @Inject private GenericDAO<Cliente> daoCliente;
	 * 
	 */ @Inject
	private GenericDAO<AberturaDBM> daoAbertura;
	/*
	 * @Inject private GenericDAO<Produtos> daoProduto;
	 * 
	 * @Inject private GenericDAO<Orcamento> daoOrcamento;
	 * 
	 * private Cliente cliente;
	 * 
	 * @Inject private GenericDAO<ParcelaVenda> daoParcelaVenda;
	 */
	@Inject
	private EntityManager manager;

	@PostConstruct
	public void inicializar() {
		// cliente = new Cliente();
		usuarioSistema = new UsuarioDBM();
	}

	public void imprimirRelatorioDia() {

		System.out.println("data passada " + data);
		try {
			List<VendasDBM> relatorio = daoVenda.listar(VendasDBM.class, " dataVenda = '" + data + "'");
			if (relatorio.size() > 0) {

				HashMap parametro = new HashMap<>();
				parametro.put("DATAS", data);
				ChamarRelatorio ch = new ChamarRelatorio("vendasConveniencia.jasper", parametro,
						"relatório de vendas " + data);
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

	public List<UsuarioDBM> completarUsuarios(String str) {
		List<UsuarioDBM> pess = new ArrayList<>();
		pess = daoUsuarioDBM.listaComStatus(UsuarioDBM.class);
		List<UsuarioDBM> pessoasSelecionados = new ArrayList<>();
		for (UsuarioDBM at : pess) {
			if (at.getNomeUsuario().toLowerCase().startsWith(str)) {
				pessoasSelecionados.add(at);
			}
		}
		return pessoasSelecionados;
	}
	
	
	public void imprimirRelatorioFechamentoMesPorUsuario() {

		try {

			List<AberturaDBM> relatorio = daoAbertura.listar(AberturaDBM.class,
					" situacao = 'fechado' and usuario = '"+usuarioSistema.getId()+"' and mes = '" + dataFechamento.charAt(0) + "" + dataFechamento.charAt(1)
							+ "' and ano = '" + dataFechamento.charAt(3) + "" + dataFechamento.charAt(4) + ""
							+ dataFechamento.charAt(5) + "" + dataFechamento.charAt(6) + "'");
			if (relatorio.size() > 0) {
				HashMap parametro = new HashMap<>();
				parametro.put("MES", dataFechamento.charAt(0) + "" + dataFechamento.charAt(1));
				parametro.put("ANO", dataFechamento.charAt(3) + "" + dataFechamento.charAt(4) + ""
						+ dataFechamento.charAt(5) + "" + dataFechamento.charAt(6));
				parametro.put("USUARIO", usuarioSistema.getId());
				ChamarRelatorio ch = new ChamarRelatorio("fechamentoMesConvenienciaDBMUsuario.jasper", parametro,
						"Fechamento de mês ");
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
	
	

	/*
	 * public void imprimirRelatorioContasReceber() { try { List<ParcelaVenda>
	 * relatorio = daoParcelaVenda.listar(ParcelaVenda.class,
	 * " situacao != 'PAGO'"); if (relatorio.size() > 0) {
	 * 
	 * HashMap parametro = new HashMap<>();
	 * 
	 * ChamarRelatorio ch = new ChamarRelatorio("devendoContas.jasper",
	 * parametro, "relatório de contas a receber "); Session sessions =
	 * manager.unwrap(Session.class); sessions.doWork(ch);
	 * 
	 * } else { ExibirMensagem.exibirMensagem(Mensagem.NADA_ENCONTRADO); } }
	 * catch (Exception e) { e.printStackTrace();
	 * ExibirMensagem.exibirMensagem(Mensagem.ERRO); } }
	 * 
	 * 
	 * // public void imprimirRelatorioValoresEstoque() { // try { //
	 * List<Produto> relatorio = daoProduto.listar(Produto.class,
	 * " quantidade != 0 "); // if (relatorio.size() > 0) { // // HashMap
	 * parametro = new HashMap<>(); // // ChamarRelatorio ch = new
	 * ChamarRelatorio("produtosValor.jasper", parametro,
	 * "relatório valores em estoque"); // Session sessions =
	 * manager.unwrap(Session.class); // sessions.doWork(ch); // // } else { //
	 * ExibirMensagem.exibirMensagem(Mensagem.NADA_ENCONTRADO); // } // } catch
	 * (Exception e) { // e.printStackTrace(); //
	 * ExibirMensagem.exibirMensagem(Mensagem.ERRO); // } // }
	 * 
	 * 
	 * public void imprimirRelatorioReceber() { try { List<ParcelaVenda>
	 * relatorio = daoParcelaVenda.listar(ParcelaVenda.class,
	 * " venda.status is true and status is true and situacao != 'PAGO'");
	 * 
	 * for(ParcelaVenda p : relatorio){
	 * System.out.println("pessoas "+p.getVenda().getOrcamento().getCliente().
	 * getPessoa().getNome()); }
	 * 
	 * 
	 * if (relatorio.size() > 0) {
	 * 
	 * HashMap parametro = new HashMap<>();
	 * 
	 * ChamarRelatorio ch = new ChamarRelatorio("contaRecebe.jasper", parametro,
	 * "relatório contas a receber"); Session sessions =
	 * manager.unwrap(Session.class); sessions.doWork(ch);
	 * 
	 * } else { ExibirMensagem.exibirMensagem(Mensagem.NADA_ENCONTRADO); } }
	 * catch (Exception e) { e.printStackTrace();
	 * ExibirMensagem.exibirMensagem(Mensagem.ERRO); } }
	 * 
	 * public void imprimirRelatorioCliente() {
	 * 
	 * try { List<Orcamento> relatorio = daoOrcamento.listar(Orcamento.class,
	 * " status is true and cliente='"+cliente.getId()+"'"); if
	 * (relatorio.size() > 0) {
	 * 
	 * HashMap parametro = new HashMap<>(); parametro.put("DATAS",
	 * cliente.getId()); ChamarRelatorio ch = new
	 * ChamarRelatorio("vendaClienteTodas.jasper", parametro,
	 * "relatório de Cliente "+cliente.getPessoa().getNome()); Session sessions
	 * = manager.unwrap(Session.class); sessions.doWork(ch);
	 * 
	 * } else { ExibirMensagem.exibirMensagem(Mensagem.NADA_ENCONTRADO); } }
	 * catch (Exception e) { e.printStackTrace();
	 * ExibirMensagem.exibirMensagem(Mensagem.ERRO); } } // // public void
	 * imprimirRelatorioCliente(Venda venda) { // // try { // HashMap parametro
	 * = new HashMap<>(); // parametro.put("IDCLIENTE", venda.getId()); //
	 * ChamarRelatorio ch = new ChamarRelatorio("vendasCliente.jasper",
	 * parametro, // "relatório de vendas Cliente :" +
	 * venda.getOrcamento().getCliente().getPessoa().getNome()); // Session
	 * sessions = manager.unwrap(Session.class); // sessions.doWork(ch); // // }
	 * catch (Exception e) { // e.printStackTrace(); //
	 * ExibirMensagem.exibirMensagem(Mensagem.ERRO); // } // } // // public void
	 * imprimirRelatorioClienteParcelado(Venda venda) { // // try { // HashMap
	 * parametro = new HashMap<>(); // parametro.put("IDCLIENTE",
	 * venda.getId()); // ChamarRelatorio ch = new
	 * ChamarRelatorio("vendasParcela.jasper", parametro, //
	 * "relatório de par Cliente :" +
	 * venda.getOrcamento().getCliente().getPessoa().getNome()); // Session
	 * sessions = manager.unwrap(Session.class); // sessions.doWork(ch); // // }
	 * catch (Exception e) { // e.printStackTrace(); //
	 * ExibirMensagem.exibirMensagem(Mensagem.ERRO); // } // }
	 * 
	 * public void imprimirRelatorioOrcamento(Orcamento orcamento) {
	 * 
	 * try { HashMap parametro = new HashMap<>(); parametro.put("IDORCAMENTO",
	 * orcamento.getId()); ChamarRelatorio ch = new
	 * ChamarRelatorio("orcamentoCliente.jasper", parametro,
	 * "Orçamento Cliente :" + orcamento.getCliente().getPessoa().getNome());
	 * Session sessions = manager.unwrap(Session.class); sessions.doWork(ch);
	 * 
	 * } catch (Exception e) { e.printStackTrace();
	 * ExibirMensagem.exibirMensagem(Mensagem.ERRO); } }
	 */

	public void buscarVendas() {

		listVendasDBM = new ArrayList<>();

		listVendasDBM = daoVenda.listar(VendasDBM.class, " dataVenda = '" + data + "'");

		if (listVendasDBM.size() == 0) {
			ExibirMensagem.exibirMensagem(Mensagem.NADA_ENCONTRADO);
		} else {
			valorTotal = new BigDecimal(0);
			for (VendasDBM o : listVendasDBM) {
				valorTotal = valorTotal.add(o.getValorTotal());
			}
		}

		List<VendasCartao> listVendasDBMCartao = daoVendasCartao.listar(VendasCartao.class,
				" dataVenda = '" + data + "'");

		if (listVendasDBMCartao.size() > 0) {

			for (VendasCartao o : listVendasDBMCartao) {
				valorTotal = valorTotal.add(o.getValorTotal());
			}
		}

	}

	public void imprimirRelatorioFechamentoMes() {

		try {

			List<AberturaDBM> relatorio = daoAbertura.listar(AberturaDBM.class,
					" situacao = 'fechado' and mes = '" + dataFechamento.charAt(0) + "" + dataFechamento.charAt(1)
							+ "' and ano = '" + dataFechamento.charAt(3) + "" + dataFechamento.charAt(4) + ""
							+ dataFechamento.charAt(5) + "" + dataFechamento.charAt(6) + "'");
			if (relatorio.size() > 0) {
				HashMap parametro = new HashMap<>();
				parametro.put("MES", dataFechamento.charAt(0) + "" + dataFechamento.charAt(1));
				parametro.put("ANO", dataFechamento.charAt(3) + "" + dataFechamento.charAt(4) + ""
						+ dataFechamento.charAt(5) + "" + dataFechamento.charAt(6));
				ChamarRelatorio ch = new ChamarRelatorio("fechamentoMesConveniencia.jasper", parametro,
						"Fechamento de mês ");
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

			List<AberturaDBM> relatorio = daoAbertura.listar(AberturaDBM.class,
					" situacao = 'fechado' and dataAbertura = '" + data + "'");
			if (relatorio.size() > 0) {
				HashMap parametro = new HashMap<>();
				parametro.put("MES", data);
				parametro.put("ANO", data);
				ChamarRelatorio ch = new ChamarRelatorio("fechamentoCaixaConveniencia.jasper", parametro,
						"Fechamento de Caixa ");
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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(String dataFechamento) {
		this.dataFechamento = dataFechamento;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public List<VendasDBM> getListVendasDBM() {
		return listVendasDBM;
	}

	public void setListVendasDBM(List<VendasDBM> listVendasDBM) {
		this.listVendasDBM = listVendasDBM;
	}

	public UsuarioDBM getUsuarioSistema() {
		return usuarioSistema;
	}

	public void setUsuarioSistema(UsuarioDBM usuarioSistema) {
		this.usuarioSistema = usuarioSistema;
	}

}
