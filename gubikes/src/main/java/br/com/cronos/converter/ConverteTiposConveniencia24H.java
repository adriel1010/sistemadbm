package br.com.cronos.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException; 
import javax.inject.Inject;
import javax.inject.Named;
 
import dao.GenericDAO;
import modeloConveniencia24horas.TipoConveniencia24Horas;
import util.ExibirMensagem;
import util.Mensagem;

@Named("controleConveniencia24horas")
public class ConverteTiposConveniencia24H implements Converter {

	
	@Inject
	private GenericDAO<TipoConveniencia24Horas> dao;
	
	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		 
		if (value != null && value.trim().length() > 0) { 
			try {
				
				return  dao.buscarPorId(TipoConveniencia24Horas.class, Long.parseLong(value));
			
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
			TipoConveniencia24Horas cur = (TipoConveniencia24Horas) object;
			return String.valueOf(cur.getId());
		} else {
			return null;
		}
	}
}
