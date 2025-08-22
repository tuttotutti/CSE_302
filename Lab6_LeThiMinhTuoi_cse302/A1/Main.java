public class Main {

    public static void main(String[] args) {
        int virtualAddress = 19986;
        int pageSize = 4096;

        int pageNumber = virtualAddress / pageSize;
        int offset = virtualAddress % pageSize;

        System.out.println("The address " + virtualAddress);
        System.out.println("Page number: " + pageNumber);
        System.out.println("Offset: " + offset);
    }
}
