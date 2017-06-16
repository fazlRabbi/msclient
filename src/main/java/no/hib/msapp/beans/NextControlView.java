/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hib.msapp.beans;

import no.hib.msapp.RESTClient.SettingsFacade;
import no.hib.msapp.RESTClient.SurveyFacade;
import no.hib.msapp.entities.AppointmentPreperation;

import javax.enterprise.context.Dependent;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author Leif Arne
 */
@Named(value = "nextControlView")
@Dependent
public class NextControlView {

    private Date meetingDate;
    private SurveyFacade surveyFacade;
    private Date answerDate;
    private DateFormat dateFormat;
    private Date now;
    private String uuid;
    private int minWeeks;
    private boolean noNextSurvey = false;

    /**
     * Creates a new instance of NextControlView
     */
    public NextControlView() {
        String ssn = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("ssn");
        surveyFacade = new SurveyFacade(ssn);
        AppointmentPreperation survey = surveyFacade.findNextSurvey();

        if (survey != null) {
            LocalTime appointmentTime = survey.getAppointmentTime();
            LocalDate appointmentDate = survey.getAppointmentDate();

            uuid = survey.getUuid();

            Calendar cal = Calendar.getInstance();
            cal.set(appointmentDate.getYear(), appointmentDate.getMonthValue() - 1,
                    appointmentDate.getDayOfMonth(), appointmentTime.getHour(), appointmentTime.getMinute());

            meetingDate = cal.getTime();

            cal.add(Calendar.DATE, -7);
            answerDate = cal.getTime();
            Locale local = new Locale("no");
            dateFormat = new SimpleDateFormat("dd MMMMMMMMMM YYYY.", local);
            now = Calendar.getInstance().getTime();

            minWeeks = (new SettingsFacade()).getMaxWeeks();
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Guid", survey.getUuid());
        } else {
            noNextSurvey = true;
        }
    }

    public String getStage() {
        if (noNextSurvey) return "noNextSurvey";
        Date nowDate = new Date();
        int maxWeeks = (new SettingsFacade()).getMaxWeeks();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(meetingDate);
        calendar.add(Calendar.WEEK_OF_YEAR, -maxWeeks);
        Date maxDate = calendar.getTime();

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(meetingDate);
        calendar1.add(Calendar.WEEK_OF_YEAR, -1);
        Date oneWeek = calendar1.getTime();


        //Less than 1 week
        if (nowDate.after(oneWeek)) {
            return "dateExpired";
        } //More than 3 weeks
        else if (nowDate.before(maxDate)) {
            return "informAboutSurvey";
        } // between 1 and 3 weeks
        else {
            return "displaySurveyOption";
        }
    }

    /**
     * @return the meetingDate
     */
    public String getMeetingDate() {
        return dateFormat.format(meetingDate);
    }

    /**
     * @return the answerDate
     */
    public String getAnswerDate() {
        return dateFormat.format(answerDate);
    }

    public String getCurrentDate() {
        return dateFormat.format(now);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getMinWeeks() {
        return minWeeks;
    }

    public void setMinWeeks(int minWeeks) {
        this.minWeeks = minWeeks;
    }
}
