/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProcessSchedule;

/**
 *
 * @author Chris
 */
public class Process {
    private int processNo;
    private double arrivalTime;
    private double burstTime;
    private int priority;
    private double timeQuantum;
    private double completionTime;
    private double turnAroundTime;
    private double waitingTime;
    
    public Process(){
        this.processNo = this.priority = 0;
        this.arrivalTime = this.burstTime = this.completionTime = this.turnAroundTime = this.waitingTime = 0;
    }

    public Process(int processNo, double arrivalTime, double burstTime) {
        this.processNo = processNo;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = 0;
        this.timeQuantum = this.completionTime = this.turnAroundTime = this.waitingTime = 0;
    }

    public Process(int processNo, double arrivalTime, double burstTime, int priority) {
        this.processNo = processNo;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.timeQuantum = this.completionTime = this.turnAroundTime = this.waitingTime = 0;
    }
    
    public Process(int processNo, double arrivalTime, double burstTime, int priority, double timeQuantum){    
        this.processNo = processNo;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.timeQuantum = timeQuantum; 
        this.completionTime = this.turnAroundTime = this.waitingTime = 0;
    }
    
    public Process(Process p){
        this.processNo = p.processNo;
        this.arrivalTime = p.arrivalTime;
        this.burstTime = p.burstTime;
        this.priority = p.priority;
        this.timeQuantum = p.timeQuantum;
        this.completionTime = p.completionTime;
        this.turnAroundTime = p.turnAroundTime;
        this.waitingTime = p.waitingTime;
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
        this.arrivalTime = arrivalTime;
    }

    public double getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(double burstTime) {
        this.burstTime = burstTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public double getTimeQuantum() {
        return timeQuantum;
    }

    public void setTimeQuantum(double timeQuantum) {
        this.timeQuantum = timeQuantum;
    }

    public double getCompletionTime() {
        return completionTime;
    }

    public Process setCompletionTime(double completionTime) {
        this.completionTime = completionTime;
        return this;
    }

    public double getTurnAroundTime() {
        return turnAroundTime;
    }

    public Process computeTurnAroundTime() {
        this.turnAroundTime = this.completionTime - this.arrivalTime;
        return this;
    }

    public double getWaitingTime() {
        return waitingTime;
    }

    public Process computeWaitingTime() {
        this.waitingTime = this.turnAroundTime - this.burstTime;
        return this;
    }
    
    /**
     *
     * @return
     */
    @Override
    public String toString(){
        return "\n>> PNO: " + Integer.toString(this.processNo) + "\n" +
               ">> AT(" + Double.toString(this.arrivalTime) + ") BT(" + Double.toString(this.burstTime) + ") Priority(" + Integer.toString(this.priority) + ") QT(" + Double.toString(this.timeQuantum) + ")\n" +
               ">> CT(" + Double.toString(this.completionTime) + ") TAT(" + Double.toString(this.turnAroundTime) + ") WT(" + Double.toString(this.waitingTime) + ")\n";
    }
}
