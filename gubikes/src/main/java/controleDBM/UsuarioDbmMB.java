package controleDBM;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import util.CriptografiaSenha;
import util.ExibirMensagem;
import util.FecharDialog;
import util.Mensagem;
import util.ValidaCadastro;
import dao.GenericDAO;
import modeloDBM.PermissaoDBM;
import modeloDBM.TipoDBM;
import modeloDBM.UsuarioDBM;
import serviceDBM.FuncionarioService;
import serviceDBM.PermissaoService;

@ViewScoped
@Named("usuarioDbmMB")
public class UsuarioDbmMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private UsuarioDBM usuario;
	private PermissaoDBM permissao;
	private TipoDBM tipos;
	private List<UsuarioDBM> listUsuario;
	private List<TipoDBM> listPermissao;
	private boolean senha = true;
	private boolean cadastro = true;
	private boolean cadastroExistente = true;
	private int controle = 0;
	private boolean controleAdd = false;
	private boolean controleAddTipo = true;

	public boolean isControleAddTipo() {
		return controleAddTipo;
	}

	public void setControleAddTipo(boolean controleAddTipo) {
		this.controleAddTipo = controleAddTipo;
	}

	@Inject
	private GenericDAO<UsuarioDBM> daoUsuario;

	@Inject
	private GenericDAO<TipoDBM> daoTipos;

	@Inject
	private GenericDAO<PermissaoDBM> daoPermissao;

	@Inject
	private FuncionarioService funcionarioService;
 

	@Inject
	private PermissaoService permissaoService;
 

	@Inject
	private ValidaCadastro validaCadastro;

	@PostConstruct
	public void inicializar() {

		usuario = new UsuarioDBM();
		permissao = new PermissaoDBM();
		listPermissao = new ArrayList<>();
		listUsuario = daoUsuario.listarCodicaoLivre(UsuarioDBM.class, " status is true");

		tipos = new TipoDBM();

	}

	public void removePermissao(TipoDBM t) {
		if (usuario.getId() == null) {
			listPermissao.remove(t);
		} else {
			PermissaoDBM permissaoInativar = new PermissaoDBM();
			permissaoInativar = daoPermissao.buscarCondicao(PermissaoDBM.class,
					" status is true and usuario = " + usuario.getId() + " and tipo = " + t.getId());

			funcionarioService.update("Permissao set status = false where id = " + permissaoInativar.getId());
			ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
			buscaPermissaoFuncionario();

		}

	}
	
	public void removerFuncionario(UsuarioDBM t) {
		funcionarioService.update(" Usuario set status = false where id = " + t.getId());
		criarNovoObjeto();
		ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
		listUsuario = daoUsuario.listarCodicaoLivre(UsuarioDBM.class, " status is true");
	}

	public void adicionarPermissao() {

		if (usuario.getId() == null) {
			if (listPermissao.size() == 0) {
				listPermissao.add(tipos);
				tipos = new TipoDBM();
			} else {
				if (validaPermissaoAdiciona()) {
					listPermissao.add(tipos);
					tipos = new TipoDBM();
				} else {
					ExibirMensagem.exibirMensagem(Mensagem.ADDPERMISSAO);
				}
			}
		} else {
			if (validaPermissaoAdicionaCadastrado()) {
				permissao.setDataInclusao(new Date());
				permissao.setStatus(true);
				permissao.setUsuario(usuario);
				permissao.setTipo(tipos);

				permissaoService.inserirAlterar(permissao);

				permissao = new PermissaoDBM();
				ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
				buscaPermissaoFuncionario();
			} else {
				ExibirMensagem.exibirMensagem(Mensagem.ADDPERMISSAO);
			}
		}
	}

	public void buscaPermissaoFuncionario() {
		listPermissao.clear();
		for (PermissaoDBM p : daoPermissao.listar(PermissaoDBM.class, " usuario = " + usuario.getId())) {
			listPermissao.add(p.getTipo());
		}
	}

	public boolean validaPermissaoAdicionaCadastrado() {
		List<PermissaoDBM> listPermissAdd = daoPermissao.listar(PermissaoDBM.class, " usuario = " + usuario.getId());
		for (PermissaoDBM t : listPermissAdd) {
			if (t.getTipo().getDescricao().equals(tipos.getDescricao())) {
				return false;
			}
		}
		return true;
	}

	public boolean validaPermissaoAdiciona() {

		for (TipoDBM t : listPermissao) {
			if (t.getDescricao().equals(tipos.getDescricao())) {
				return false;
			}
		}
		return true;
	}

	public void buscar() {
		usuario = new UsuarioDBM();
		cadastro = true;
		cadastroExistente = false;
		controle = 1;
	}

	public void controle() {
		controleAddTipo = false;
	}

	public void preencherLista(UsuarioDBM t) {

		this.usuario = t;

		senha = false;
		controleAdd = true;

		buscaPermissaoFuncionario();
	}

	// public void excluirFuncionario(Usuario t) {
	// // validar se só inativa aqui ou inativa os dois
	//
	// if (validaCadastro.validaExcluirFuncionario(t.getPessoa().getId()) ==
	// false) {
	// funcionarioService.update(
	// " Funcionario set statusFuncionario = false, controleFuncionario = 0
	// where id = " + t.getId());
	// funcionarioPessoaService
	// .update(" Pessoas set status = false, controle = 0 where id = " +
	// t.getPessoa().getId());
	//
	// inativarPermissao(t.getId());
	// criarNovoObjeto();
	// ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
	// carregarLista();
	// buscarInativos();
	// } else {
	// funcionarioService.update(
	// " Funcionario set statusFuncionario = false, controleFuncionario = 0
	// where id = " + t.getId());
	// inativarPermissao(t.getId());
	// criarNovoObjeto();
	// ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
	// carregarLista();
	// buscarInativos();
	// }
	//
	// }

	// public void inativarPermissao(Long id){
	// List<Permissao> listPerInativa = daoPermissao.listar(Permissao.class,
	// " funcionario = " + id);
	// for (Permissao t : listPerInativa) {
	// funcionarioService.update("Permissao set status = false where id = " +
	// t.getId());
	// }
	//
	// }
	//
	//
	// public void salvar() {
	// funcionarioPessoa.setNome(funcionarioPessoa.getNome().toUpperCase());
	// try {
	//
	// if (listPermissao.size() > 0) {
	// if (funcionario.getId() == null) {
	//
	//
	// if (validaCadastro.buscarFuncionarioEmail(funcionarioPessoa)) {
	// ExibirMensagem.exibirMensagem(Mensagem.CADASTROEMAIL);
	// } else {
	//
	// funcionarioPessoa.setDataCadastroPessoa(new Date());
	// funcionarioPessoa.setStatus(true);
	// funcionarioPessoa.setControle(1);
	// funcionarioPessoaService.inserirAlterar(funcionarioPessoa);
	//
	// funcionario.setSenha(CriptografiaSenha.criptografar(funcionario.getSenha()));
	// funcionario.setDataCadastroFuncionario(new Date());
	// funcionario.setPessoa(funcionarioPessoa);
	// funcionario.setControleFuncionario(1);
	// funcionario.setStatusFuncionario(true);
	// funcionarioService.inserirAlterar(funcionario);
	//
	// for (Tipos t : listPermissao) {
	// permissao.setDataInclusao(new Date());
	// permissao.setStatus(true);
	// permissao.setFuncionario(funcionario);
	// permissao.setTipo(t);
	//
	// permissaoService.inserirAlterar(permissao);
	//
	// permissao = new Permissao();
	// }
	//
	// criarNovoObjeto();
	// ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
	// FecharDialog.fecharDialogCliente();
	// carregarLista();
	// buscarInativos();
	// senha = true;
	// }
	//
	//
	// } else {
	//
	// funcionarioPessoaService.inserirAlterar(funcionarioPessoa);
	//
	// funcionarioService.inserirAlterar(funcionario);
	// criarNovoObjeto();
	// ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
	// FecharDialog.fecharDialogCliente();
	// carregarLista();
	// buscarInativos();
	// senha = true;
	//
	//
	// }
	// } else {
	// ExibirMensagem.exibirMensagem(Mensagem.PERMISSAO);
	// }
	// } catch (Exception e) {
	// ExibirMensagem.exibirMensagem(Mensagem.ERRO);
	// e.printStackTrace();
	// }
	// }

	public void salvar() {
	 
		try {

			if (listPermissao.size() > 0) {
				if (usuario.getId() == null) {

					if (validaCadastro.buscarUsuario(usuario)) {
						ExibirMensagem.exibirMensagem(Mensagem.CADASTROEMAIL);
					} else {

						usuario.setSenha(CriptografiaSenha.criptografar(usuario.getSenha()));
						usuario.setDataCadastro(new Date());
						usuario.setStatus(true);
						funcionarioService.inserirAlterar(usuario);

						for (TipoDBM t : listPermissao) {
							permissao.setDataInclusao(new Date());
							permissao.setStatus(true);
							permissao.setUsuario(usuario);
							permissao.setTipo(t);

							permissaoService.inserirAlterar(permissao);

							permissao = new PermissaoDBM();
						}

						criarNovoObjeto();
						ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
						FecharDialog.fecharDialogCliente();
						senha = true;
					}

				} else {

					funcionarioService.inserirAlterar(usuario);
					criarNovoObjeto();
					ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
					FecharDialog.fecharDialogCliente();
					senha = true;

				}
				listUsuario = daoUsuario.listarCodicaoLivre(UsuarioDBM.class, " status is true");
			} else {
				ExibirMensagem.exibirMensagem(Mensagem.PERMISSAO);
			}
		} catch (Exception e) {
			ExibirMensagem.exibirMensagem(Mensagem.ERRO);
			e.printStackTrace();
		}
	}

	// public void salvar() {
	// funcionarioPessoa.setNome(funcionarioPessoa.getNome().toUpperCase());
	// try {
	//
	// if (listPermissao.size() > 0) {
	// if (funcionario.getId() == null) {
	//
	// if (validaCadastro.buscarFuncionario(funcionarioPessoa)) {
	// ExibirMensagem.exibirMensagem(Mensagem.CADASTRO);
	// } else {
	// if (validaCadastro.buscarFuncionarioEmail(funcionarioPessoa)) {
	// ExibirMensagem.exibirMensagem(Mensagem.CADASTROEMAIL);
	// } else {
	//
	// funcionarioPessoa.setDataCadastroPessoa(new Date());
	// funcionarioPessoa.setStatus(true);
	// funcionarioPessoa.setControle(1);
	// funcionarioPessoaService.inserirAlterar(funcionarioPessoa);
	//
	// funcionario.setSenha(CriptografiaSenha.criptografar(funcionario.getSenha()));
	// funcionario.setDataCadastroFuncionario(new Date());
	// funcionario.setPessoa(funcionarioPessoa);
	// funcionario.setControleFuncionario(1);
	// funcionario.setStatusFuncionario(true);
	// funcionarioService.inserirAlterar(funcionario);
	//
	// for (Tipos t : listPermissao) {
	// permissao.setDataInclusao(new Date());
	// permissao.setStatus(true);
	// permissao.setFuncionario(funcionario);
	// permissao.setTipo(t);
	//
	// permissaoService.inserirAlterar(permissao);
	//
	// permissao = new Permissao();
	// }
	//
	// criarNovoObjeto();
	// ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
	// FecharDialog.fecharDialogCliente();
	// carregarLista();
	// buscarInativos();
	// senha = true;
	// }
	// }
	//
	// } else {
	// if (validaCadastro.buscarFuncionario(funcionarioPessoa)
	// && validaCadastro.buscarFuncionarioAlterar(funcionarioPessoa)) {
	// ExibirMensagem.exibirMensagem(Mensagem.CADASTRO);
	// } else {
	// funcionarioPessoaService.inserirAlterar(funcionarioPessoa);
	//
	// funcionarioService.inserirAlterar(funcionario);
	// criarNovoObjeto();
	// ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
	// FecharDialog.fecharDialogCliente();
	// carregarLista();
	// buscarInativos();
	// senha = true;
	//
	// }
	// }
	// } else {
	// ExibirMensagem.exibirMensagem(Mensagem.PERMISSAO);
	// }
	// } catch (Exception e) {
	// ExibirMensagem.exibirMensagem(Mensagem.ERRO);
	// e.printStackTrace();
	// }
	// }

	public List<TipoDBM> completarTipos(String str) {
		List<TipoDBM> pess = new ArrayList<>();
		pess = daoTipos.listaComStatus(TipoDBM.class);
		List<TipoDBM> pessoasSelecionados = new ArrayList<>();
		for (TipoDBM at : pess) {
			if (at.getDescricao().toLowerCase().startsWith(str)) {
				pessoasSelecionados.add(at);
			}
		}
		return pessoasSelecionados;
	}

	public void salvarSenha() {
		usuario.setSenha(CriptografiaSenha.criptografar(usuario.getSenha()));
		funcionarioService.inserirAlterar(usuario);
		criarNovoObjeto();
		ExibirMensagem.exibirMensagem(Mensagem.SUCESSO);
		FecharDialog.fecharDialogFuncionarioSenha();

	}

	public void criarNovoObjeto() {
		permissao = new PermissaoDBM();
		usuario = new UsuarioDBM();
		senha = true;
		cadastro = false;
		cadastroExistente = true;
		controle = 0;
		controleAdd = false;
		listPermissao = new ArrayList<>();
	}

	// public void carregarLista() {
	// listUsuario = daoUsuario.listarCodicaoLivre(Usuario.class,
	// " statusFuncionario is true and pessoa.status is true");
	// }

	public boolean isControleAdd() {
		return controleAdd;
	}

	public void setControleAdd(boolean controleAdd) {
		this.controleAdd = controleAdd;
	}

	public PermissaoDBM getPermissao() {
		return permissao;
	}

	public void setPermissao(PermissaoDBM permissao) {
		this.permissao = permissao;
	}

	public UsuarioDBM getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDBM usuario) {
		this.usuario = usuario;
	}

	public List<UsuarioDBM> getListUsuario() {
		return listUsuario;
	}

	public void setListUsuario(List<UsuarioDBM> listUsuario) {
		this.listUsuario = listUsuario;
	}

	public TipoDBM getTipos() {
		return tipos;
	}

	public void setTipos(TipoDBM tipos) {
		this.tipos = tipos;
	}

	public List<TipoDBM> getListPermissao() {
		return listPermissao;
	}

	public void setListPermissao(List<TipoDBM> listPermissao) {
		this.listPermissao = listPermissao;
	}

	public boolean isSenha() {
		return senha;
	}

	public void setSenha(boolean senha) {
		this.senha = senha;
	}

}
