package kz.danilov.backend.dto.trainers;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

/**
 * User: Nikolai Danilov
 * Date: 31.12.2023
 */
public class NewExerciseDTO {
    private String name;
    private String muscle;
    private String description;

    public NewExerciseDTO() {
    }

    public NewExerciseDTO(String name, String muscle, String description) {
        this.name = name;
        this.muscle = muscle;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMuscle() {
        return muscle;
    }

    public void setMuscle(String muscle) {
        this.muscle = muscle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "NewExerciseDTO{" +
                "name='" + name + '\'' +
                ", muscle='" + muscle + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
