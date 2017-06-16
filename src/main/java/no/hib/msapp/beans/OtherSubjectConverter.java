/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hib.msapp.beans;

import no.hib.msapp.entities.OtherSubject;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * @author Leif Arne
 */
@FacesConverter("otherSubjectConverter")
public class OtherSubjectConverter implements Converter {

    /**
     * Creates a new instance of OtherSubjectConverter
     */
    public OtherSubjectConverter() {

    }

    @Override
    public Object getAsObject(FacesContext facesContext,
                              UIComponent component, String value) {
        OtherSubject otherSubject = new OtherSubject();
        otherSubject.setName(value);

        return otherSubject;
    }

    @Override
    public String getAsString(FacesContext facesContext,
                              UIComponent component, Object value) {
        return value.toString();
    }
}
    
