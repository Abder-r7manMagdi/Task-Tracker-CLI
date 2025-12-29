package service;

import model.Status;
import model.Task;
import repository.TaskStorage;
import java.time.LocalDate;
import java.util.List;

public class TaskService {

    private final TaskStorage taskStorage;
    private List<Task> tasks;

    public TaskService() {
        this.taskStorage = new TaskStorage();
        this.tasks = taskStorage.loadTasks(); // دايمًا List مش null
    }

    // 1️⃣ get all
    public List<Task> getAllTasks() {
        return tasks;
    }

    // 2️⃣ add task
    public String addTask(String description) {
        Task task = new Task();
        task.setId(generateId());
        task.setDescription(description);
        task.setStatus(Status.TODO);
        task.setCreatedAt(LocalDate.now());
        task.setUpdatedAt(LocalDate.now());

        tasks.add(task);
        taskStorage.saveTasks(tasks);

        return "Task added successfully";
    }

    // 3️⃣ delete by id
    public String deleteTaskById(Long id) {
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                tasks.remove(task);
                taskStorage.saveTasks(tasks);
                return "Task deleted successfully";
            }
        }
        return "Task not found";
    }

    // 4️⃣ update status
    public String updateStatus(Long id, Status status) {
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                task.setStatus(status);
                task.setUpdatedAt(LocalDate.now());
                taskStorage.saveTasks(tasks);
                return "Status updated successfully";
            }
        }
        return "Task not found";
    }

    // 🔹 generate ID
    private Long generateId() {
        if (tasks.isEmpty()) {
            return 1L;
        }
        return tasks.get(tasks.size() - 1).getId() + 1;
    }
}
