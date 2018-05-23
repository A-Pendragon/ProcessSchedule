package ProcessSchedule;

public class Process {
    
    private int processNo;
    private double arrivalTime;
    private double burstTime;
    private int priority;
    private double completionTime;
    private double turnAroundTime;
    private double waitingTime;
    public static int precision = 2;
    
    public Process(int processNo, double arrivalTime, double burstTime) {
        this.processNo = processNo;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = 0;
        this.completionTime = this.turnAroundTime = this.waitingTime = 0;
    }
    
    public Process(int processNo, double arrivalTime, double burstTime, int priority) {
        this.processNo = processNo;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.completionTime = this.turnAroundTime = this.waitingTime = 0;
    }
       
    public Process(Process p){
        this.processNo = p.processNo;
        this.arrivalTime = p.arrivalTime;
        this.burstTime = p.burstTime;
        this.priority = p.priority;
        this.completionTime = p.completionTime;
        this.turnAroundTime = p.turnAroundTime;
        this.waitingTime = p.waitingTime;
    }
    
    private double round(double number) {
        int numberOfPrecision = 1;
        
        for (int i = 0; i < Process.precision; i++) {
            numberOfPrecision *= 10;
        }
        return (double)Math.round(number * numberOfPrecision) / numberOfPrecision;
    }
    
    @Override
    public String toString() {
        return Integer.toString(this.processNo) + "\t" +
               Double.toString(this.arrivalTime) + "\t" + 
               Double.toString(this.burstTime) + "\t" + 
               Integer.toString(this.priority) + "\t" + 
               Double.toString(NumericHandlers.round(this.completionTime, 2)) + "\t" + 
               Double.toString(NumericHandlers.round(this.turnAroundTime, 2)) + "\t" + 
               Double.toString(NumericHandlers.round(this.waitingTime, 2)) + "\n";
    }
    
    @Override
    public boolean equals(Object process) {
        if (process instanceof Process){
            Process p = (Process)process;
            return this.hashCode() == p.hashCode();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        
        hash = 67 * hash + this.processNo;
        return hash;
    }
        
    public int getProcessNo() {
        return processNo;
    }

    public void setProcessNo(int processNo) {
        this.processNo = processNo;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = this.round(arrivalTime);
    }

    public double getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(double burstTime) {
        this.burstTime = this.round(burstTime);
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public double getCompletionTime() {
        return completionTime;
    }

    public Process setCompletionTime(double completionTime) {
        this.completionTime = this.round(completionTime);
        return this;
    }

    public double getTurnAroundTime() {
        return turnAroundTime;
    }

    public Process computeTurnAroundTime() {
        this.turnAroundTime = this.round(this.completionTime - this.arrivalTime);
        return this;
    }

    public double getWaitingTime() {
        return waitingTime;
    }

    public Process computeWaitingTime() {
        this.waitingTime = this.round(this.turnAroundTime - this.burstTime);
        return this;
    }
}
