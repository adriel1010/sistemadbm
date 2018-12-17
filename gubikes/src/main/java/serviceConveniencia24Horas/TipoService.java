package serviceConveniencia24Horas;

import java.io.Serializable;

import javax.inject.Inject;
 
import dao.GenericDAO;
import modeloConveniencia24horas.TipoConveniencia24Horas;
import util.Transacional;

public class TipoService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<TipoConveniencia24Horas> daoTipo;
	
	@Transacional
	public void inserirAlterar(TipoConveniencia24Horas tipo){
		if(tipo.getId()==null){
			daoTipo.inserir(tipo);
		}else{
			daoTipo.alterar(tipo);
		}
	}
	
	@Transacional
	public void update(String valor){
		daoTipo.update(valor);
	}

}
