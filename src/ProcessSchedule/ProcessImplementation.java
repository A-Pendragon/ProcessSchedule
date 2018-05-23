package ProcessSchedule;

import java.util.LinkedList;

public interface ProcessImplementation {
    
    public LinkedList<Process> addValuesToProcess();
    public void doProcessOperation(ProcessOperation pa);
    public void doProcessOperation(ProcessOperation pa, double timeQuantum);
}
