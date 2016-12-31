/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProcessSchedule;

import java.util.LinkedList;
import javax.swing.JTable;

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
       
    public Process(Process p){
        this.processNo = p.processNo;
        this.arrivalTime = p.arrivalTime;
        this.burstTime = p.burstTime;
        this.priority = p.priority;
        this.completionTime = p.completionTime;
        this.turnAroundTime = p.turnAroundTime;
        this.waitingTime = p.waitingTime;
    }
    
    /*
    @Override
    public String toString(){
        return "\n>> PNO(" + Integer.toString(this.processNo) + ") "+
               ">> AT(" + Double.toString(this.arrivalTime) + ") BT(" + Double.toString(this.burstTime) + ") Priority(" + Integer.toString(this.priority) + ") " + 
               ">> CT(" + Double.toString(this.completionTime) + ") TAT(" + Double.toString(this.turnAroundTime) + ") WT(" + Double.toString(this.waitingTime) + ") ";
    }
    */
    
    @Override
    public String toString(){
        return Integer.toString(this.processNo) + "\t" +
               Double.toString(this.arrivalTime) + "\t" + 
               Double.toString(this.burstTime) + "\t" + 
               Integer.toString(this.priority) + "\t" + 
               Double.toString(NumericHandlers.roundTo2DecimalPlaces(this.completionTime)) + "\t" + 
               Double.toString(NumericHandlers.roundTo2DecimalPlaces(this.turnAroundTime)) + "\t" + 
               Double.toString(NumericHandlers.roundTo2DecimalPlaces(this.waitingTime)) + "\n";
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
    
    //////////
    
    public static LinkedList<Process> addValuesToProcess(String processType, JTable table) {
        LinkedList<Process> plist = new LinkedList<>();
        
        switch (processType) {
            case "First Come First Serve":
            case "Shortest Job First":
            case "Shortest Remaining Time First":
            case "Round Robin":
                for(int i = 0; i < table.getRowCount(); i++) {
                    plist.add(new Process(TableHandlers.tableValueToInteger(table, i, 0), TableHandlers.tableValueToDouble(table, i, 1), TableHandlers.tableValueToDouble(table, i, 2)));
                }   break;
            case "Non-Preemptive Priority":
            case "Preemptive Priority":
                for(int i = 0; i < table.getRowCount(); i++) {
                    plist.add(new Process(TableHandlers.tableValueToInteger(table, i, 0), TableHandlers.tableValueToDouble(table, i, 1), TableHandlers.tableValueToDouble(table, i, 2), TableHandlers.tableValueToInteger(table, i, 3)));                
                }   break;                            
            default:
                break;
        }
        
        return plist;
    }
    
    public static void doProcessOperation(String processType, ProcessOperation pa) {
        switch (processType) {
            case "First Come First Serve":
                pa.nonPreemptiveSchedule("firstcomefirstserve");
                break;
            case "Shortest Job First":
                pa.nonPreemptiveSchedule("shortestjobfirst");
                break;
            case "Non-Preemptive Priority":
                pa.nonPreemptiveSchedule("nppriority");
                break;
            case "Shortest Remaining Time First":
                pa.preemptiveSchedule("shortestremainingtime");
                break;
            case "Preemptive Priority":
                pa.preemptiveSchedule("ppriority");
                break;
            default:
                break;
        }
    }
}
