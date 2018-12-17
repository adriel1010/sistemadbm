package controleDBM;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
 
import util.ExibirMensagem;
import util.FecharDialog;
import util.Mensagem;
import util.ValidacoesGerirUsuarios;
import dao.GenericDAO;
import modeloDBM.TipoDBM;
import serviceDBM.TipoService;


@ViewScoped
@Named("tipoServidorDbmMB")
public class TipoServidorDbmMB implements Serializable{
	
	private static final long serialVersionUID = 1L;


	private TipoDBM tipoServidor;
	private List<TipoDBM> tipoServidorBusca;
	private List<TipoDBM> listTipoServidor;
	
	@Inject
	private ValidacoesGerirUsuarios validacoesGerirUsuarios;

	
	@Inject
	private GenericDAO<TipoDBM> daoTipo; //faz as buscas
	
	@Inject
	private TipoService tipoService; // inserir no banco
	
	
	@PostConstruct
	public void inicializar() {
	
		tipoServidor = new TipoDBM();
	
		listTipoServidor = new ArrayList<>();
		listTipoServidor = daoTipo.listaComStatus(TipoDBM.class);
		tipoServidorBusca = new ArrayList<>();
		
	}

	public void preencherListaTipoServidor(TipoDBM t) {
		this.tipoServidor = t;

	}

	public void inativarTipoServidor(TipoDBM t) {
		tipoService.update(" Tipo set status = false where id = " + t.getId());
		criarNovoObjeto();
		ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
		carregarLista();
	}

	public void salvar() {

		try {
			
			if (validacoesGerirUsuarios.buscarPermissao(tipoServidor) == true) {
				ExibirMensagem.exibirMensagem(Mensagem.TIPOCADASTRO);
			} else {

				if (tipoServidor.getId() == null) {
					tipoServidor.setStatus(true);
					tipoService.inserirAlterar(tipoServidor);

				} else {
					tipoServidor.setStatus(true);
					tipoService.inserirAlterar(tipoServidor);
				}

				criarNovoObjeto();
				ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
				FecharDialog.fecharDialogTipoServidor();
				carregarLista();
			}
		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
			e.printStackTrace();
		}

	}

	public void criarNovoObjeto() {
		tipoServidor = new TipoDBM();
	}

	public void carregarLista() {
		listTipoServidor = daoTipo.listaComStatus(TipoDBM.class);
	}

	public List<TipoDBM> getListTipoServidor() {
		return listTipoServidor;
	}

	public void setListTipoServidor(List<TipoDBM> listTipoServidor) {
		this.listTipoServidor = listTipoServidor;
	}
 
	public TipoDBM getTipoServidor() {
		return tipoServidor;
	}

	public void setTipoServidor(TipoDBM tipoServidor) {
		this.tipoServidor = tipoServidor;
	}

	public List<TipoDBM> getTipoServidorBusca() {
		return tipoServidorBusca;
	}

	public void setTipoServidorBusca(List<TipoDBM> tipoServidorBusca) {
		this.tipoServidorBusca = tipoServidorBusca;
	}
}

