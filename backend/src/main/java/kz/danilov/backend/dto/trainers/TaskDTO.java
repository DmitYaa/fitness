package kz.danilov.backend.dto.trainers;

import kz.danilov.backend.models.trainers.Exercise;

/**
 * User: Nikolai Danilov
 * Date: 04.01.2024
 */
public class TaskDTO {
    private int id;
    private String name;
    private String description;
    private Exercise exercise;
    private int count;
    private byte typeCount;

    public TaskDTO() {
    }

    public TaskDTO(int id, String name, String description, Exercise exercise, int count, byte typeCount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.exercise = exercise;
        this.count = count;
        this.typeCount = typeCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
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
