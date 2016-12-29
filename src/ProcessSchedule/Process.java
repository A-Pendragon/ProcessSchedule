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
        this.completionTime = this.turnAroundTime = this.waitingTime = 0;
    }

    public Process(int processNo, double arrivalTime, double burstTime, int priority) {
        this.processNo = processNo;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.completionTime = this.turnAroundTime = this.waitingTime = 0;
    }
    
    public Process(int processNo, double arrivalTime, double burstTime, int priority, double timeQuantum){    
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

    public Process splitProcessBy(String timeUnit){
        if(this.burstTime == 0){
            return null;
        }else{
            Process splitProcess = new Process(this);
            splitProcess.setBurstTime(timeUnit.equalsIgnoreCase("SINGLE_TIME_UNIT") ? 1 : this.burstTime);
            this.burstTime = (timeUnit.equalsIgnoreCase("SINGLE_TIME_UNIT") ? this.burstTime-- : 0);
            return new Process(splitProcess);
        }
    }
    
    @Override
    public String toString(){
        return "\n>> PNO(" + Integer.toString(this.processNo) + ") "+
               ">> AT(" + Double.toString(this.arrivalTime) + ") BT(" + Double.toString(this.burstTime) + ") Priority(" + Integer.toString(this.priority) + ") " + 
               ">> CT(" + Double.toString(this.completionTime) + ") TAT(" + Double.toString(this.turnAroundTime) + ") WT(" + Double.toString(this.waitingTime) + ") ";
    }
    
    @Override
    public boolean equals(Object process){
        if(process instanceof Process){
            Process p = (Process)process;
            return this.hashCode() == p.hashCode();
        }return false;
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
}
