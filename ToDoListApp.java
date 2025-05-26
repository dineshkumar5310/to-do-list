import java.io.*;
import java.util.*;

public class ToDoListApp {

    static List<Task> tasks = new ArrayList<>();
    static final String FILE_NAME = "todo_tasks.txt";

    public static void main(String[] args) {
        // Load tasks from the file at the beginning
        loadTasksFromFile();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== To-Do List Application =====");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Update Task");
            System.out.println("4. Delete Task");
            System.out.println("5. Mark Task as Completed");
            System.out.println("6. Mark Task as Incomplete");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
           
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline character
           
            switch (choice) {
                case 1:
                    addTask(scanner);
                    break;
                case 2:
                    viewTasks();
                    break;
                case 3:
                    updateTask(scanner);
                    break;
                case 4:
                    deleteTask(scanner);
                    break;
                case 5:
                    markTaskAsCompleted(scanner);
                    break;
                case 6:
                    markTaskAsIncomplete(scanner);
                    break;
                case 7:
                    // Save tasks to the file before exiting
                    saveTasksToFile();
                    System.out.println("Exiting the program. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    // Method to add a new task
    private static void addTask(Scanner scanner) {
        System.out.print("Enter task description: ");
        String description = scanner.nextLine();
        Task newTask = new Task(description);
        tasks.add(newTask);
        System.out.println("Task added: " + description);
    }

    // Method to view all tasks
    private static void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
        } else {
            System.out.println("\n=== All Tasks ===");
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                String status = task.isCompleted() ? "Completed" : "Incomplete";
                System.out.println((i + 1) + ". " + task.getDescription() + " - " + status);
            }
        }
    }

    // Method to update a task's description
    private static void updateTask(Scanner scanner) {
        System.out.print("Enter task number to update: ");
        int taskNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        if (taskNumber < 1 || taskNumber > tasks.size()) {
            System.out.println("Invalid task number.");
        } else {
            Task task = tasks.get(taskNumber - 1);
            System.out.print("Enter new description for task: ");
            String newDescription = scanner.nextLine();
            task.setDescription(newDescription);
            System.out.println("Task updated to: " + newDescription);
        }
    }

    // Method to delete a task
    private static void deleteTask(Scanner scanner) {
        System.out.print("Enter task number to delete: ");
        int taskNumber = scanner.nextInt();

        if (taskNumber < 1 || taskNumber > tasks.size()) {
            System.out.println("Invalid task number.");
        } else {
            tasks.remove(taskNumber - 1);
            System.out.println("Task deleted.");
        }
    }

    // Method to mark a task as completed
    private static void markTaskAsCompleted(Scanner scanner) {
        System.out.print("Enter task number to mark as completed: ");
        int taskNumber = scanner.nextInt();

        if (taskNumber < 1 || taskNumber > tasks.size()) {
            System.out.println("Invalid task number.");
        } else {
            tasks.get(taskNumber - 1).setCompleted(true);
            System.out.println("Task marked as completed.");
        }
    }

    // Method to mark a task as incomplete
    private static void markTaskAsIncomplete(Scanner scanner) {
        System.out.print("Enter task number to mark as incomplete: ");
        int taskNumber = scanner.nextInt();

        if (taskNumber < 1 || taskNumber > tasks.size()) {
            System.out.println("Invalid task number.");
        } else {
            tasks.get(taskNumber - 1).setCompleted(false);
            System.out.println("Task marked as incomplete.");
        }
    }

    // Method to load tasks from the file
    private static void loadTasksFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split each line by comma (to separate description and completion status)
                String[] taskData = line.split(",");
                String description = taskData[0];
                boolean isCompleted = Boolean.parseBoolean(taskData[1]);

                // Create a new Task object and add it to the tasks list
                tasks.add(new Task(description, isCompleted));
            }
        } catch (IOException e) {
            System.out.println("No previous tasks found, starting fresh.");
        }
    }

    // Method to save tasks to a file
    private static void saveTasksToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Task task : tasks) {
                // Write task description and completion status to the file
                bw.write(task.getDescription() + "," + task.isCompleted());
                bw.newLine(); // Move to the next line
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks to file.");
        }
    }

    // Task class representing each task
    static class Task {
        private String description;
        private boolean completed;

        public Task(String description) {
            this(description, false);
        }

        public Task(String description, boolean completed) {
            this.description = description;
            this.completed = completed;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }
    }
}
