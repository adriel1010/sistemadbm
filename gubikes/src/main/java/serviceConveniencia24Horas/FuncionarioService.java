package serviceConveniencia24Horas;

import java.io.Serializable;

import javax.inject.Inject;
 
import dao.GenericDAO; 
import modeloConveniencia24horas.UsuarioConveniencia24Horas;
import util.Transacional;

public class FuncionarioService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<UsuarioConveniencia24Horas> daoFuncionario;
	
	@Transacional
	public void inserirAlterar(UsuarioConveniencia24Horas funcionario){
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
