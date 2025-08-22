import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CMA cma = new CMA();
        Scanner keyboard = new Scanner(System.in);
        String command = "";

        while (command.equalsIgnoreCase("X") == false) {
            System.out.print("Allocation>");
            String line = keyboard.nextLine();
            String[] components = line.split("\\s+");

            command = components[0].trim().toLowerCase();
            int size;
            String name;
            System.out.println(command);

            switch (command) {
                case "size":
                    size = Integer.parseInt(components[1]);
                    cma.setMemorySize(size);
                    break;

                case "rq":
                    size = Integer.parseInt(components[2]);
                    name = components[1].trim().toUpperCase();
                    String algorithm = components[3].trim().toUpperCase();
                    if (algorithm.equals("F")) {
                        cma.firstFitRequest(name, size);
                    }
                    if (algorithm.equals("B")) {
                        cma.bestFitRequest(name, size);
                    }
                    if (algorithm.equals("W")) {
                        cma.worstFitRequest(name, size);
                    }
                    break;

                case "rl":
                    boolean res = cma.release(components[1].trim().toUpperCase());
                    if (res == false) {
                        System.out.println("Unknown process name.");
                    }
                    break;

                case "c":
                    cma.compact();
                    System.out.println("Compacted.");
                    break;

                case "stat":
                    String result = cma.toString();
                    System.out.println(result);
                    break;

                case "x":
                    break;

                default:
                    System.out.println("Invalid Command.");
            }
        }
    }
}
