package kz.danilov.backend.dto;

import javax.validation.constraints.NotEmpty;

public class PersonOnlyWithNameDTO {
    @NotEmpty(message = "Name should not be empty")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
