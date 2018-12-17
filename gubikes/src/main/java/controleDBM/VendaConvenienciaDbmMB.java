package controleDBM;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import util.CriptografiaSenha;
import util.ExibirMensagem;
import util.FecharDialog;
import util.Mensagem;
import util.UsuarioSessaoDbmMB;
import util.UsuarioSessaoMB;
import util.ValidaCadastro;
import dao.GenericDAO;
import modeloDBM.AberturaDBM;
import modeloDBM.ItensVendaProdutoDBM;
import modeloDBM.PermissaoDBM;
import modeloDBM.ProdutosDBM;
import modeloDBM.TipoDBM;
import modeloDBM.UsuarioDBM;
import modeloDBM.VendasDBM;
import modeloDBM.VendasCartaoDBM;
import serviceDBM.AberturaService;
import serviceDBM.FuncionarioService;
import serviceDBM.ItensProdutoService;
import serviceDBM.PermissaoService;
import serviceDBM.VendaCartaoService;
import serviceDBM.VendaService;

@ViewScoped
@Named("vendaConvenienciaDbmMB")
public class VendaConvenienciaDbmMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private AberturaDBM abertura;
	private UsuarioDBM usuario;
	private VendasDBM venda;
	private ItensVendaProdutoDBM itensVendaProduto;
	private List<ItensVendaProdutoDBM> listItensVendaProduto;
	private String codigoProduto;
	private List<ProdutosDBM> listProduto;
	private VendasDBM buscaVenda;
	private Integer quantidadeProduto;
	private boolean someTexto = false;
	private Integer codigoItem = 0;
	private Integer codigoItemRemove;
	private BigDecimal[] valores = new BigDecimal[3];
	private List<BigDecimal> listaDinheiro = new ArrayList<>();
	private List<BigDecimal> listaTroco = new ArrayList<>();
	private List<BigDecimal> listaTotal = new ArrayList<>();
	private BigDecimal valorTotalItens = new BigDecimal(0);
	private BigDecimal dinheiro = new BigDecimal(0);
	private BigDecimal troco = new BigDecimal(0);
	private BigDecimal subTotal = new BigDecimal(0);
	private BigDecimal valorDiversos = new BigDecimal(0);
	private boolean escondeValor = false;
	private boolean ativaValor2 = false;
	private AberturaDBM aberturaVerificada;
	private String dataVendaSetada;
	private int flag = 1;
	private BigDecimal valorVendaTotalAtribuido;

	@Inject
	private GenericDAO<AberturaDBM> daoAbertura;

	@Inject
	private GenericDAO<UsuarioDBM> daoUsuario;

	@Inject
	private GenericDAO<VendasDBM> daoVenda;

	@Inject
	private GenericDAO<VendasCartaoDBM> daoVendasCartao;

	@Inject
	private GenericDAO<ItensVendaProdutoDBM> daoItensVendaProduto;

	@Inject
	private GenericDAO<ProdutosDBM> daoProdutos;

	@Inject
	private AberturaService aberturaService;

	@Inject
	private VendaService vendaService;

	@Inject
	private VendaCartaoService vendaCartaoService;

	@Inject
	private ItensProdutoService itensProdutoService;

	@Inject
	private UsuarioSessaoDbmMB usuarioSessao;

	// update panel
	// context.update("form:panel");

	@PostConstruct
	public void inicializar() {

		venda = new VendasDBM();
		usuario = new UsuarioDBM();
		usuario = usuarioSessao.recuperarUsuario();
		abertura = new AberturaDBM();
		verificaAbertura();
		quantidadeProduto = 1;
		listItensVendaProduto = new ArrayList<>();
		RequestContext.getCurrentInstance().execute("$(function(){PrimeFaces.focus('dadosProduto:porct');});");

	}

	public void voltar() {

		System.out.println("chamou voltar");

		try {
			if (listItensVendaProduto.size() > 0) {
				ExibirMensagem.exibirMensagem(Mensagem.VENDAABERTO);
			} else {
				FacesContext.getCurrentInstance().getExternalContext().redirect("inicio2.jsf");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void voltar2() {

		try {

			FacesContext.getCurrentInstance().getExternalContext().redirect("inicio2.jsf");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void verificaTroco() {

		if (flag == 1) {
			valorVendaTotalAtribuido = valorTotalItens;
		}

		BigDecimal subtracao = dinheiro.subtract(valorVendaTotalAtribuido);

		if (subtracao.doubleValue() < 0) {

			subTotal = subtracao.multiply(new BigDecimal(-1));

			valorVendaTotalAtribuido = subTotal;

			dinheiro = null;

			RequestContext.getCurrentInstance().update("frmFechaVenda:inputDinheiro");

			RequestContext.getCurrentInstance()
					.execute("$(function(){PrimeFaces.focus('frmFechaVenda:inputDinheiro');});");
			flag = 0;

			RequestContext.getCurrentInstance().update("frmFechaVenda:falta");

		} else {
			troco = subtracao;
			RequestContext.getCurrentInstance().execute("$(function(){PrimeFaces.focus('frmFechaVenda:troco');});");
			flag = 1;
			// salvaItensEVenda();**

		}

	}

	public void cancelaVenda() {
		listItensVendaProduto.clear();
		valorTotalItens = new BigDecimal(0);
		dinheiro = null;
		subTotal = null;
		troco = null;
		codigoProduto = null;

		RequestContext.getCurrentInstance().execute("$(function(){PrimeFaces.focus('dadosProduto:porct');});");

		FecharDialog.fecharDialogProduto();

	}

	public void fecharCaixa() {

		if (listItensVendaProduto.size() > 0) {
			ExibirMensagem.exibirMensagem(Mensagem.VENDAABERTA);
		} else {

			BigDecimal valorFechamentoDinheiro = new BigDecimal(0);
			BigDecimal valorFechamentoTotal = new BigDecimal(0);
			BigDecimal valorLucroDiario = new BigDecimal(0);
			BigDecimal valorFechamentoCartao = new BigDecimal(0);
			BigDecimal valorLucroDiarioCartaoNotas = new BigDecimal(0);
			BigDecimal valorFechamentoCartaoNotas = new BigDecimal(0);
			BigDecimal valorFechamentoTotalNotasDinheiro = new BigDecimal(0);
			BigDecimal valorLucroTotalNotasDinheiro = new BigDecimal(0);

			String dataFormatada = pegaDataFormatada();
			AberturaDBM aberturaCaixa = new AberturaDBM();

			List<AberturaDBM> listAbertura = new ArrayList<>();

			listAbertura = daoAbertura.listar(AberturaDBM.class,
					" situacao = 'aberto' and usuario ='" + usuario.getId() + "'");
			if (listAbertura.size() > 0) {
				aberturaCaixa = listAbertura.get(0);
			}

			List<VendasDBM> listVendasDinheiro = new ArrayList<>();

			listVendasDinheiro = daoVenda.listar(VendasDBM.class,
					" situacao = 'fechado' and status is true and situacaoAbertura = 'aberto' and tipoPagamento = 'DINHEIRO' and dataVenda = '"
							+ aberturaCaixa.getDataAbertura() + "' and usuario = '" + usuario.getId() + "'");
			if (listVendasDinheiro.size() > 0) {
				for (VendasDBM v : listVendasDinheiro) {
					valorFechamentoDinheiro = valorFechamentoDinheiro.add(v.getValorTotal());
					valorLucroDiario = valorLucroDiario.add(v.getLucroVenda());
				}
			}

			List<VendasDBM> listVendasCartao = new ArrayList<>();

			listVendasCartao = daoVenda.listar(VendasDBM.class,
					" situacao = 'fechado' and status is true and situacaoAbertura = 'aberto' and  tipoPagamento = 'CARTAO' and dataVenda = '"
							+ aberturaCaixa.getDataAbertura() + "' and usuario = '" + usuario.getId() + "'");
			if (listVendasCartao.size() > 0) {
				for (VendasDBM v : listVendasCartao) {
					valorFechamentoCartao = valorFechamentoCartao.add(v.getValorTotal());
					valorLucroDiario = valorLucroDiario.add(v.getLucroVenda());
				}
			}

			List<VendasCartaoDBM> listVendasCartaoNotas = new ArrayList<>();

			listVendasCartaoNotas = daoVendasCartao.listar(VendasCartaoDBM.class,
					" situacao = 'fechado' and status is true and situacaoAbertura = 'aberto'  and dataVenda = '"
							+ aberturaCaixa.getDataAbertura() + "' and usuario = '" + usuario.getId() + "'");
			if (listVendasCartaoNotas.size() > 0) {
				for (VendasCartaoDBM v : listVendasCartaoNotas) {
					valorFechamentoCartaoNotas = valorFechamentoCartaoNotas.add(v.getValorTotal());
					valorLucroDiarioCartaoNotas = valorLucroDiarioCartaoNotas.add(v.getLucroVenda());

					v.setSituacaoAbertura("fechado");
					vendaCartaoService.inserirAlterar(v);
				}
			}

			valorFechamentoTotal = valorFechamentoDinheiro.add(valorFechamentoCartao);
			// seta o valor de fechamento no dinherio e total dinheiro e cartao
			aberturaCaixa.setValorFechamentoDinheiro(valorFechamentoDinheiro);
			aberturaCaixa.setValorFechamento(valorFechamentoTotal);

			// seta o lucro diario no dinheiro e valor vendido no cartao
			aberturaCaixa.setLucroDiario(valorLucroDiario);
			aberturaCaixa.setValorFechamentoCartao(valorFechamentoCartao);

			// seta o valor vendido no cartãozinho e lucro cartãozinho
			aberturaCaixa.setLucroDiarioCartaoNotas(valorLucroDiarioCartaoNotas);
			aberturaCaixa.setValorFechamentoCartaoNotas(valorFechamentoCartaoNotas);

			// seta o valor vendido total cartãozinho + dinheiro e lucro
			// cartãozinho + dinheiro
			aberturaCaixa.setValorFechamentoTotal(valorFechamentoCartaoNotas.add(aberturaCaixa.getValorFechamento()));
			aberturaCaixa.setLucroDiarioTotal(valorLucroDiarioCartaoNotas.add(aberturaCaixa.getLucroDiario()));

			aberturaCaixa.setSituacao("fechado");

			aberturaService.inserirAlterar(aberturaCaixa);

			List<VendasDBM> listVendasAlterar = new ArrayList<>();

			listVendasAlterar = daoVenda.listar(VendasDBM.class,
					" situacao = 'fechado' and status is true and situacaoAbertura = 'aberto' and dataVenda = '"
							+ aberturaCaixa.getDataAbertura() + "' and usuario = '" + usuario.getId() + "'");
			if (listVendasAlterar.size() > 0) {
				for (VendasDBM v : listVendasAlterar) {
					v.setSituacaoAbertura("fechado");
					vendaService.inserirAlterar(v);
				}
			}

			voltar();
		}

	}

	public void salvaItensEVenda() {

		verificarVenda();
		BigDecimal lucroVenda = new BigDecimal(0);
		BigDecimal valorTotalVenda = new BigDecimal(0);
		for (ItensVendaProdutoDBM itensVendaProduto : listItensVendaProduto) {
			itensVendaProduto.setVenda(buscaVenda);
			lucroVenda = lucroVenda.add(itensVendaProduto.getLucroUnitario());
			valorTotalVenda = valorTotalVenda.add(itensVendaProduto.getSubTotalItem());
			itensProdutoService.inserirAlterar(itensVendaProduto);
		}

		buscaVenda.setSituacao("fechado");
		buscaVenda.setSituacaoAbertura("aberto");
		buscaVenda.setValorTotal(valorTotalVenda);
		buscaVenda.setSubTotal(valorTotalVenda);
		buscaVenda.setTipoPagamento("DINHEIRO");
		buscaVenda.setLucroVenda(lucroVenda);

		vendaService.inserirAlterar(buscaVenda);

		FecharDialog.fecharDlgFechaVenda();
		listItensVendaProduto = new ArrayList<>();
		valorTotalItens = new BigDecimal(0);
		dinheiro = new BigDecimal(0);
		subTotal = new BigDecimal(0);
		troco = new BigDecimal(0);

		codigoItem = 0;
		RequestContext.getCurrentInstance().execute("$(function(){PrimeFaces.focus('dadosProduto:porct');});");

	}

	public void finalizarVenda() {

	}

	public void removerItem() {
		ItensVendaProdutoDBM produtoRemover = new ItensVendaProdutoDBM();
		boolean verificaRemover = false;

		for (ItensVendaProdutoDBM i : listItensVendaProduto) {

			if (i.getCodigoItem().equals(codigoItemRemove)) {

				produtoRemover = i;
				verificaRemover = true;
			}
		}

		if (verificaRemover) {

			listItensVendaProduto.remove(produtoRemover);
			valorTotalItens = valorTotalItens.subtract(produtoRemover.getSubTotalItem());
			FecharDialog.fechaDlgRemoveItem();
			codigoItemRemove = null;
			RequestContext.getCurrentInstance().execute("$(function(){PrimeFaces.focus('dadosProduto:porct');});");
		} else {
			ExibirMensagem.exibirMensagem(Mensagem.PRODUTOINVALID);

		}
	}

	public void buscarVenda() {
		System.out.println("id p  b " + buscaVenda.getId());
		listItensVendaProduto = daoItensVendaProduto.listaComStatus(ItensVendaProdutoDBM.class);
		// listItensVendaProduto =
		// daoItensVendaProduto.listar(ItensVendaProduto.class,
		// "status is true and venda = '" + buscaVenda.getId() + "'");

		for (ItensVendaProdutoDBM f : listItensVendaProduto) {
			System.out.println(f.getProduto().getNome());
		}

	}

	// método responsável por verificar se o cara logado tem alguma venda em
	// aberto ou não
	public void verificarVenda() {

		String dataFormatada = pegaDataFormatada();
		char dataMes1 = dataFormatada.charAt(3);
		char dataMes2 = dataFormatada.charAt(4);
		char dataAno1 = dataFormatada.charAt(6);
		char dataAno2 = dataFormatada.charAt(7);
		char dataAno3 = dataFormatada.charAt(8);
		char dataAno4 = dataFormatada.charAt(9);

		buscaVenda = new VendasDBM();

		List<VendasDBM> listBuscaVenda = new ArrayList<>();

		listBuscaVenda = daoVenda.listar(VendasDBM.class,
				"status is true and situacao = 'aberto' and usuario = '" + usuario.getId() + "'");
		if (listBuscaVenda.size() > 0) {
			buscaVenda = listBuscaVenda.get(0);
			System.out.println("venda  " + buscaVenda.getId());
		} else {
			buscaVenda.setMes(dataMes1 + "" + dataMes2);
			buscaVenda.setAno(dataAno1 + "" + dataAno2 + "" + dataAno3 + "" + dataAno4);
			buscaVenda.setDataVenda(dataVendaSetada);
			buscaVenda.setSituacao("aberto");
			buscaVenda.setStatus(true);
			buscaVenda.setUsuario(usuario);
			vendaService.inserirAlterar(buscaVenda);
			// onde seta a data da venda
		}

		// buscarVenda();

	}

	/* inicio do add produto direto no banco */
	public void adicionaProduto() {
		ProdutosDBM produtoAdd = new ProdutosDBM();
		List<ProdutosDBM> listPorduto = new ArrayList<>();
		listPorduto = daoProdutos.listar(ProdutosDBM.class, "status is true and codBarra = '" + codigoProduto + "'");

		if (listPorduto.size() == 0) {
			ExibirMensagem.exibirMensagem(Mensagem.PRODUTOCADASTRO);
			someTexto = true;
		} else {
			itensVendaProduto = new ItensVendaProdutoDBM();
			produtoAdd = listPorduto.get(0);

			itensVendaProduto.setLucroUnitario(produtoAdd.getValorCompra().subtract(produtoAdd.getValorVenda()));
			itensVendaProduto.setProduto(produtoAdd);
			itensVendaProduto.setQuantidade(quantidadeProduto);
			itensVendaProduto.setStatus(true);
			itensVendaProduto.setValorUnitario(produtoAdd.getValorVenda());
			itensVendaProduto.setVenda(buscaVenda);

			itensProdutoService.inserirAlterar(itensVendaProduto);

			// buscarVenda();
		}
	}

	public void ativaValor2() {

		FecharDialog.fecharDialogPreco2();
		RequestContext.getCurrentInstance().execute("$(function(){PrimeFaces.focus('dadosProduto:porct');});");

	}

	// método executado assim que entra na classe, ele é responsável por
	// verificar se o cara logado está com o caixa aberto ou não
	public void verificaAbertura() {
		String dataFormatada = pegaDataFormatada();
		aberturaVerificada = new AberturaDBM();
		List<AberturaDBM> listAbertura = new ArrayList<>();
		// listAbertura = daoAbertura.listar(Abertura.class, "dataAbertura = '"
		// + dataFormatada
		// + "' and situacao = 'aberto' and usuario ='" + usuario.getId() +
		// "'");**

		listAbertura = daoAbertura.listar(AberturaDBM.class,
				" situacao = 'aberto' and usuario ='" + usuario.getId() + "'");

		if (listAbertura.size() == 0) {
			FecharDialog.abrirDialogCaixa();
		} else {
			aberturaVerificada = listAbertura.get(0);
		}

	}

	// método rponsável por adicionar o produto na lista
	public void addProdutosItens() {
		String dataFormatada = pegaDataFormatada();

		if (codigoProduto.equals("101020")) {
			valorDiversos = null;
			FecharDialog.abrirDlgDiversos();
			RequestContext.getCurrentInstance()
					.execute("$(function(){PrimeFaces.focus('frmDiversos:inputValorDiversos');});");
		} else if (codigoProduto.equals("f")) {
			codigoProduto = "";
			subTotal = valorTotalItens;
			flag = 1;

			FecharDialog.abrirDlgFechaVenda();
			// subTotal = valorTotalItens;
			RequestContext.getCurrentInstance()
					.execute("$(function(){PrimeFaces.focus('frmFechaVenda:inputDinheiro');});");

			dinheiro = null;
			troco = null;
		} else if (codigoProduto.equals("r")) {
			codigoProduto = "";
			FecharDialog.abrirDlgRemoveItem();
			RequestContext.getCurrentInstance()
					.execute("$(function(){PrimeFaces.focus('frmRemoveItem:inputCodigo');});");
		} else if (codigoProduto.equals("q")) {
			codigoProduto = "";
			quantidadeProduto = null;
			RequestContext.getCurrentInstance().execute("$(function(){PrimeFaces.focus('dadosProduto:quantidade');});");
		}

		else {

			if (aberturaVerificada.getDataAbertura().equals(dataFormatada) == false) {
				ExibirMensagem.exibirMensagem(Mensagem.DATAVENDA + aberturaVerificada.getDataAbertura()
						+ ", realizer fechamento/abertura de caixa para normalizar a data de venda");

			}

			// tem que pegar a data q ta vendendo, tem que verificar o troco "ta
			// meio bugado ", tem que arrumar o falta "para poder sumir"
			dataVendaSetada = aberturaVerificada.getDataAbertura();

			codigoItem += 1;
			ProdutosDBM produtoAdd = new ProdutosDBM();
			List<ProdutosDBM> listPorduto = new ArrayList<>();
			listPorduto = daoProdutos.listar(ProdutosDBM.class,
					"status is true and codBarra = '" + codigoProduto + "'");

			if (listPorduto.size() == 0) {

				URL url = getClass().getResource("alarme.wav");
				AudioClip audio = Applet.newAudioClip(url);
				audio.play();
				ExibirMensagem.exibirMensagem(Mensagem.PRODUTOCADASTRO);
				// Toolkit.getDefaultToolkit().beep();

				someTexto = true;
			} else {
				itensVendaProduto = new ItensVendaProdutoDBM();

				produtoAdd = listPorduto.get(0);

				itensVendaProduto.setCodigoItem(codigoItem);
				itensVendaProduto.setProduto(produtoAdd);
				itensVendaProduto.setQuantidade(quantidadeProduto);
				itensVendaProduto.setStatus(true);

				if (ativaValor2) {
					itensVendaProduto.setValorUnitario(produtoAdd.getPreco2());
					BigDecimal quantidadeItem = new BigDecimal(itensVendaProduto.getQuantidade());
					BigDecimal valorSubTotal = itensVendaProduto.getValorUnitario().multiply(quantidadeItem);
					itensVendaProduto.setSubTotalItem(valorSubTotal);
					itensVendaProduto.setLucroUnitario(
							quantidadeItem.multiply(produtoAdd.getPreco2().subtract(produtoAdd.getValorCompra())));

				} else {
					itensVendaProduto.setValorUnitario(produtoAdd.getValorVenda());
					BigDecimal quantidadeItem = new BigDecimal(itensVendaProduto.getQuantidade());
					BigDecimal valorSubTotal = itensVendaProduto.getValorUnitario().multiply(quantidadeItem);
					itensVendaProduto.setSubTotalItem(valorSubTotal);
					itensVendaProduto.setLucroUnitario(
							quantidadeItem.multiply(produtoAdd.getValorVenda().subtract(produtoAdd.getValorCompra())));

				}
				listItensVendaProduto.add(itensVendaProduto);
				BigDecimal quantidadeItem = new BigDecimal(itensVendaProduto.getQuantidade());
				BigDecimal valorSubTotal = itensVendaProduto.getValorUnitario().multiply(quantidadeItem);
				calculaSomaItens(valorSubTotal);

			}
			codigoProduto = "";
			quantidadeProduto = 1;

		}
	}

	public void adicionaDiversos() {
		String dataFormatada = pegaDataFormatada();

		if (aberturaVerificada.getDataAbertura().equals(dataFormatada) == false) {
			ExibirMensagem.exibirMensagem(Mensagem.DATAVENDA + aberturaVerificada.getDataAbertura()
					+ ", realizer fechamento/abertura de caixa para normalizar a data de venda");

		}

		// tem que pegar a data q ta vendendo, tem que verificar o troco "ta
		// meio bugado ", tem que arrumar o falta "para poder sumir"
		dataVendaSetada = aberturaVerificada.getDataAbertura();

		codigoItem += 1;
		ProdutosDBM produtoAdd = new ProdutosDBM();
		List<ProdutosDBM> listPorduto = new ArrayList<>();
		listPorduto = daoProdutos.listar(ProdutosDBM.class, "status is true and codBarra = '" + codigoProduto + "'");

		if (listPorduto.size() == 0) {
			ExibirMensagem.exibirMensagem(Mensagem.PRODUTOCADASTRO);
			someTexto = true;
		} else {
			itensVendaProduto = new ItensVendaProdutoDBM();

			produtoAdd = listPorduto.get(0);

			itensVendaProduto.setCodigoItem(codigoItem);
			itensVendaProduto.setProduto(produtoAdd);
			itensVendaProduto.setQuantidade(1);
			itensVendaProduto.setStatus(true);

			BigDecimal arredondadoValorItem = valorDiversos.multiply(new BigDecimal(0.01), MathContext.DECIMAL32);
			itensVendaProduto
					.setValorUnitario(arredondadoValorItem.divide(BigDecimal.ONE, 2, BigDecimal.ROUND_HALF_UP));

			BigDecimal lucroArredondado = itensVendaProduto.getValorUnitario().multiply(new BigDecimal(25),
					MathContext.DECIMAL32);
			BigDecimal divisaoArredondamento = lucroArredondado.divide(new BigDecimal(100));
			itensVendaProduto
					.setLucroUnitario(divisaoArredondamento.divide(BigDecimal.ONE, 2, BigDecimal.ROUND_HALF_UP));

			itensVendaProduto.setSubTotalItem(itensVendaProduto.getValorUnitario());
			System.out.println("sub tot " + itensVendaProduto.getSubTotalItem());
			listItensVendaProduto.add(itensVendaProduto);
			calculaSomaItens(itensVendaProduto.getValorUnitario());
			FecharDialog.fecharDlgDiversos();
			RequestContext.getCurrentInstance().execute("$(function(){PrimeFaces.focus('dadosProduto:porct');});");

		}
		codigoProduto = "";
		quantidadeProduto = 1;
	}

	// metodo responsável por calcular o valor da compra
	public void calculaSomaItens(BigDecimal valorItem) {
		valorTotalItens = valorTotalItens.add(valorItem);
		System.out.println("valor somado " + valorTotalItens);
	}

	public void consultaProdutos() {

		listProduto = new ArrayList<>();
		listProduto = daoProdutos.listar(ProdutosDBM.class, "status is true and codBarra = '" + codigoProduto + "'");

		codigoProduto = null;
		if (listProduto.size() == 0) {
			ExibirMensagem.exibirMensagem(Mensagem.PRODUTOCADASTRO);
		}
	}

	// método responsável por abrir o caixa
	public void abrirCaixa() {

		String dataFormatada = pegaDataFormatada();
		aberturaVerificada = new AberturaDBM();
		char dataMes1 = dataFormatada.charAt(3);
		char dataMes2 = dataFormatada.charAt(4);
		char dataAno1 = dataFormatada.charAt(6);
		char dataAno2 = dataFormatada.charAt(7);
		char dataAno3 = dataFormatada.charAt(8);
		char dataAno4 = dataFormatada.charAt(9);

		abertura.setMes(dataMes1 + "" + dataMes2);
		abertura.setAno(dataAno1 + "" + dataAno2 + "" + dataAno3 + "" + dataAno4);
		abertura.setStatus(true);
		abertura.setSituacao("aberto");

		abertura.setDataAbertura(dataFormatada);
		abertura.setUsuario(usuario);

		aberturaService.inserirAlterar(abertura);

		aberturaVerificada = abertura;

		RequestContext.getCurrentInstance().execute("$(function(){PrimeFaces.focus('dadosProduto:porct');});");

		FecharDialog.fecharDialogCaixa();
		// verificarVenda();
	}

	public String pegaDataFormatada() {
		DateFormat formata = new SimpleDateFormat("dd/MM/yyyy");

		return formata.format(new Date());
	}

	public UsuarioDBM getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDBM usuario) {
		this.usuario = usuario;
	}

	public AberturaDBM getAbertura() {
		return abertura;
	}

	public void setAbertura(AberturaDBM abertura) {
		this.abertura = abertura;
	}

	public String getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(String codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	public Integer getQuantidadeProduto() {
		return quantidadeProduto;
	}

	public void setQuantidadeProduto(Integer quantidadeProduto) {
		this.quantidadeProduto = quantidadeProduto;
	}

	public boolean isSomeTexto() {
		return someTexto;
	}

	public void setSomeTexto(boolean someTexto) {
		this.someTexto = someTexto;
	}

	public List<ItensVendaProdutoDBM> getListItensVendaProduto() {
		return listItensVendaProduto;
	}

	public void setListItensVendaProduto(List<ItensVendaProdutoDBM> listItensVendaProduto) {
		this.listItensVendaProduto = listItensVendaProduto;
	}

	public BigDecimal getValorTotalItens() {
		return valorTotalItens;
	}

	public void setValorTotalItens(BigDecimal valorTotalItens) {
		this.valorTotalItens = valorTotalItens;
	}

	public Integer getCodigoItemRemove() {
		return codigoItemRemove;
	}

	public void setCodigoItemRemove(Integer codigoItemRemove) {
		this.codigoItemRemove = codigoItemRemove;
	}

	public BigDecimal getDinheiro() {
		return dinheiro;
	}

	public void setDinheiro(BigDecimal dinheiro) {
		this.dinheiro = dinheiro;
	}

	public BigDecimal getTroco() {
		return troco;
	}

	public void setTroco(BigDecimal troco) {
		this.troco = troco;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public boolean isEscondeValor() {
		return escondeValor;
	}

	public void setEscondeValor(boolean escondeValor) {
		this.escondeValor = escondeValor;
	}

	public List<ProdutosDBM> getListProduto() {
		return listProduto;
	}

	public void setListProduto(List<ProdutosDBM> listProduto) {
		this.listProduto = listProduto;
	}

	public boolean isAtivaValor2() {
		return ativaValor2;
	}

	public void setAtivaValor2(boolean ativaValor2) {
		this.ativaValor2 = ativaValor2;
	}

	public BigDecimal getValorDiversos() {
		return valorDiversos;
	}

	public void setValorDiversos(BigDecimal valorDiversos) {
		this.valorDiversos = valorDiversos;
	}

}
