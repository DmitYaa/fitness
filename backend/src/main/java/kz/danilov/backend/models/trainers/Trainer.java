package kz.danilov.backend.models.trainers;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * User: Nikolai Danilov
 * Date: 31.07.2023
 */
@Entity
@Table(name = "Trainer")
public class Trainer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "person_id")
    private int personId;

    @Column(name = "rating")
    private double rating;

    @OneToMany(mappedBy = "trainer")
    private List<Exercise> exercises;

    @OneToMany(mappedBy = "trainer")
    private List<Task> tasks;

    @OneToMany(mappedBy = "trainer")
    private List<Training> trainings;

    @OneToMany(mappedBy = "trainer")
    private List<Program> programs;


    public Trainer() {
    }

    public Trainer(int id, int personId, double rating, List<Exercise> exercises, List<Task> tasks, List<Training> trainings, List<Program> programs) {
        this.id = id;
        this.personId = personId;
        this.rating = rating;
        this.exercises = exercises;
        this.tasks = tasks;
        this.trainings = trainings;
        this.programs = programs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Training> getTrainings() {
        return trainings;
    }

    public void setTrainings(List<Training> trainings) {
        this.trainings = trainings;
    }

    public List<Program> getPrograms() {
        return programs;
    }

    public void setPrograms(List<Program> programs) {
        this.programs = programs;
    }
}
