package no.hib.msapp.entities;

public class BankIdUser {
    private String ssn;
    private String password;

    public BankIdUser(String ssn, String password) {
        this.ssn = ssn;
        this.password = password;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
