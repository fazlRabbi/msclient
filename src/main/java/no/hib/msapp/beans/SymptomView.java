/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hib.msapp.beans;

import no.hib.msapp.entities.Symptom;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

/**
 * @author Leif Arne
 */
@ManagedBean
@ViewScoped
public class SymptomView implements Serializable {

	private static final long serialVersionUID = 2444006076011790018L;
	private Symptom symptom;


    public SymptomView() {
        // symptom = new Symptom();
    }

    @PostConstruct
    public void initBean() {
        symptom = (Symptom) FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get("symptom");
    }


    public String submit() {
        return "survey.xhtml";
    }

    /**
     * @return the symptom
     */
    public Symptom getSymptom() {
        return symptom;
    }

    /**
     * @param symptom the symptom to set
     */
    public void setSymptom(Symptom symptom) {
        this.symptom = symptom;
    }


}
