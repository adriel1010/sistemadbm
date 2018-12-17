package serviceConvenienciaGuinnesBeer;

import java.io.Serializable;

import javax.inject.Inject;
 
import dao.GenericDAO;
import modeloConvenienciaGuinnesBeer.ControleVencimentos;
import util.Transacional;

public class ControleVencimentoService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<ControleVencimentos> dao;
	
	@Transacional
	public void inserirAlterar(ControleVencimentos entidade){
		if(entidade.getId()==null){
			dao.inserir(entidade);
		}else{
			dao.alterar(entidade);
		}
	}
	
	@Transacional
	public void update(String valor){
		dao.update(valor);
	}

}
