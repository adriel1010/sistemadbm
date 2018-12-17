package br.com.cronos.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException; 
import javax.inject.Inject;
import javax.inject.Named;
 
import dao.GenericDAO;
import modeloConvenienciaGuinnesBeer.Tipo;
import util.ExibirMensagem;
import util.Mensagem;

@Named("converterTipoPermissao")
public class ConverteTipos implements Converter {

	
	@Inject
	private GenericDAO<Tipo> dao;
	
	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		 
		if (value != null && value.trim().length() > 0) { 
			try {
				
				return  dao.buscarPorId(Tipo.class, Long.parseLong(value));
			
			} catch (Exception e) {
				e.printStackTrace();
				throw new ConverterException(
						
						new FacesMessage(FacesMessage.SEVERITY_ERROR, Mensagem.ERRO_CONVERTER, ""));
				
			}
		}  
		
		 
		return null;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			Tipo cur = (Tipo) object;
			return String.valueOf(cur.getId());
		} else {
			return null;
		}
	}
}
