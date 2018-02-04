/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. */
package no.hib.msapp.beans;

import no.hib.msapp.RESTClient.OtherSubjectFacade;
import no.hib.msapp.RESTClient.SurveyFacade;
import no.hib.msapp.RESTClient.SymptomFacade;
import no.hib.msapp.entities.AppointmentPreperation;
import no.hib.msapp.entities.OtherSubject;
import no.hib.msapp.entities.Symptom;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Leif Arne
 */
@Named
@ApplicationScoped
@ManagedBean
public class SurveyView implements Serializable {

    private int step = 1;
    private AppointmentPreperation survey;
    private SurveyFacade surveyFacade;
    private final int MAX_STEPS = 8;

    private List<String> selectedOthers;
    private List<SelectItem> othersSubjectsSelectItems;

    public SurveyView() {
        init();
    }

    @PostConstruct
    public void init() {
        String Guid = (String) FacesContext.getCurrentInstance().
                getExternalContext().getRequestMap().get("Guid");

        String ssn = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("ssn");
        surveyFacade = new SurveyFacade(ssn);

        // Henter survey
        if (Guid != null && !Guid.equals("")) {
            survey = surveyFacade.findByGuid(Guid);
        } else {
            survey = surveyFacade.findNextSurvey();
        }

        selectedOthers = new ArrayList<>();

        initSurvey();
    }

    private AppointmentPreperation initSurvey() {
        // Fyller survey med default symptoms fra settings
        List<Symptom> symptoms = (new SymptomFacade()).findAll();
        for (Symptom symptom : symptoms) {
            if (!survey.getSymptoms().contains(symptom)) {
                survey.addSymptom(symptom);
            }
        }

        //fyllet survey med default annet fra settings
        List<OtherSubject> subjects = (new OtherSubjectFacade()).findAll();
        for (OtherSubject subject : subjects) {
            if (!survey.getOtherSubjects().contains(subject)) {
                survey.addOtherSubject(subject);
            }
        }

        othersSubjectsSelectItems = new ArrayList<>();
        for (OtherSubject otherSubject : survey.getOtherSubjects()) {
            SelectItem option = new SelectItem(otherSubject.getName(), otherSubject.getName());
            othersSubjectsSelectItems.add(option);
        }
        return survey;
    }

    public List<String> getSelectedOthers() {
        return selectedOthers;
    }

    public void setSelectedOthers(List<String> selectedOthers) {
        this.selectedOthers = selectedOthers;
    }

    public List<SelectItem> getOthersSubjectsSelectItems() {
        return othersSubjectsSelectItems;
    }

    public void setOthersSubjectsSelectItems(List<SelectItem> othersSubjectsSelectItems) {
        this.othersSubjectsSelectItems = othersSubjectsSelectItems;
    }


    public String viewSymptom(Symptom symptom) {
        FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put("symptom", symptom);
        return "symptomTemplate.xhtml";
    }

    public void incrementStep() {
        setStep(getStep() + 1);
        System.out.println("Incrementing step");
        surveyFacade.saveSurvey(survey);
    }

    public void decrementStep() {
        setStep(getStep() - 1);
        System.out.println("Decrementing step");
        surveyFacade.saveSurvey(survey);
        if (getStep() == 1) {
            initSurvey();
        }
    }

    public String confirmSurvey() {
        return "consultationHistory.xhtml";
    }

    /**
     * @return the step
     */
    public int getStep() {
        return step;
    }

    public void test() {
        System.out.println("Testing");
    }

    public String goToConfirmation() {
        survey.setOtherSubjects(new ArrayList<OtherSubject>());
        for (String name : selectedOthers) {
            OtherSubject other = new OtherSubject(name);
            survey.addOtherSubject(other);
        }
        surveyFacade.saveSurvey(survey);
        return "confirmation.xhtml";
    }

    /**
     * @param step the step to set
     */
    public void setStep(int step) {
        this.step = step;
    }

    /**
     * @return the survey
     */
    public AppointmentPreperation getSurvey() {
        String guid = (String) FacesContext.getCurrentInstance().
                getExternalContext().getRequestMap().get("Guid");
        if(guid == null){
            guid = FacesContext.getCurrentInstance().
                    getExternalContext().getRequestParameterMap().get("Guid");
            if(guid == null) guid = survey.getUuid();
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Guid", guid);
        }
        if(!guid.equals(survey.getUuid())){
            init();
        }
        return survey;
    }

    /**
     * @param survey the survey to set
     */
    public void setSurvey(AppointmentPreperation survey) {
        this.survey = survey;
    }

    /**
     * @return the MAX_STEPS
     */
    public int getMAX_STEPS() {
        return MAX_STEPS;
    }

    public String saveSymptom() {
        surveyFacade.saveSurvey(survey);
        initSurvey();
        return "survey.xhtml";
    }

    public String cancel() {
        initSurvey();
        return "survey.xhtml";
    }
}
