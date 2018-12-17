package serviceConveniencia24Horas;

import java.io.Serializable;

import javax.inject.Inject;
 
import dao.GenericDAO;
import modeloConveniencia24horas.EntradasConveniencia24Horas;
import util.Transacional;

public class EntradaService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<EntradasConveniencia24Horas> dao;
	
	@Transacional
	public void inserirAlterar(EntradasConveniencia24Horas entidade){
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
