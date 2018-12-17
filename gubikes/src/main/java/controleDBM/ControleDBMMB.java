package controleDBM;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import java.util.*;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import util.ExibirMensagem;
import util.FecharDialog;
import util.Mensagem;
import util.UsuarioSessaoDbmMB;
import util.UsuarioSessaoMB;
import util.ValidaCadastro;
import util.ValidacoesGerirUsuarios;
import dao.GenericDAO;
import modeloDBM.ControleVencimentosDBM;
import modeloDBM.PermissaoDBM;
import modeloDBM.UsuarioDBM;
import serviceDBM.ControleVencimentoService;

@ViewScoped
@Named("controleDBMMB")
public class ControleDBMMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private ControleVencimentosDBM vencimento;
	private List<ControleVencimentosDBM> listControleVencimentos;
	private ControleVencimentosDBM codigoVencimento;
	private String codigoAtivacao;
	private String codigoPavaAtiva;
	private String dataVencimento;
	private boolean escondeTexto = false;
	private boolean sumir = false;

	@Inject
	private GenericDAO<ControleVencimentosDBM> daoControleVencimentos;

	@Inject
	private GenericDAO<PermissaoDBM> daoPermissao;

	@Inject
	private ControleVencimentoService ControleVencimentosService;

	@Inject
	private UsuarioSessaoDbmMB usuarioSessao;

	@PostConstruct
	public void inicializar() {

		codigoVencimento = new ControleVencimentosDBM();

	}

	public void voltar() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../validadbm.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void principal() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("dbm/inicio.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void valida2() {

		String formato = "dd/MM/yyyy";
		SimpleDateFormat dataFormatada = new SimpleDateFormat(formato);
		Date dia = new Date();
		String data = dataFormatada.format(dia);
		double mes = data.charAt(3) + data.charAt(4) + data.charAt(0) + data.charAt(9) + data.charAt(1);
		double divisao = ((mes / 2) * 5) / 3;
		String vod = String.valueOf(divisao);
		String codigoGerado = String.valueOf("CVDS" + divisao + "ADMGBKS" + divisao + "CG" + vod.charAt(1));

		if (codigoGerado.equals(codigoPavaAtiva)) {

			listControleVencimentos = daoControleVencimentos.listaComStatus(ControleVencimentosDBM.class);

			if (listControleVencimentos.size() == 0) {
				voltar();
			} else {

				for (ControleVencimentosDBM c : listControleVencimentos) {
					vencimento = c;
				}

				vencimento.setStatus(false);
				ControleVencimentosService.inserirAlterar(vencimento);
			}

			LocalDate dataHoje = LocalDate.now();
			LocalDate meses = dataHoje.plusMonths(1);
			Date date = java.sql.Date.valueOf(meses);

			codigoVencimento.setData(date);
			codigoVencimento.setStatus(true);
			ControleVencimentosService.inserirAlterar(codigoVencimento);
			principal();

		} else {
			ExibirMensagem.exibirMensagem("Código Inválido");
		}

	}

	public void controleTravamento() {

		listControleVencimentos = daoControleVencimentos.listaComStatus(ControleVencimentosDBM.class);

		if (listControleVencimentos.size() == 0) {
			voltar();
		} else {

			for (ControleVencimentosDBM c : listControleVencimentos) {
				vencimento = c;
			}

			String formato = "yyyy-MM-dd";
			SimpleDateFormat dataFormatada = new SimpleDateFormat(formato);

			Date dia = new Date();

			String dat = String.valueOf(dia);
			String dataSystema = String.valueOf(vencimento.getData());

			String formatada = dataFormatada.format(dia);
			String formatadaSus = dataFormatada.format(vencimento.getData());

			System.out.println("ecibre dia " + formatada + " sys " + formatadaSus);

			try {

				Date dataSys = dataFormatada.parse(formatadaSus);
				Date dataSyss = dataFormatada.parse(formatada);

				System.out.println("ecibre dataSys   - " + dataSys + " sys dataSyss   - " + dataSyss);

				if (dataSys.equals(dataSyss)) {
					ExibirMensagem.exibirMensagem("Hoje é o ultimo dia válido de licença");

					System.out.println("aquiiii vence ");
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

	}

	public void ControleVencimentos() {

		listControleVencimentos = daoControleVencimentos.listaComStatus(ControleVencimentosDBM.class);

		if (listControleVencimentos.size() == 0) {
			voltar();
		} else {

			for (ControleVencimentosDBM c : listControleVencimentos) {
				vencimento = c;
			}

			String formato = "yyyy-MM-dd";
			SimpleDateFormat dataFormatada = new SimpleDateFormat(formato);

			Date dia = new Date();

			String dat = String.valueOf(dia);
			String dataSystema = String.valueOf(vencimento.getData());

			String formatada = dataFormatada.format(dia);
			String formatadaSus = dataFormatada.format(vencimento.getData());

			System.out.println("ecibre dia " + formatada + " sys " + formatadaSus);

			try {

				Date dataSys = dataFormatada.parse(formatadaSus);
				Date dataSyss = dataFormatada.parse(formatada);

				System.out.println("ecibre dataSys   - " + dataSys + " sys dataSyss   - " + dataSyss);

				if (dataSys.before(dataSyss)) {

					vencimento.setStatus(false);
					ControleVencimentosService.inserirAlterar(vencimento);
					voltar();

				}
				if (dataSys.equals(dataSyss)) {
					ExibirMensagem.exibirMensagem("Hoje é o ultimo dia válido de licença");

					System.out.println("aquiiii vence ");
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

	}

	public void buscarData() {
		String formato = "dd/MM/yyyy";
		SimpleDateFormat dataFormatada = new SimpleDateFormat(formato);

		List<ControleVencimentosDBM> listControle = new ArrayList<>();
		ControleVencimentosDBM pegaData = new ControleVencimentosDBM();
		listControle = daoControleVencimentos.listaComStatus(ControleVencimentosDBM.class);

		if (listControle.size() > 0) {

			pegaData = listControle.get(0);

			dataVencimento = dataFormatada.format(pegaData.getData());

			UsuarioDBM usuario = usuarioSessao.recuperarUsuario();
			List<PermissaoDBM> listPermissao = new ArrayList<>();
			listPermissao = daoPermissao.listar(PermissaoDBM.class, " usuario ='" + usuario.getId() + "'");
			boolean some = false;

			for (PermissaoDBM p : listPermissao) {
				if (p.getTipo().getDescricao().equals("relatorios")) {
					some = true;
				}
			}

			if (some) {
				sumir = true;
			}

			System.out.println("valor sumir " + sumir);
		} else {
			sumir = false;
		}
	}
	
	
	 

	public void valida() {

		String formato = "dd/MM/yyyy";
		SimpleDateFormat dataFormatada = new SimpleDateFormat(formato);
		Date dia = new Date();
		String data = dataFormatada.format(dia);
		double mes = data.charAt(3) + data.charAt(4) + data.charAt(0) + data.charAt(9) + data.charAt(1);
		double divisao = ((mes / 2) * 5) / 3;
		String vod = String.valueOf(divisao);
		String codigoGerado = String.valueOf("CVDS" + divisao + "ADMGBKS" + divisao + "CG" + vod.charAt(1));

		if (codigoGerado.equals(codigoPavaAtiva)) {

			LocalDate dataHoje = LocalDate.now();
			LocalDate meses = dataHoje.plusMonths(1);
			Date date = java.sql.Date.valueOf(meses);

			codigoVencimento.setData(date);
			codigoVencimento.setStatus(true);
			ControleVencimentosService.inserirAlterar(codigoVencimento);
			principal();

		} else {
			ExibirMensagem.exibirMensagem("Código Inválido");
		}

	}

	public void voltarValidar() {

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../valida2dbm.jsf");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void gerarValidacao() {

		String formato = "dd/MM/yyyy";
		SimpleDateFormat dataFormatada = new SimpleDateFormat(formato);

		Date dia = new Date();
		String data = dataFormatada.format(dia);
		double mes = data.charAt(3) + data.charAt(4) + data.charAt(0) + data.charAt(9) + data.charAt(1);
		double divisao = ((mes / 2) * 5) / 3;
		String vod = String.valueOf(divisao);
		codigoAtivacao = String.valueOf("CVDS" + divisao + "ADMGBKS" + divisao + "CG" + vod.charAt(1));

	}

	public boolean isEscondeTexto() {
		return escondeTexto;
	}

	public void setEscondeTexto(boolean escondeTexto) {
		this.escondeTexto = escondeTexto;
	}

	public String getCodigoAtivacao() {
		return codigoAtivacao;
	}

	public void setCodigoAtivacao(String codigoAtivacao) {
		this.codigoAtivacao = codigoAtivacao;
	}

	public ControleVencimentosDBM getCodigoVencimento() {
		return codigoVencimento;
	}

	public void setCodigoVencimento(ControleVencimentosDBM codigoVencimento) {
		this.codigoVencimento = codigoVencimento;
	}

	public String getCodigoPavaAtiva() {
		return codigoPavaAtiva;
	}

	public void setCodigoPavaAtiva(String codigoPavaAtiva) {
		this.codigoPavaAtiva = codigoPavaAtiva;
	}

	public String getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(String dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public boolean isSumir() {
		return sumir;
	}

	public void setSumir(boolean sumir) {
		this.sumir = sumir;
	}

}
