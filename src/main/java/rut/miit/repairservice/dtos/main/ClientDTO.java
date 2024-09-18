package rut.miit.repairservice.dtos.main;

import rut.miit.repairservice.dtos.base.TimestampedDTO;

public class ClientDTO extends TimestampedDTO{
    private String firstName;
    private String phoneNumber;
    private String email;

    public ClientDTO(String firstName, String phoneNumber, String email) {
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public ClientDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ClientDTO{" +
                "firstName='" + firstName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
