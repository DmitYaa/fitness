package kz.danilov.backend.models.trainers;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * User: Nikolai Danilov
 * Date: 31.07.2023
 */
@Entity
@Table(name = "Task")
public class Task {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Name should not be empty")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    @Column(name = "count")
    private int count;

    /**
     * 0 - кол-во повторений
     * 1 - секунд нужно заниматься
     * 2 - минут нужно заниматься
     */
    @Column(name = "type_count")
    private byte typeCount;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @ManyToMany(mappedBy = "tasks")
    private List<Training> trainings;

    public Task() {
    }

    public Task(int id, String name, String description, Exercise exercise, int count, byte typeCount, Trainer trainer, List<Training> trainings) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.exercise = exercise;
        this.count = count;
        this.typeCount = typeCount;
        this.trainer = trainer;
        this.trainings = trainings;
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

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public List<Training> getTrainings() {
        return trainings;
    }

    public void setTrainings(List<Training> trainings) {
        this.trainings = trainings;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", exercise=" + exercise +
                ", count=" + count +
                ", typeCount=" + typeCount +
                ", trainer=" + trainer +
                ", trainings=" + trainings +
                '}';
    }
}
