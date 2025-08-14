import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        // ReaderWriter rw = new FirstReaderWriterProblem();
        ReaderWriter rw = new SecondReaderWriterProblem();
        List<Reader> readers = new ArrayList<>();
        List<Writer> writers = new ArrayList<>();

        int n = 20;
        for (int i = 0; i < n; i++) {
            Reader r = new Reader(rw);
            readers.add(r);
            r.start();

            Writer w = new Writer(rw);
            writers.add(w);
            w.start();
        }

        for (Reader r : readers) {
            try {
                r.join();
            } catch (InterruptedException e) {
            }
        }

        for (Writer w : writers) {
            try {
                w.join();
            } catch (InterruptedException e) {
            }
        }

        System.out.println("Done.");
    }
}

class Reader extends Thread {
    private ReaderWriter rw;

    public Reader(ReaderWriter rw) {
        this.rw = rw;
    }

    @Override
    public void run() {
        Random rd = new Random();

        try {
            Thread.sleep(rd.nextInt(200));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.rw.readEnter();
        // Reading
        try {
            Thread.sleep(rd.nextInt(200));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.rw.readExit();
    }
}

class Writer extends Thread {
    private ReaderWriter rw;

    public Writer(ReaderWriter rw) {
        this.rw = rw;
    }

    @Override
    public void run() {
        Random rd = new Random();

        try {
            Thread.sleep(rd.nextInt(200));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.rw.writeEnter();
        // Reading
        try {
            Thread.sleep(rd.nextInt(200));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.rw.writeExit();
    }
}
