package no.hib.msapp.entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
    private String uuid;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private AppointmentPreperation appointmentPreperation;
    private String patientSsn;
    private String doctorSsn;

    public Appointment(LocalDate appointmentDate, LocalTime appointmentTime, AppointmentPreperation appointmentPreperation, String patientSsn, String doctorSsn) {
        this.appointmentTime = appointmentTime;
        this.appointmentDate = appointmentDate;
        this.appointmentPreperation = appointmentPreperation;
        this.patientSsn = patientSsn;
        this.doctorSsn = doctorSsn;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public AppointmentPreperation getAppointmentPreperation() {
        return appointmentPreperation;
    }

    public void setAppointmentPreperation(AppointmentPreperation appointmentPreperation) {
        this.appointmentPreperation = appointmentPreperation;
    }

    public String getPatientSsn() {
        return patientSsn;
    }

    public void setPatientSsn(String patientSsn) {
        this.patientSsn = patientSsn;
    }

    public String getDoctorSsn() {
        return doctorSsn;
    }

    public void setDoctorSsn(String doctorSsn) {
        this.doctorSsn = doctorSsn;
    }
}
