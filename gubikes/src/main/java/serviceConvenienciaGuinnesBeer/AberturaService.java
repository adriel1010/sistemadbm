package serviceConvenienciaGuinnesBeer;

import java.io.Serializable;

import javax.inject.Inject;
 
import dao.GenericDAO;
import modeloConvenienciaGuinnesBeer.Abertura;
import util.Transacional;

public class AberturaService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private GenericDAO<Abertura> daoAbertura;
	
	@Transacional
	public void inserirAlterar(Abertura abertura){
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
