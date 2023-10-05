package kz.danilov.backend.dto;


import javax.validation.constraints.NotEmpty;

public class PersonDTO {
    @NotEmpty(message = "Name should not be empty")
    private String name;
    @NotEmpty(message = "Password should not be empty")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
