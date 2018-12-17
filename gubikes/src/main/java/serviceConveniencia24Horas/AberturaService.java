package serviceConveniencia24Horas;

import java.io.Serializable;

import javax.inject.Inject;
 
import dao.GenericDAO;
import modeloConveniencia24horas.AberturaConveniencia24Horas;
import util.Transacional;

public class AberturaService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<AberturaConveniencia24Horas> daoAbertura;
	
	@Transacional
	public void inserirAlterar(AberturaConveniencia24Horas abertura){
		if(abertura.getId()==null){
			daoAbertura.inserir(abertura);
		}else{
			daoAbertura.alterar(abertura);
		}
	}
	
	@Transacional
	public void update(String valor){
		daoAbertura.update(valor);
	}

}
