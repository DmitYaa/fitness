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

    @Column(name = "name")
    @NotEmpty(message = "Name should not be empty")
    private String name;

    @Column(name = "password")
    @NotEmpty(message = "Password should not be empty")
    private String password;

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

    public Trainer(int id, String name, String password, double rating, List<Exercise> exercises, List<Task> tasks, List<Training> trainings, List<Program> programs) {
        this.id = id;
        this.name = name;
        this.password = password;
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

    @Override
    public String toString() {
        return "Trainer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", rating=" + rating +
                ", exercises=" + exercises +
                ", tasks=" + tasks +
                ", trainings=" + trainings +
                ", programs=" + programs +
                '}';
    }
}
