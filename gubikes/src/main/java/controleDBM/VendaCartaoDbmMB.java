package controleDBM;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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
import modeloConvenienciaGuinnesBeer.Cartao;
import modeloConvenienciaGuinnesBeer.ItensVendaProdutoCartao;
import modeloDBM.AberturaDBM;
import modeloDBM.CartaoDBM;
import modeloDBM.ConfigDBM;
import modeloDBM.ItensVendaProdutoDBM;
import modeloDBM.ItensVendaProdutoCartaoDBM;
import modeloDBM.PermissaoDBM;
import modeloDBM.ProdutosDBM;
import modeloDBM.TipoDBM;
import modeloDBM.UsuarioDBM;
import modeloDBM.VendasDBM;
import modeloDBM.VendasCartaoDBM;
import serviceDBM.AberturaService;
import serviceDBM.CartaoService;
import serviceDBM.ConfigService;
import serviceDBM.FuncionarioService;
import serviceDBM.ItensProdutoCartaoService;
import serviceDBM.ItensProdutoService;
import serviceDBM.PermissaoService;
import serviceDBM.VendaCartaoService;
import serviceDBM.VendaService;

@ViewScoped
@Named("vendaCartaoDbmMB")
public class VendaCartaoDbmMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private AberturaDBM abertura;
	private UsuarioDBM usuario;
	private VendasDBM venda;
	private VendasCartaoDBM vendaCartao;
	private CartaoDBM cartao;
	private ItensVendaProdutoDBM itensVendaProduto;
	private ItensVendaProdutoCartaoDBM itensVendaProdutoCartao;
	private List<ItensVendaProdutoDBM> listItensVendaProduto;
	private List<ItensVendaProdutoCartaoDBM> listItensVendaProdutoCartao;
	private ConfigDBM config;
	private String codigoProduto;
	private String codigoCartao;
	private List<ProdutosDBM> listProduto;
	private List<CartaoDBM> listCartao;
	private VendasDBM buscaVenda;
	private Integer quantidadeProduto;
	private boolean someTexto = false;
	private Integer codigoItem = 0;
	private Integer codigoItemCartao = 0;
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
	private BigDecimal valorTotalDisponivel = new BigDecimal(0);
	private boolean escondeValor = false;
	private boolean ativaValor2 = false;
	private AberturaDBM aberturaVerificada;
	private String dataVendaSetada;
	private int flag = 1;
	private int outroValor = 0;
	private BigDecimal valorTotalVendas = new BigDecimal(0);
	private String obsercacao;
	private BigDecimal valorVendaTotalAtribuido;
	private List<String> listaString = new ArrayList<>();
	private int qtdeCartao;
	private BigDecimal valorVendidoNoMes = new BigDecimal(0);
	private BigDecimal valorVendidoTotal = new BigDecimal(0);
	private BigDecimal valorVendidoNoAno = new BigDecimal(0);

	private BigDecimal valorRecebidoNoMes = new BigDecimal(0);

	private BigDecimal valorRecebidoNoAno = new BigDecimal(0);

	@Inject
	private GenericDAO<AberturaDBM> daoAbertura;

	@Inject
	private GenericDAO<UsuarioDBM> daoUsuario;

	@Inject
	private GenericDAO<ConfigDBM> daoConfigDBM;

	@Inject
	private GenericDAO<CartaoDBM> daoCartao;

	@Inject
	private GenericDAO<VendasDBM> daoVenda;

	@Inject
	private GenericDAO<ItensVendaProdutoDBM> daoItensVendaProduto;

	@Inject
	private GenericDAO<ProdutosDBM> daoProdutos;

	@Inject
	private GenericDAO<ItensVendaProdutoCartaoDBM> daoItensVendaProdutoCartao;

	@Inject
	private GenericDAO<VendasCartaoDBM> daoVendaCartao;

	@Inject
	private AberturaService aberturaService;

	@Inject
	private VendaService vendaService;

	@Inject
	private VendaCartaoService vendaCartaoService;

	@Inject
	private ItensProdutoService itensProdutoService;

	@Inject
	private ItensProdutoCartaoService itensProdutoCartaoService;

	@Inject
	private CartaoService cartaoService;

	@Inject
	private ConfigService configService;

	@Inject
	private UsuarioSessaoDbmMB usuarioSessao;

	@PostConstruct
	public void inicializar() {

		venda = new VendasDBM();
		usuario = new UsuarioDBM();
		usuario = usuarioSessao.recuperarUsuario();
		cartao = new CartaoDBM();
		config = new ConfigDBM();
		abertura = new AberturaDBM();
		verificaAbertura();
		quantidadeProduto = 1;
		listItensVendaProduto = new ArrayList<>();
		RequestContext.getCurrentInstance().execute("$(function(){PrimeFaces.focus('frmCodigoCartao:codBrr');});");
		buscaCartao();
		listItensVendaProdutoCartao = new ArrayList<>();
		buscaCartao();
		obsercacao = "";

	}

	public void voltar() {

		try {
			if (listItensVendaProdutoCartao.size() > 0) {
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

	public void inserirVendaCartao() {

		List<CartaoDBM> listCartao = new ArrayList<>();
		listCartao = daoCartao.listarCodicaoLivre(CartaoDBM.class,
				"situacao = 'aberto' and codigoBarra = '" + cartao.getCodigoBarra() + "'");
		if (listCartao.size() > 0) {
			ExibirMensagem.exibirMensagem(Mensagem.VINCULOCARTAOCART);
		} else {

			List<ConfigDBM> listConfig = new ArrayList<>();
			listConfig.clear();
			listConfig = daoConfigDBM.listarCodicaoLivre(ConfigDBM.class, " status is true");

			if (listConfig.size() > 0) {

				if (valorTotalVendas.add(cartao.getSaldoDisponibilizado()).doubleValue() > listConfig.get(0)
						.getValorMaximo().doubleValue()) {
					ExibirMensagem.exibirMensagem(Mensagem.LIMITEULTRAPASSADO);
				} else {

					String dataFormatada = pegaDataFormatada();
					String ano = retrornaAno();
					String mes = retrornaMes();
					cartao.setStatus(true);
					cartao.setSaldo(cartao.getSaldoDisponibilizado());
					cartao.setDataVenda(dataFormatada);
					cartao.setSituacao("aberto");

					cartaoService.inserirAlterar(cartao);

					VendasCartaoDBM vendaCartao = new VendasCartaoDBM();
					vendaCartao.setCartao(cartao);
					vendaCartao.setSituacao("aberto");
					vendaCartao.setSituacaoAbertura("aberto");
					vendaCartao.setStatus(true);

					vendaCartaoService.inserirAlterar(vendaCartao);

					buscaCartao();
					ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
					FecharDialog.fechaNovoCartao();
					cartao = new CartaoDBM();

				}
			}
		}
	}

	public void alterarVendaCartao() {

		cartao.setSaldo(cartao.getSaldoDisponibilizado().subtract(valorTotalItens));
		cartaoService.inserirAlterar(cartao);

		FecharDialog.abrirDlgFechar();
		RequestContext.getCurrentInstance().execute("$(function(){PrimeFaces.focus('frmFechaVenda:codBrr');});");
		buscaCartao();

	}

	public void salvarConfiguracoes() {

		if (config.getValorMaximo() == null)
			config.setValorMaximo(new BigDecimal(0));

		config.setStatus(true);
		configService.inserirAlterar(config);
		FecharDialog.fecharDialogLimite();

	}

	public void buscarObservacao(ItensVendaProdutoCartaoDBM cartao) {
		DateFormat formata = new SimpleDateFormat("dd/MM/yyyy");

		List<VendasCartaoDBM> listVendas = new ArrayList<>();
		listVendas = daoVendaCartao.listarCadastro(VendasCartaoDBM.class,
				" status = true and parcelado = 'Parcial' and cartao = '" + cartao.getVendasCartao().getCartao().getId()
						+ "'");
		listaString.clear();

		if (listVendas.size() > 0) {

			for (VendasCartaoDBM v : listVendas) {

				listaString.add(" Valor Total = " + v.getValorTotalAntigo() + " - Valor Pago = " + v.getTotalDinheiro()
						+ " Data Pagamento " + v.getDataVenda());
			}
		}
		listaString.add(" OBS Item: " + cartao.getObservacao());
	}

	public void criarCartao() {
		cartao = new CartaoDBM();
	}

	public void buscaCartao() {
		listCartao = daoCartao.listar(CartaoDBM.class, " status is true and situacao = 'aberto'");
	}

	public void verificaTroco() {

		if (flag == 1) {

			valorVendaTotalAtribuido = valorTotalItens;
		}

		BigDecimal subtracao = dinheiro.subtract(valorVendaTotalAtribuido);

		if (subtracao.doubleValue() < 0) {

			System.out.println("sub " + valorVendaTotalAtribuido);

			subTotal = subtracao.multiply(new BigDecimal(-1));

			valorVendaTotalAtribuido = subTotal;

			dinheiro = null;

			RequestContext.getCurrentInstance().update("frmFechaVenda:inputDinheiro");

			RequestContext.getCurrentInstance()
					.execute("$(function(){PrimeFaces.focus('frmFechaVenda:inputDinheiro');});");
			flag = 0;

			RequestContext.getCurrentInstance().update(":frmFechaVenda:inputDinheiro");
		} else {
			troco = subtracao;
			RequestContext.getCurrentInstance().execute("$(function(){PrimeFaces.focus('frmFechaVenda:troco');});");
			flag = 1;
			// salvaItensEVenda();**

		}

	}

	public void verificaTrocoParcial() {

		if (flag == 1) {

			valorVendaTotalAtribuido = valorTotalItens;
		}

		BigDecimal subtracao = dinheiro.subtract(valorVendaTotalAtribuido);

		subTotal = subtracao.multiply(new BigDecimal(-1));

		valorVendaTotalAtribuido = subTotal;

		troco = subTotal;
		RequestContext.getCurrentInstance()
				.execute("$(function(){PrimeFaces.focus('frmFechaVendaParcial:trocoParcial');});");
		flag = 1;
		// salvaItensEVenda();**

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

			String dataFormatada = pegaDataFormatada();
			AberturaDBM aberturaCaixa = new AberturaDBM();

			List<AberturaDBM> listAbertura = new ArrayList<>();
			// listAbertura = daoAbertura.listar(Abertura.class, "dataAbertura =
			// '"
			// + dataFormatada
			// + "' and situacao = 'aberto' and usuario ='" + usuario.getId() +
			// "'");
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

			valorFechamentoTotal = valorFechamentoDinheiro.add(valorFechamentoCartao);

			aberturaCaixa.setValorFechamentoDinheiro(valorFechamentoDinheiro);
			aberturaCaixa.setValorFechamento(valorFechamentoTotal);
			aberturaCaixa.setLucroDiario(valorLucroDiario);
			aberturaCaixa.setValorFechamentoCartao(valorFechamentoCartao);
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
		String dataFormatada = pegaDataFormatada();
		String ano = retrornaAno();
		String mes = retrornaMes();
		BigDecimal valotTotalVenda = new BigDecimal(0);
		BigDecimal valotLucroVenda = new BigDecimal(0);
		vendaCartao.setDataVenda(dataFormatada);
		vendaCartao.setSituacao("fechado");
		vendaCartao.setAno(ano);
		vendaCartao.setMes(mes);
		vendaCartao.setUsuario(usuario);
		vendaCartao.setParcelado("Total");

		for (ItensVendaProdutoCartaoDBM p : listItensVendaProdutoCartao) {
			valotTotalVenda = valotTotalVenda.add(p.getSubTotalItem());
			valotLucroVenda = valotLucroVenda.add(p.getLucroUnitario());
		}
		vendaCartao.setValorTotal(valotTotalVenda);
		vendaCartao.setLucroVenda(valotLucroVenda);

		vendaCartaoService.inserirAlterar(vendaCartao);

		cartao.setSituacao("fechado");

		cartaoService.inserirAlterar(cartao);

		FecharDialog.fecharDlgFechaVenda();

		valorTotalItens = new BigDecimal(0);
		dinheiro = new BigDecimal(0);
		subTotal = new BigDecimal(0);
		troco = new BigDecimal(0);
		codigoCartao = null;

		RequestContext.getCurrentInstance().execute("$(function(){PrimeFaces.focus('frmCodigoCartao:codBrr');});");
		listItensVendaProdutoCartao = new ArrayList<>();
		buscaCartao();

	}

	public void salvaItensEVendaParcial() {

		System.out.println("dinheiro passado " + dinheiro);

		String dataFormatada = pegaDataFormatada();
		String ano = retrornaAno();
		String mes = retrornaMes();
		BigDecimal valotTotalVenda = new BigDecimal(0);
		BigDecimal valotLucroVenda = new BigDecimal(0);
		BigDecimal valorPorcentagem = new BigDecimal(0);
		BigDecimal valorFinal = new BigDecimal(0);

		BigDecimal valorPorcentagemLucro = new BigDecimal(0);
		BigDecimal valorFinalLucro = new BigDecimal(0);

		BigDecimal novoValorFinal = new BigDecimal(0);
		BigDecimal novoValorLucro = new BigDecimal(0);

		vendaCartao.setDataVenda(dataFormatada);
		vendaCartao.setSituacao("fechado");
		vendaCartao.setAno(ano);
		vendaCartao.setMes(mes);
		vendaCartao.setTotalDinheiro(dinheiro);
		vendaCartao.setUsuario(usuario);
		vendaCartao.setParcelado("Parcial");

		for (ItensVendaProdutoCartaoDBM p : listItensVendaProdutoCartao) {
			valotTotalVenda = valotTotalVenda.add(p.getSubTotalItem());
			valotLucroVenda = valotLucroVenda.add(p.getLucroUnitario());
		}

		System.out.println("total da venda " + valotTotalVenda);
		System.out.println("valor total do lucro " + valotLucroVenda);

		valorPorcentagem = valorPorcentagem.add(new BigDecimal(100).multiply(dinheiro));
		valorFinal = valorFinal.add(valorPorcentagem.divide(valotTotalVenda, 2, RoundingMode.HALF_UP)); // descobri
																										// quantos
																										// por
																										// cento

		System.out.println("porcentagem da venda " + valorFinal);

		valorPorcentagemLucro = valorPorcentagemLucro.add(valotLucroVenda.multiply(valorFinal));
		valorFinalLucro = valorFinalLucro
				.add(valorPorcentagemLucro.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP)); // aqui
																									// eu
																									// tenho
																									// o
																									// meu
																									// lucro

		System.out.println("valor do meu lucro " + valorFinalLucro);

		vendaCartao.setValorTotalAntigo(valotTotalVenda);
		vendaCartao.setValorTotal(dinheiro);
		vendaCartao.setStatus(true);
		vendaCartao.setLucroVenda(valorFinalLucro);

		vendaCartaoService.inserirAlterar(vendaCartao); // salvei os dados do
														// cartão antigo
		VendasCartaoDBM vendaCartaoNovo = new VendasCartaoDBM();

		vendaCartaoNovo.setSituacao("aberto");
		vendaCartaoNovo.setSituacaoAbertura("aberto");
		vendaCartaoNovo.setParcelado("");
		vendaCartaoNovo.setStatus(true);
		vendaCartaoNovo.setCartao(cartao);
		vendaCartaoService.inserirAlterar(vendaCartaoNovo); // agora salva o
															// novo cartão

		List<ProdutosDBM> produto = new ArrayList<>();
		produto = daoProdutos.listar(ProdutosDBM.class, " status = true and codBarra = '101020'");

		System.out.println("produto buscado " + produto.get(0).getNome());
		ItensVendaProdutoCartaoDBM itens = new ItensVendaProdutoCartaoDBM();
		itens.setCodigoItem(1);
		novoValorLucro = novoValorLucro.add(valotLucroVenda.subtract(valorFinalLucro));
		itens.setLucroUnitario(novoValorLucro);
		itens.setQuantidade(1);
		novoValorFinal = novoValorFinal.add(valotTotalVenda.subtract(dinheiro));
		itens.setSubTotalItem(novoValorFinal);
		itens.setValorUnitario(novoValorFinal);
		itens.setProduto(produto.get(0));
		itens.setVendasCartao(vendaCartaoNovo);
		itens.setStatus(true);
		itens.setDataVenda(new Date());
		itens.setObservacao("Restante, Total = " + valotTotalVenda + " menos " + dinheiro + " ficou um restante de = "
				+ novoValorFinal + " na Data de " + dataFormatada);
		itens.setSomar("nao");

		itensProdutoCartaoService.inserirAlterar(itens); // agora salva o novo
															// item

		cartao.setSituacao("aberto");
		cartao.setSaldo(cartao.getSaldo().add(dinheiro));
		cartaoService.inserirAlterar(cartao);

		FecharDialog.fecharDlgFechaVendaParcial();

		valorTotalItens = new BigDecimal(0);
		dinheiro = new BigDecimal(0);
		subTotal = new BigDecimal(0);
		troco = new BigDecimal(0);
		cartao.setSaldo(new BigDecimal(0));
		codigoCartao = null;

		RequestContext.getCurrentInstance().execute("$(function(){PrimeFaces.focus('frmCodigoCartao:codBrr');});");
		listItensVendaProdutoCartao = new ArrayList<>();
		buscaCartao();

	}

	public void removerItem() {
		ItensVendaProdutoCartaoDBM produtoRemover = new ItensVendaProdutoCartaoDBM();
		boolean verificaRemover = false;

		for (ItensVendaProdutoCartaoDBM i : listItensVendaProdutoCartao) {

			if (i.getCodigoItem().equals(codigoItemRemove)) {

				produtoRemover = i;
				verificaRemover = true;
			}
		}

		if (verificaRemover) {

			produtoRemover.setStatus(false);
			produtoRemover.setSomar("nao");
			itensProdutoCartaoService.inserirAlterar(produtoRemover);

			buscarItensVenda();
			cartao.setSaldo(produtoRemover.getSubTotalItem().add(cartao.getSaldo()));
			cartaoService.inserirAlterar(cartao);

			System.out.println("novo saldo " + produtoRemover.getVendasCartao().getCartao().getSaldo());
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

	// m?todo respons?vel por verificar se o cara logado tem alguma venda em
	// aberto ou n?o
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
			URL url = getClass().getResource("alarme.wav");
			AudioClip audio = Applet.newAudioClip(url);
			audio.play();
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

	// m?todo executado assim que entra na classe, ele ? respons?vel por
	// verificar se o cara logado est? com o caixa aberto ou n?o
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

	// m?todo rpons?vel por adicionar o produto na lista

	public void buscaCartaoVinculado() {

		if (codigoCartao.equals("v")) {
			voltar();
		} else {
			List<CartaoDBM> listCartao = new ArrayList<>();
			listCartao = daoCartao.listar(CartaoDBM.class,
					"status is true and situacao = 'aberto' and codigoBarra = '" + codigoCartao + "'");

			if (listCartao.size() == 0) {
				ExibirMensagem.exibirMensagem(Mensagem.VINCULOCARTAO);
				codigoCartao = null;
				RequestContext.getCurrentInstance()
						.execute("$(function(){PrimeFaces.focus('frmCodigoCartao:codBrr');});");
				codigoItemCartao = 0;
			} else {
				cartao = listCartao.get(0);
				vendaCartao = new VendasCartaoDBM();
				vendaCartao = daoVendaCartao.buscarCondicao(VendasCartaoDBM.class,
						"status is true and situacao = 'aberto' and cartao = '" + cartao.getId() + "'");
				RequestContext.getCurrentInstance().execute("$(function(){PrimeFaces.focus('dadosProduto:porct');});");
				buscarItensVenda();

			}
		}

	}

	public void buscarItensVenda() {
		valorTotalItens = new BigDecimal(0);
		listItensVendaProdutoCartao = daoItensVendaProdutoCartao.listar(ItensVendaProdutoCartaoDBM.class,
				" status is true and vendasCartao = '" + vendaCartao.getId() + "'");

		for (ItensVendaProdutoCartaoDBM p : listItensVendaProdutoCartao) {
			valorTotalItens = valorTotalItens.add(p.getSubTotalItem());
		}

		List<ItensVendaProdutoCartaoDBM> itens = new ArrayList<>();
		itens = daoItensVendaProdutoCartao.listarCodicaoLivre(ItensVendaProdutoCartaoDBM.class,
				" vendasCartao = '" + vendaCartao.getId() + "'");

		codigoItemCartao = itens.size();

	}

	public void addProdutosItens() {
		String dataFormatada = pegaDataFormatada();

		if (codigoProduto.equals("1010")) {
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

			outroValor = 3;
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

			ProdutosDBM produtoAdd = new ProdutosDBM();
			List<ProdutosDBM> listPorduto = new ArrayList<>();
			listPorduto = daoProdutos.listar(ProdutosDBM.class,
					"status is true and codBarra = '" + codigoProduto + "'");

			if (listPorduto.size() == 0) {
				URL url = getClass().getResource("alarme.wav");
				AudioClip audio = Applet.newAudioClip(url);
				audio.play();
				ExibirMensagem.exibirMensagem(Mensagem.PRODUTOCADASTRO);
				someTexto = true;
			} else {
				codigoItem += 1;
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

	public void addProdutosItensCartao() {
		String dataFormatada = pegaDataFormatada();

		if (codigoProduto.equals("101020")) {
			valorDiversos = null;
			FecharDialog.abrirDlgDiversos();
			RequestContext.getCurrentInstance()
					.execute("$(function(){PrimeFaces.focus('frmDiversos:inputValorDiversos');});");
		} else if (codigoProduto.equals("f")) {
			codigoProduto = "";
			flag = 1;
			subTotal = valorTotalItens;
			flag = 1;
			FecharDialog.abrirDlgFechaVenda();
			flag = 1;
			RequestContext.getCurrentInstance()
					.execute("$(function(){PrimeFaces.focus('frmFechaVenda:inputDinheiro');});");
			flag = 1;
			dinheiro = null;
			flag = 1;
			troco = null;
			flag = 1;
		} else if (codigoProduto.equals("p")) {
			codigoProduto = "";
			flag = 1;
			subTotal = valorTotalItens;
			flag = 1;
			FecharDialog.abrirDlgFechaVendaParcial();
			flag = 1;
			RequestContext.getCurrentInstance()
					.execute("$(function(){PrimeFaces.focus('frmFechaVendaParcial:inputDinheiroParcial');});");
			flag = 1;
			dinheiro = null;
			flag = 1;
			troco = null;
			flag = 1;
		} else if (codigoProduto.equals("r")) {
			codigoProduto = "";
			FecharDialog.abrirDlgRemoveItem();
			RequestContext.getCurrentInstance()
					.execute("$(function(){PrimeFaces.focus('frmRemoveItem:inputCodigo');});");
		} else if (codigoProduto.equals("s")) {
			codigoProduto = "";
			codigoCartao = "";
			valorTotalItens = new BigDecimal(0);
			RequestContext.getCurrentInstance().update(":dadosProduto :tabelaProdutos");
			RequestContext.getCurrentInstance().update(":frmCodigoCartao");
			RequestContext.getCurrentInstance().execute("$(function(){PrimeFaces.focus('frmCodigoCartao:codBrr');});");
			listItensVendaProdutoCartao = new ArrayList<>();

		} else if (codigoProduto.equals("q")) {
			codigoProduto = "";
			quantidadeProduto = null;
			RequestContext.getCurrentInstance().execute("$(function(){PrimeFaces.focus('dadosProduto:quantidade');});");
		} else if (codigoProduto.equals("a")) {
			codigoProduto = "";
			FecharDialog.abrirDlgAlterarCartao();
			RequestContext.getCurrentInstance()
					.execute("$(function(){PrimeFaces.focus('frmCataoAlterar:nomeClientAltera');});");
		}

		else if (codigoProduto.equals("i")) {

			// try{

			codigoProduto = "";

			List<CartaoDBM> listVendasCartao = new ArrayList<>();
			listVendasCartao = daoCartao.listar(CartaoDBM.class, " situacao = 'aberto'");
			valorTotalVendas = new BigDecimal(0);
			valorTotalDisponivel = new BigDecimal(0);

			for (CartaoDBM v : listVendasCartao) {

				if (v.getSaldoDisponibilizado() == null) {
					v.setSaldoDisponibilizado(new BigDecimal(500));
				}

				valorTotalVendas = valorTotalVendas.add(v.getSaldoDisponibilizado());

			}

			List<ConfigDBM> listConfig = new ArrayList<>();
			listConfig = daoConfigDBM.listarCodicaoLivre(ConfigDBM.class, " status is true");
			if (listConfig.size() > 0) {

				valorTotalDisponivel = valorTotalDisponivel
						.add(listConfig.get(0).getValorMaximo().subtract(valorTotalVendas));

				config = listConfig.get(0);
			}

			FecharDialog.abrirDlgLimites();

		} else if (codigoProduto.equals("v")) {

			codigoProduto = "";
			
			try{
				
			String dataFormatadaDados = pegaDataFormatada();
			valorVendidoNoMes = new BigDecimal(0);
			valorVendidoNoAno = new BigDecimal(0);
			valorRecebidoNoMes = new BigDecimal(0);
			valorRecebidoNoAno = new BigDecimal(0);

			String dataAnoConferir = dataFormatadaDados.charAt(6) + "" + dataFormatadaDados.charAt(7) + ""
					+ dataFormatadaDados.charAt(8) + "" + dataFormatadaDados.charAt(9);
			String dataMesConferir = dataFormatadaDados.charAt(3) + "" + dataFormatadaDados.charAt(4);

			List<ItensVendaProdutoCartaoDBM> listItensVendasCartao2 = new ArrayList<>();
			listItensVendasCartao2 = daoItensVendaProdutoCartao.listarCodicaoLivre(ItensVendaProdutoCartaoDBM.class,
					" status is true ");

			for (ItensVendaProdutoCartaoDBM ss : listItensVendasCartao2) {
				System.out.println("ss.getS " + ss.getSomar());
				if (ss.getSomar() == null) {
					ss.setSomar("sim");
					itensProdutoCartaoService.inserirAlterar(ss);
				}
			}

			List<ItensVendaProdutoCartaoDBM> listItensVendasCartao3 = new ArrayList<>();
			listItensVendasCartao3 = daoItensVendaProdutoCartao.listarCodicaoLivre(ItensVendaProdutoCartaoDBM.class,
					" status is false ");

			for (ItensVendaProdutoCartaoDBM ss : listItensVendasCartao3) {
				if (ss.getSomar() == null) {
					ss.setSomar("nao");
					itensProdutoCartaoService.inserirAlterar(ss);
				}
			}
			
			List<VendasCartaoDBM> listVendasCartaoSomas = new ArrayList<>();
			listVendasCartaoSomas = daoVendaCartao.listar(VendasCartaoDBM.class, " situacao = 'aberto'");

			List<ItensVendaProdutoCartaoDBM> listItensVendasCartao = new ArrayList<>();
			
			for(VendasCartaoDBM vs: listVendasCartaoSomas){
				
				listItensVendasCartao.clear();
				listItensVendasCartao = daoItensVendaProdutoCartao.listarCodicaoLivre(ItensVendaProdutoCartaoDBM.class,
						" status is true and vendasCartao = '"+vs.getId()+"'");
				
				for(ItensVendaProdutoCartaoDBM s : listItensVendasCartao){
					
					

					valorVendidoTotal = valorVendidoTotal.add(s.getSubTotalItem());
					
					String dataMes = String.valueOf(s.getDataVenda()).charAt(5) + ""
							+ String.valueOf(s.getDataVenda()).charAt(6);
					String dataAno = String.valueOf(s.getDataVenda()).charAt(0) + ""
							+ String.valueOf(s.getDataVenda()).charAt(1) + ""
							+ String.valueOf(s.getDataVenda()).charAt(2) + ""
							+ String.valueOf(s.getDataVenda()).charAt(3);

					if (dataMes.equals(dataMesConferir)) {

						valorVendidoNoMes = valorVendidoNoMes.add(s.getSubTotalItem());
					}

					if (dataAno.equals(dataAnoConferir)) {
						valorVendidoNoAno = valorVendidoNoAno.add(s.getSubTotalItem());
					}
					
					
				}
				 
				
			}
			
			

			/*List<ItensVendaProdutoCartaoDBM> listItensVendasCartao = new ArrayList<>();
			listItensVendasCartao = daoItensVendaProdutoCartao.listarCodicaoLivre(ItensVendaProdutoCartaoDBM.class,
					" status is true");

			for (ItensVendaProdutoCartaoDBM s : listItensVendasCartao) {

				if (s.getSomar().equals("sim")) {

					if (s.getDataVenda() == null) {
						s.setDataVenda(new Date());
						itensProdutoCartaoService.inserirAlterar(s);
					}

					String dataMes = String.valueOf(s.getDataVenda()).charAt(5) + ""
							+ String.valueOf(s.getDataVenda()).charAt(6);
					String dataAno = String.valueOf(s.getDataVenda()).charAt(0) + ""
							+ String.valueOf(s.getDataVenda()).charAt(1) + ""
							+ String.valueOf(s.getDataVenda()).charAt(2) + ""
							+ String.valueOf(s.getDataVenda()).charAt(3);

					if (dataMes.equals(dataMesConferir)) {

						valorVendidoNoMes = valorVendidoNoMes.add(s.getSubTotalItem());
					}

					if (dataAno.equals(dataAnoConferir)) {
						valorVendidoNoAno = valorVendidoNoAno.add(s.getSubTotalItem());
					}

				}

			}*/

			List<VendasCartaoDBM> listVendasCartaoDBMAno = new ArrayList<>();
			listVendasCartaoDBMAno = daoVendaCartao.listar(VendasCartaoDBM.class, " ano = '" + dataAnoConferir + "'");

			List<VendasCartaoDBM> listVendasCartaoDBMMes = new ArrayList<>();
			listVendasCartaoDBMMes = daoVendaCartao.listar(VendasCartaoDBM.class, " mes = '" + dataMesConferir + "'");

			for (VendasCartaoDBM s : listVendasCartaoDBMMes) {

				valorRecebidoNoMes = valorRecebidoNoMes.add(s.getValorTotal());

			}

			for (VendasCartaoDBM s : listVendasCartaoDBMAno) {

				valorRecebidoNoAno = valorRecebidoNoAno.add(s.getValorTotal());

			}

			List<CartaoDBM> listVendasCartao = new ArrayList<>();
			listVendasCartao = daoCartao.listar(CartaoDBM.class, " situacao = 'aberto'");
			qtdeCartao = listVendasCartao.size();

			FecharDialog.abrirDlgValores();
			

			}catch (Exception e) {
				ExibirMensagem.exibirMensagem(""+e);
			}

		}

		else {

			ProdutosDBM produtoAdd = new ProdutosDBM();
			List<ProdutosDBM> listPorduto = new ArrayList<>();
			listPorduto = daoProdutos.listar(ProdutosDBM.class,
					"status is true and codBarra = '" + codigoProduto + "'");

			if (listPorduto.size() == 0) {
				URL url = getClass().getResource("alarme.wav");
				AudioClip audio = Applet.newAudioClip(url);
				audio.play();
				ExibirMensagem.exibirMensagem(Mensagem.PRODUTOCADASTRO);

			} else {

				codigoItemCartao += 1;
				itensVendaProdutoCartao = new ItensVendaProdutoCartaoDBM();

				produtoAdd = listPorduto.get(0);

				BigDecimal valorSaldo = new BigDecimal(0);

				BigDecimal quantidadeItemVerificar = new BigDecimal(quantidadeProduto);
				BigDecimal valorSubTotalVerificar = produtoAdd.getValorCompra().multiply(quantidadeItemVerificar);

				if (valorSubTotalVerificar.doubleValue() > vendaCartao.getCartao().getSaldo().doubleValue()) {

					ExibirMensagem.exibirMensagem("saldo insuficiente, você possui um saldo de "
							+ vendaCartao.getCartao().getSaldo().doubleValue());
				} else {

					itensVendaProdutoCartao.setCodigoItem(codigoItemCartao);
					itensVendaProdutoCartao.setObservacao(obsercacao);
					itensVendaProdutoCartao.setProduto(produtoAdd);
					itensVendaProdutoCartao.setDataVenda(new Date());
					itensVendaProdutoCartao.setQuantidade(quantidadeProduto);
					itensVendaProdutoCartao.setStatus(true);
					itensVendaProdutoCartao.setSomar("sim");
					itensVendaProdutoCartao.setVendasCartao(vendaCartao);

					if (ativaValor2) {
						itensVendaProdutoCartao.setValorUnitario(produtoAdd.getPreco2());
						BigDecimal quantidadeItem = new BigDecimal(itensVendaProdutoCartao.getQuantidade());
						BigDecimal valorSubTotal = itensVendaProdutoCartao.getValorUnitario().multiply(quantidadeItem);
						itensVendaProdutoCartao.setSubTotalItem(valorSubTotal);
						itensVendaProdutoCartao.setLucroUnitario(
								quantidadeItem.multiply(produtoAdd.getPreco2().subtract(produtoAdd.getValorCompra())));

					} else {
						itensVendaProdutoCartao.setValorUnitario(produtoAdd.getValorVenda());
						BigDecimal quantidadeItem = new BigDecimal(itensVendaProdutoCartao.getQuantidade());
						BigDecimal valorSubTotal = itensVendaProdutoCartao.getValorUnitario().multiply(quantidadeItem);
						itensVendaProdutoCartao.setSubTotalItem(valorSubTotal);
						itensVendaProdutoCartao.setLucroUnitario(quantidadeItem
								.multiply(produtoAdd.getValorVenda().subtract(produtoAdd.getValorCompra())));

					}

					itensProdutoCartaoService.inserirAlterar(itensVendaProdutoCartao);

					BigDecimal quantidadeItem = new BigDecimal(itensVendaProdutoCartao.getQuantidade());
					BigDecimal valorSubTotal = itensVendaProdutoCartao.getValorUnitario().multiply(quantidadeItem);

					cartao.setSaldo(cartao.getSaldo().subtract(valorSubTotal));
					cartaoService.inserirAlterar(cartao);

					calculaSomaItens(valorSubTotal);

				}
				codigoProduto = "";
				obsercacao = "";
				quantidadeProduto = 1;
				buscarItensVenda();
			}
		}
	}

	public void adicionaDiversos() {
		String dataFormatada = pegaDataFormatada();

		codigoItemCartao += 1;
		ProdutosDBM produtoAdd = new ProdutosDBM();
		List<ProdutosDBM> listPorduto = new ArrayList<>();
		listPorduto = daoProdutos.listar(ProdutosDBM.class, "status is true and codBarra = '" + codigoProduto + "'");

		if (listPorduto.size() == 0) {
			URL url = getClass().getResource("alarme.wav");
			AudioClip audio = Applet.newAudioClip(url);
			audio.play();
			ExibirMensagem.exibirMensagem(Mensagem.PRODUTOCADASTRO);
			someTexto = true;
		} else {
			itensVendaProdutoCartao = new ItensVendaProdutoCartaoDBM();

			produtoAdd = listPorduto.get(0);

			itensVendaProdutoCartao.setCodigoItem(codigoItemCartao);
			itensVendaProdutoCartao.setObservacao(obsercacao);
			itensVendaProdutoCartao.setProduto(produtoAdd);
			itensVendaProdutoCartao.setDataVenda(new Date());
			itensVendaProdutoCartao.setQuantidade(1);
			itensVendaProdutoCartao.setStatus(true);
			itensVendaProdutoCartao.setSomar("sim");
			itensVendaProdutoCartao.setVendasCartao(vendaCartao);

			BigDecimal arredondadoValorItem = valorDiversos.multiply(new BigDecimal(0.01), MathContext.DECIMAL32);
			itensVendaProdutoCartao
					.setValorUnitario(arredondadoValorItem.divide(BigDecimal.ONE, 2, BigDecimal.ROUND_HALF_UP));

			BigDecimal lucroArredondado = itensVendaProdutoCartao.getValorUnitario().multiply(new BigDecimal(25),
					MathContext.DECIMAL32);
			BigDecimal divisaoArredondamento = lucroArredondado.divide(new BigDecimal(100));
			itensVendaProdutoCartao
					.setLucroUnitario(divisaoArredondamento.divide(BigDecimal.ONE, 2, BigDecimal.ROUND_HALF_UP));

			itensVendaProdutoCartao.setSubTotalItem(itensVendaProdutoCartao.getValorUnitario());

			cartao.setSaldo(cartao.getSaldo().subtract(itensVendaProdutoCartao.getSubTotalItem()));
			cartaoService.inserirAlterar(cartao);

			itensProdutoCartaoService.inserirAlterar(itensVendaProdutoCartao);

			buscarItensVenda();

			FecharDialog.fecharDlgDiversos();
			RequestContext.getCurrentInstance().execute("$(function(){PrimeFaces.focus('dadosProduto:porct');});");

		}
		codigoProduto = "";
		obsercacao = "";
		quantidadeProduto = 1;
	}

	// metodo respons?vel por calcular o valor da compra
	public void calculaSomaItens(BigDecimal valorItem) {
		valorTotalItens = valorTotalItens.add(valorItem);

	}

	public void consultaProdutos() {

		listProduto = new ArrayList<>();
		listProduto = daoProdutos.listar(ProdutosDBM.class, "status is true and codBarra = '" + codigoProduto + "'");

		codigoProduto = null;
		if (listProduto.size() == 0) {
			URL url = getClass().getResource("alarme.wav");
			AudioClip audio = Applet.newAudioClip(url);
			audio.play();
			ExibirMensagem.exibirMensagem(Mensagem.PRODUTOCADASTRO);
		}
	}

	public String retrornaMes() {
		String dataFormatada = pegaDataFormatada();
		char dataMes1 = dataFormatada.charAt(3);
		char dataMes2 = dataFormatada.charAt(4);
		return dataMes1 + "" + dataMes2;
	}

	public String retrornaAno() {
		String dataFormatada = pegaDataFormatada();
		char dataAno1 = dataFormatada.charAt(6);
		char dataAno2 = dataFormatada.charAt(7);
		char dataAno3 = dataFormatada.charAt(8);
		char dataAno4 = dataFormatada.charAt(9);

		return dataAno1 + "" + dataAno2 + "" + dataAno3 + "" + dataAno4;

	}

	// m?todo respons?vel por abrir o caixa
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

	public BigDecimal getValorTotalVendas() {
		return valorTotalVendas;
	}

	public void setValorTotalVendas(BigDecimal valorTotalVendas) {
		this.valorTotalVendas = valorTotalVendas;
	}

	public Integer getQuantidadeProduto() {
		return quantidadeProduto;
	}

	public void setQuantidadeProduto(Integer quantidadeProduto) {
		this.quantidadeProduto = quantidadeProduto;
	}

	public List<String> getListaString() {
		return listaString;
	}

	public void setListaString(List<String> listaString) {
		this.listaString = listaString;
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

	public CartaoDBM getCartao() {
		return cartao;
	}

	public void setCartao(CartaoDBM cartao) {
		this.cartao = cartao;
	}

	public List<CartaoDBM> getListCartao() {
		return listCartao;
	}

	public void setListCartao(List<CartaoDBM> listCartao) {
		this.listCartao = listCartao;
	}

	public int getQtdeCartao() {
		return qtdeCartao;
	}

	public void setQtdeCartao(int qtdeCartao) {
		this.qtdeCartao = qtdeCartao;
	}

	public BigDecimal getValorVendidoNoMes() {
		return valorVendidoNoMes;
	}

	public void setValorVendidoNoMes(BigDecimal valorVendidoNoMes) {
		this.valorVendidoNoMes = valorVendidoNoMes;
	}

	public BigDecimal getValorVendidoNoAno() {
		return valorVendidoNoAno;
	}

	public void setValorVendidoNoAno(BigDecimal valorVendidoNoAno) {
		this.valorVendidoNoAno = valorVendidoNoAno;
	}

	public String getCodigoCartao() {
		return codigoCartao;
	}

	public void setCodigoCartao(String codigoCartao) {
		this.codigoCartao = codigoCartao;
	}

	public ConfigDBM getConfig() {
		return config;
	}

	public void setConfig(ConfigDBM config) {
		this.config = config;
	}

	public String getObsercacao() {
		return obsercacao;
	}

	public void setObsercacao(String obsercacao) {
		this.obsercacao = obsercacao;
	}

	public List<ItensVendaProdutoCartaoDBM> getListItensVendaProdutoCartao() {
		return listItensVendaProdutoCartao;
	}

	public void setListItensVendaProdutoCartao(List<ItensVendaProdutoCartaoDBM> listItensVendaProdutoCartao) {
		this.listItensVendaProdutoCartao = listItensVendaProdutoCartao;
	}

	public BigDecimal getValorTotalDisponivel() {
		return valorTotalDisponivel;
	}

	public void setValorTotalDisponivel(BigDecimal valorTotalDisponivel) {
		this.valorTotalDisponivel = valorTotalDisponivel;
	}
	
	

	public BigDecimal getValorVendidoTotal() {
		return valorVendidoTotal;
	}

	public void setValorVendidoTotal(BigDecimal valorVendidoTotal) {
		this.valorVendidoTotal = valorVendidoTotal;
	}

	public BigDecimal getValorRecebidoNoMes() {
		return valorRecebidoNoMes;
	}

	public void setValorRecebidoNoMes(BigDecimal valorRecebidoNoMes) {
		this.valorRecebidoNoMes = valorRecebidoNoMes;
	}

	public BigDecimal getValorRecebidoNoAno() {
		return valorRecebidoNoAno;
	}

	public void setValorRecebidoNoAno(BigDecimal valorRecebidoNoAno) {
		this.valorRecebidoNoAno = valorRecebidoNoAno;
	}

}
