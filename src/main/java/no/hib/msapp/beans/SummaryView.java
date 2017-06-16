/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hib.msapp.beans;

import no.hib.msapp.entities.AppointmentPreperation;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 * @author Leif Arne
 */
@ManagedBean
public class SummaryView {

    /**
     * Creates a new instance of SummaryView
     */
    private AppointmentPreperation survey = new AppointmentPreperation();

    @PostConstruct
    public void initBean() {
        setSurvey((AppointmentPreperation) FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get("survey"));
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Guid", survey.getUuid());
    }

    public SummaryView() {
    }

    /**
     * @return the survey
     */
    public AppointmentPreperation getSurvey() {
        return survey;
    }

    public String goBack() {
        return "consultationHistory.xhtml";
    }

    /**
     * @param survey the survey to set
     */
    public void setSurvey(AppointmentPreperation survey) {
        this.survey = survey;
    }

}
