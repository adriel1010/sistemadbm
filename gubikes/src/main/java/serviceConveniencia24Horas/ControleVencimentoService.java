package serviceConveniencia24Horas;

import java.io.Serializable;

import javax.inject.Inject;
 
import dao.GenericDAO;
import modeloConveniencia24horas.ControleVencimentosConveniencia24Horas;
import util.Transacional;

public class ControleVencimentoService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<ControleVencimentosConveniencia24Horas> dao;
	
	@Transacional
	public void inserirAlterar(ControleVencimentosConveniencia24Horas entidade){
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
