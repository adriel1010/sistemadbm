package util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import dao.GenericDAO;
import modeloConveniencia24horas.TipoConveniencia24Horas;
import modeloConvenienciaGuinnesBeer.Tipo;
import modeloDBM.TipoDBM; 


public class ValidacoesGerirUsuarios implements Serializable{
	
	
	private static final long serialVersionUID = 1L;


	@Inject
	private GenericDAO<Tipo> daoTipo; //faz as buscas
	
	@Inject
	private GenericDAO<TipoDBM> daoTipoDBM; //faz as buscas
	
	@Inject
	private GenericDAO<TipoConveniencia24Horas> daoTipoConveniencia24Horas; //faz as buscas
	
//	@Inject
//	private GenericDAO<Pessoas> daoPessoa; //faz as buscas
//	
//	@Inject
//	private GenericDAO<Funcionario> daoFuncionario; //faz as buscas
	

 
	

	

/*	public Boolean buscarUsuarios(Pessoas pessoa) {
		List<Pessoas> pessoas = new ArrayList<>();
		try {
			pessoas = daoPessoa.listarCadastro(Pessoas.class, " usuario = '" + pessoa.getUsuario() + "'");
			if (pessoas.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			System.err.println("Erro buscarUsuarios");
			e.printStackTrace();
		}
		return false;
	}
	*/
	public boolean buscarPermissao(Tipo tipo) {
		
		
		List<Tipo> tipos = new ArrayList<>();
		try {
			tipos = daoTipo.listar(Tipo.class, " descricao = '" + tipo.getDescricao() + "'");
			if (tipos.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			System.err.println("Erro buscarUsuarios");
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean buscarPermissao(TipoConveniencia24Horas tipo) {
		
		
		List<TipoConveniencia24Horas> tipos = new ArrayList<>();
		try {
			tipos = daoTipoConveniencia24Horas.listar(TipoConveniencia24Horas.class, " descricao = '" + tipo.getDescricao() + "'");
			if (tipos.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			System.err.println("Erro buscarUsuarios");
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean buscarPermissao(TipoDBM tipo) {
		
		
		List<TipoDBM> tipos = new ArrayList<>();
		try {
			tipos = daoTipoDBM.listar(TipoDBM.class, " descricao = '" + tipo.getDescricao() + "'");
			if (tipos.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			System.err.println("Erro buscarUsuarios");
			e.printStackTrace();
		}
		return false;
	}
	
	/*

	public Boolean buscarUsuarioAlterar(Pessoas pessoa) {
		
		List<Pessoas> pessoas = new ArrayList<>();
		pessoas = daoPessoa.listar(Pessoas.class,
				" usuario = '" + pessoa.getUsuario() + "' and id = '" + pessoa.getId() + "'");
		if (pessoas.size() > 0) {
			return false;
		}
		return true;
	}

*/





}
