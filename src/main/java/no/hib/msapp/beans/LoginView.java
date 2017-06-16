/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hib.msapp.beans;

import no.hib.msapp.RESTClient.LoginFacade;
import no.hib.msapp.entities.BankIdUser;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.util.regex.Pattern;

/**
 * @author Leif Arne
 */
@Named(value = "loginView")
@ManagedBean
@RequestScoped
public class LoginView {

    /**
     * Creates a new instance of LoginView
     */
    public LoginView() {
    }

    private String ssn;
    private String password;
    private LoginFacade loginFacade = new LoginFacade();

    public String login() {

        if (Pattern.matches("^[0-9]{11}$", ssn)) {
            BankIdUser user = new BankIdUser(ssn, password);
            if (loginFacade.validPatient(user)) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ssn", ssn);
                FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put("survey", null);
                return "myPage.xhtml";
            } else {
                FacesContext.getCurrentInstance().addMessage("submitForm",
                        new FacesMessage("Ugyldig personnummer eller passord"));
                return null;
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("submitForm:fnr",
                    new FacesMessage("Vennligst skriv inn et gyldig personsnummer (11 siffer)"));

            return null;
        }
    }


    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the ssn
     */
    public String getSsn() {
        return ssn;
    }

    /**
     * @param ssn the ssn to set
     */
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

}
