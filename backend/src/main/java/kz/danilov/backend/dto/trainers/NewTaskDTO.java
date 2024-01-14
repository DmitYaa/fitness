package kz.danilov.backend.dto.trainers;

import kz.danilov.backend.models.trainers.Exercise;

/**
 * User: Nikolai Danilov
 * Date: 14.01.2024
 */
public class NewTaskDTO {
    private String name;
    private String description;
    private int idExercise;
    private int count;
    private byte typeCount;

    public NewTaskDTO() {}

    public NewTaskDTO(String name, String description, int idExercise, int count, byte typeCount) {
        this.name = name;
        this.description = description;
        this.idExercise = idExercise;
        this.count = count;
        this.typeCount = typeCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdExercise() {
        return idExercise;
    }

    public void setIdExercise(int idExercise) {
        this.idExercise = idExercise;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public byte getTypeCount() {
        return typeCount;
    }

    public void setTypeCount(byte typeCount) {
        this.typeCount = typeCount;
    }
}
