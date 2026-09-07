package za.ac.cput.factory;

import za.ac.cput.domain.Facility;
import za.ac.cput.domain.FacilityType;

public class FacilityFactory {
    public static Facility createFacility(String name,
                                          FacilityType type,
                                          String address,
                                          String contactNumber,
                                          String contactPerson) {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Facility name is required");

        if (type == null)
            throw new IllegalArgumentException("Facility type is required");

        if (address == null || address.isEmpty())
            throw new IllegalArgumentException("Address is required");

        if (contactNumber == null || contactNumber.isEmpty())
            throw new IllegalArgumentException("Contact number is required");

        if (contactPerson == null || contactPerson.isEmpty())
            throw new IllegalArgumentException("Contact person is required");

        return Facility.builder()
                .name(name)
                .type(type)
                .address(address)
                .contactNumber(contactNumber)
                .contactPerson(contactPerson)
                .build();
    }
}
