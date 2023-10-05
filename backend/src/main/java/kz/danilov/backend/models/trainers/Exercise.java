package kz.danilov.backend.models.trainers;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * User: Nikolai Danilov
 * Date: 31.07.2023
 */
@Entity
@Table(name = "Exercise")
public class Exercise {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Name should not be empty")
    private String name;

    @Column(name = "muscle")
    private String muscle;

    @Column(name = "description")
    private String description;

    @Column(name = "video")
    private String video;

    @Column(name = "image")
    private String image;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @OneToMany(mappedBy = "exercise")
    private List<Task> tasks;

    public Exercise() {
    }

    public Exercise(int id, String name, String muscle, String description, String video, String image, Trainer trainer, List<Task> tasks) {
        this.id = id;
        this.name = name;
        this.muscle = muscle;
        this.description = description;
        this.video = video;
        this.image = image;
        this.trainer = trainer;
        this.tasks = tasks;
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

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", muscle='" + muscle + '\'' +
                ", description='" + description + '\'' +
                ", video='" + video + '\'' +
                ", image='" + image + '\'' +
                ", trainer=" + trainer +
                ", tasks=" + tasks +
                '}';
    }
}
