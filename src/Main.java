import model.Status;
import model.Task;
import service.TaskService;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        TaskService taskService = new TaskService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            switch (choice) {

                case 1:
                    System.out.print("Enter task description: ");
                    String description = scanner.nextLine();
                    System.out.println(taskService.addTask(description));
                    break;

                case 2:
                    List<Task> tasks = taskService.getAllTasks();
                    if (tasks.isEmpty()) {
                        System.out.println("No tasks found.");
                    } else {
                        for (Task task : tasks) {
                            System.out.println(
                                    task.getId() + " | " +
                                            task.getDescription() + " | " +
                                            task.getStatus()
                            );
                        }
                    }
                    break;

                case 3:
                    System.out.print("Enter task id: ");
                    Long id = scanner.nextLong();
                    scanner.nextLine();

                    System.out.print("Enter status (TODO, IN_PROGRESS, DONE): ");
                    String statusInput = scanner.nextLine();

                    Status status = Status.valueOf(statusInput.toUpperCase());
                    System.out.println(taskService.updateStatus(id, status));
                    break;

                case 4:
                    System.out.print("Enter task id: ");
                    Long deleteId = scanner.nextLong();
                    System.out.println(taskService.deleteTaskById(deleteId));
                    break;

                case 0:
                    System.out.println("Goodbye 👋");
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=== TASK MANAGER ===");
        System.out.println("1. Add Task");
        System.out.println("2. Show All Tasks");
        System.out.println("3. Update Task Status");
        System.out.println("4. Delete Task");
        System.out.println("0. Exit");
        System.out.print("Choose: ");
    }
}
