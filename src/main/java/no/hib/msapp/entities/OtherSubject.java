package no.hib.msapp.entities;

public class OtherSubject {
    private String uuid;
    private String name;

    public OtherSubject(String name) {
        this.name = name;
    }

    public OtherSubject(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public OtherSubject() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OtherSubject subject = (OtherSubject) o;

        return name != null ? name.equals(subject.name) : subject.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
