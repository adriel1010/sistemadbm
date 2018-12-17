package serviceConvenienciaGuinnesBeer;

import java.io.Serializable;

import javax.inject.Inject;
 
import dao.GenericDAO; 
import modeloConvenienciaGuinnesBeer.Usuario;
import util.Transacional;

public class FuncionarioService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<Usuario> daoFuncionario;
	
	@Transacional
	public void inserirAlterar(Usuario funcionario){
		if(funcionario.getId()==null){
			daoFuncionario.inserir(funcionario);
		}else{
			daoFuncionario.alterar(funcionario);
		}
	}
	
	@Transacional
	public void update(String valor){
		daoFuncionario.update(valor);
	}

}
