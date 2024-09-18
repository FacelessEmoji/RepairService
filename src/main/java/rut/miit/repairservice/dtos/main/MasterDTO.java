package rut.miit.repairservice.dtos.main;

import rut.miit.repairservice.dtos.base.TimestampedDTO;
import rut.miit.repairservice.models.enums.SpecializationType;

public class MasterDTO extends TimestampedDTO {
    private String firstName;
    private String phoneNumber;
    private SpecializationType specialization;

    public MasterDTO(String firstName, String phoneNumber, SpecializationType specialization) {
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.specialization = specialization;
    }

    public MasterDTO() {
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

    public SpecializationType getSpecialization() {
        return specialization;
    }

    public void setSpecialization(SpecializationType specialization) {
        this.specialization = specialization;
    }

    @Override
    public String toString() {
        return "MasterDTO{" +
                "firstName='" + firstName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", specialization=" + specialization +
                '}';
    }
}
