package kz.danilov.backend.services.trainers;

import kz.danilov.backend.models.trainers.Task;
import kz.danilov.backend.repositories.trainers.TasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: Nikolai Danilov
 * Date: 01.08.2023
 */
@Service
@Transactional(readOnly = true)
public class TasksService {

    private final TasksRepository tasksRepository;

    @Autowired
    public TasksService(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    @Transactional
    public Task save(Task task) {
        return tasksRepository.save(task);
    }

    @Transactional
    public void deleteAll() {
        tasksRepository.deleteAll();
    }
}
