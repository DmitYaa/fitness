package kz.danilov.backend.dto.trainers;

import java.util.Arrays;

/**
 * User: Nikolai Danilov
 * Date: 31.12.2023
 */
public class NewExerciseDTO {
    private String name;
    private String muscle;
    private String description;
    private byte[] image;
    private byte[] video;

    public NewExerciseDTO() {
    }

    public NewExerciseDTO(String name, String muscle, String description, byte[] image, byte[] video) {
        this.name = name;
        this.muscle = muscle;
        this.description = description;
        this.image = image;
        this.video = video;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public byte[] getVideo() {
        return video;
    }

    public void setVideo(byte[] video) {
        this.video = video;
    }

    @Override
    public String toString() {
        return "NewExerciseDTO{" +
                "name='" + name + '\'' +
                ", muscle='" + muscle + '\'' +
                ", description='" + description + '\'' +
                ", image==null? " + (image == null) +
                ", video==null? " + (video == null) +
                '}';
    }
}
