package rut.miit.repairservice.models.enums;

public enum SpecializationType {
    PHONES(1),        // Телефоны
    COMPUTERS(2),     // Компьютеры
    TELEVISIONS(3),   // Телевизоры
    APPLIANCES(4),    // Бытовая техника
    UNIVERSAL(5);

    private final int specializationCode;

    private SpecializationType(int specializationCode) {
        this.specializationCode = specializationCode;
    }

    public int getSpecializationCode() {
        return specializationCode;
    }
}
