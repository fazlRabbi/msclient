/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hib.msapp.beans;

import no.hib.msapp.RESTClient.SurveyFacade;
import no.hib.msapp.entities.AppointmentPreperation;

import javax.annotation.ManagedBean;
import javax.enterprise.context.Dependent;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Leif Arne
 */
@Named(value = "surveyListView")
@Dependent
@ManagedBean
public class SurveyListView {

    /**
     * Creates a new instance of SurveyListView
     */
    private List<AppointmentPreperation> surveys;

    private SurveyFacade surveyFacade;

    private List<AppointmentPreperation> oldConsultations = new ArrayList<AppointmentPreperation>();
    private List<AppointmentPreperation> activeConsultations = new ArrayList<AppointmentPreperation>();

    public SurveyListView() {

        String ssn = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("ssn");
        surveyFacade = new SurveyFacade(ssn);
        splitConsultations(surveyFacade.findAll());

    }

    public final void splitConsultations(List<AppointmentPreperation> consultations) {
        for (AppointmentPreperation survey : consultations) {
            LocalTime appointmentTime = survey.getAppointmentTime();
            LocalDate appointmentDate = survey.getAppointmentDate();
            Calendar cal = Calendar.getInstance();
            cal.set(appointmentDate.getYear(), appointmentDate.getMonthValue() - 1,
                    appointmentDate.getDayOfMonth(), appointmentTime.getHour(), appointmentTime.getMinute());
            cal.add(Calendar.DATE, -7);
            Date answerDate = cal.getTime();

            Date now = Calendar.getInstance().getTime();
            if (now.compareTo(answerDate) > 0) {
                getOldConsultations().add(survey);
            } else {
                getActiveConsultations().add(survey);
            }
        }

    }

    /**
     * @return the surveys
     */
    public List<AppointmentPreperation> getSurveys() {
        return surveys;
    }

    public String viewSurvey(AppointmentPreperation survey) {
        FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put("survey", survey);
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Guid", survey.getUuid());

        return "summary.xhtml";
    }

    /**
     * @param surveys the surveys to set
     */
    public void setSurveys(List<AppointmentPreperation> surveys) {
        this.surveys = surveys;
    }

    public String goToConsultation(AppointmentPreperation survey) {
        FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put("survey", survey);
        return "nextConsultation.xhtml";
    }

    /**
     * @return the oldConsultations
     */
    public List<AppointmentPreperation> getOldConsultations() {
        return oldConsultations;
    }

    /**
     * @param oldConsultations the oldConsultations to set
     */
    public void setOldConsultations(List<AppointmentPreperation> oldConsultations) {
        this.oldConsultations = oldConsultations;
    }

    /**
     * @return the activeConsultations
     */
    public List<AppointmentPreperation> getActiveConsultations() {
        return activeConsultations;
    }

    /**
     * @param activeConsultations the activeConsultations to set
     */
    public void setActiveConsultations(List<AppointmentPreperation> activeConsultations) {
        this.activeConsultations = activeConsultations;
    }

}
