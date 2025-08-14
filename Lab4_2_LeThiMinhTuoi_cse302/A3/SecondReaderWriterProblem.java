import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SecondReaderWriterProblem implements ReaderWriter {
    private int readerCount = 0;
    private int writerCount = 0;
    private int waitingWriterCount = 0;

    private ReentrantLock lock = new ReentrantLock();
    private Condition readCond = this.lock.newCondition();
    private Condition writeCond = this.lock.newCondition();

    @Override
    public void readEnter() {
        try {
            this.lock.lockInterruptibly();
            try {
                while (writerCount > 0 || waitingWriterCount > 0) {
                    System.out.println(
                            "The reader is waiting because there are " + this.waitingWriterCount
                                    + " writers waiting or " + this.writerCount + " writers writing.");
                    readCond.await();
                }
                System.out.println("The reader is reading.");
                readerCount++;
            } finally {
                this.lock.unlock();
            }
        } catch (InterruptedException e) {
        }
    }

    @Override
    public void readExit() {
        try {
            this.lock.lockInterruptibly();
            try {
                System.out.println("A reader has finished reading.");
                readerCount--;
                if (readerCount == 0) {
                    System.out.println("A writer is released to write.");
                    writeCond.signal();
                }
            } finally {
                this.lock.unlock();
            }
        } catch (InterruptedException e) {
        }
    }

    @Override
    public void writeEnter() {
        try {
            this.lock.lockInterruptibly();
            try {
                waitingWriterCount++;
                while (readerCount > 0 || writerCount > 0) {
                    System.out.println(
                            this.readerCount + " readers are reading, " + this.writerCount + " writers are writing.");
                    writeCond.await();
                }
                waitingWriterCount--;
                System.out.println("The writer is writing.");
                writerCount++;
            } finally {
                this.lock.unlock();
            }
        } catch (InterruptedException e) {
        }
    }

    @Override
    public void writeExit() {
        try {
            this.lock.lockInterruptibly();
            try {
                System.out.println("A writer has finished writing.");
                writerCount--;
                if (waitingWriterCount > 0) {
                    System.out.println("A writer is released to access.");
                    writeCond.signal();
                } else {
                    System.out.println("All readers are released to access.");
                    readCond.signalAll();
                }
            } finally {
                this.lock.unlock();
            }
        } catch (InterruptedException e) {
        }
    }
}
