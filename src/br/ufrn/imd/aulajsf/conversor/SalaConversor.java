package br.ufrn.imd.aulajsf.conversor;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

import br.ufrn.imd.aulajsf.dominio.Sala;
import br.ufrn.imd.aulajsf.repositorios.SalaRepositorio;

@FacesConverter("salaConversor")
public class SalaConversor implements Converter<Object>, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public SalaConversor() {
        // TODO Auto-generated constructor stub
    }

    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
				return SalaRepositorio.getSala((Integer.valueOf(value)));
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw new ConverterException(
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "sala não valida."));
            }
        } else {
            return null;
        }
    }

    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object instanceof Sala && object != null){ 
        	Integer id =  (Integer)((Sala) object).getId();
            return id.toString();
        } else {
            return null;
        }
    }

}