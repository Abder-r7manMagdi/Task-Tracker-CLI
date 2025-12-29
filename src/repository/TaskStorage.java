package repository;

import model.Status;
import model.Task;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskStorage {

    private static final String FILE_NAME = "tasks.txt";

    /**
     * Ensures that the storage file exists.
     * If not, it creates a new empty file.
     */
    public Boolean ensureFileExists() {
        File file = new File(FILE_NAME);
        try {
            if (!file.exists()) {
                return file.createNewFile();
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Loads tasks from file.
     * Always returns a List (never null).
     */
    public List<Task> loadTasks() {
        ensureFileExists();

        List<Task> tasks = new ArrayList<>();
        File file = new File(FILE_NAME);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;
            while ((line = reader.readLine()) != null) {
                // expected format:
                // id|description|status|createdAt|updatedAt
                String[] parts = line.split("\\|");

                if (parts.length != 5) {
                    continue; // skip corrupted line
                }

                Task task = new Task();
                task.setId(Long.parseLong(parts[0]));
                task.setDescription(parts[1]);
                task.setStatus(Status.valueOf(parts[2]));
                task.setCreatedAt(LocalDate.parse(parts[3]));
                task.setUpdatedAt(LocalDate.parse(parts[4]));

                tasks.add(task);
            }

        } catch (IOException e) {
            // return empty list if reading fails
        }

        return tasks;
    }

    /**
     * Saves all tasks to file.
     * Overwrites existing content.
     */
    public void saveTasks(List<Task> tasks) {
        ensureFileExists();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, false))) {

            for (Task task : tasks) {
                String line =
                        task.getId() + "|" +
                                task.getDescription() + "|" +
                                task.getStatus() + "|" +
                                task.getCreatedAt() + "|" +
                                task.getUpdatedAt();

                writer.write(line);
                writer.newLine();
            }

        } catch (IOException e) {
            // fail silently (storage responsibility only)
        }
    }
}
