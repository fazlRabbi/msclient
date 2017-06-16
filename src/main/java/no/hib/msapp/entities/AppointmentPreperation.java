package no.hib.msapp.entities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AppointmentPreperation implements Comparable<AppointmentPreperation>{

    private String uuid;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String needForConsultation;
    private List<Symptom> symptoms;
    private String symptomListUpdated;
    private String hasSideEffects;
    private String newSideEffectsDegree;
    private String oldSideEffectsDegree;
    private String sideEffectsNote;
    private boolean sideEffectsAreImportant;
    private String sideEffectsUpdated;
    private List<OtherSubject> otherSubjects;
    private String otherSubjectsNote;
    private String appointmentUuid;
    private Locale local = new Locale("no");

    public AppointmentPreperation(String appointmentUuid, LocalDate appointmentDate, LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
        this.appointmentDate = appointmentDate;
        this.symptoms = new ArrayList<>();
        this.otherSubjects = new ArrayList<>();
        this.appointmentUuid = appointmentUuid;
    }

    public AppointmentPreperation() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFormattedTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd MMMMMMMMMM YYYY.", local);

        Calendar c = Calendar.getInstance();
        c.set(appointmentDate.getYear(), appointmentDate.getMonthValue() - 1,
                appointmentDate.getDayOfMonth(), appointmentTime.getHour(), appointmentTime.getMinute());
        return dateFormat.format(c.getTime());
    }

    public String getFormattedTime(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format, local);
        Calendar c = Calendar.getInstance();
        c.set(appointmentDate.getYear(), appointmentDate.getMonthValue() - 1,
                appointmentDate.getDayOfMonth(), appointmentTime.getHour(), appointmentTime.getMinute());
        return dateFormat.format(c.getTime());
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getNeedForConsultation() {
        return needForConsultation;
    }

    public void setNeedForConsultation(String needForConsultation) {
        this.needForConsultation = needForConsultation;
    }

    public List<Symptom> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(List<Symptom> symptoms) {
        this.symptoms = symptoms;
    }

    public String getSymptomListUpdated() {
        return symptomListUpdated;
    }

    public void setSymptomListUpdated(String symptomListUpdated) {
        this.symptomListUpdated = symptomListUpdated;
    }

    public String getHasSideEffects() {
        return hasSideEffects;
    }

    public void setHasSideEffects(String hasSideEffects) {
        this.hasSideEffects = hasSideEffects;
    }

    public String getNewSideEffectsDegree() {
        return newSideEffectsDegree;
    }

    public void setNewSideEffectsDegree(String newSideEffectsDegree) {
        this.newSideEffectsDegree = newSideEffectsDegree;
    }

    public String getOldSideEffectsDegree() {
        return oldSideEffectsDegree;
    }

    public void setOldSideEffectsDegree(String oldSideEffectsDegree) {
        this.oldSideEffectsDegree = oldSideEffectsDegree;
    }

    public String getSideEffectsNote() {
        return sideEffectsNote;
    }

    public void setSideEffectsNote(String sideEffectsNote) {
        this.sideEffectsNote = sideEffectsNote;
    }

    public boolean isSideEffectsAreImportant() {
        return sideEffectsAreImportant;
    }

    public void setSideEffectsAreImportant(boolean sideEffectsAreImportant) {
        this.sideEffectsAreImportant = sideEffectsAreImportant;
    }

    public String getSideEffectsUpdated() {
        return sideEffectsUpdated;
    }

    public void setSideEffectsUpdated(String sideEffectsUpdated) {
        this.sideEffectsUpdated = sideEffectsUpdated;
    }

    public List<OtherSubject> getOtherSubjects() {
        return otherSubjects;
    }

    public void setOtherSubjects(List<OtherSubject> otherSubjects) {
        this.otherSubjects = otherSubjects;
    }

    public String getOtherSubjectsNote() {
        return otherSubjectsNote;
    }

    public void setOtherSubjectsNote(String otherSubjectsNote) {
        this.otherSubjectsNote = otherSubjectsNote;
    }

    public String getAppointmentUuid() {
        return appointmentUuid;
    }

    public void setAppointmentUuid(String appointmentUuid) {
        this.appointmentUuid = appointmentUuid;
    }

    @Override
    public int compareTo(AppointmentPreperation o) {
         if(this.getAppointmentDate().isBefore(o.getAppointmentDate())){
            return -1;
        }
        if(this.getAppointmentDate().isAfter(o.getAppointmentDate())){
            return 1;
        }
        if(this.getAppointmentTime().isBefore(o.getAppointmentTime())){
            return -1;
        }
        if(this.getAppointmentTime().isAfter(o.getAppointmentTime())){
            return 1;
        }
        return 0;
    }

    public void addOtherSubject(OtherSubject other) {
        otherSubjects.add(other);
    }

    public void addSymptom(Symptom symptom) {
        symptoms.add(symptom);
    }
}
