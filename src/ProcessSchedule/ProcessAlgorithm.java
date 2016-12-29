/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProcessSchedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 * 
 * @author Chris
 */
public class ProcessAlgorithm {
    private final LinkedList<Process> processSchedule;
    private final LinkedList<Process> arrivalQueue;
    private double totalProcessTime;
    private double averageTurnAroundTime;
    private double averageWaitingTime;
    
    /**
     * The constructor assumes that <code>processList</code> is not null and empty.
     * @param processList the list of process to be scheduled on a criteria.
     */
    public ProcessAlgorithm(LinkedList<Process> processList){
        this.processSchedule = new LinkedList<>();
        this.arrivalQueue = new LinkedList<>();
        processList.forEach((process) -> {
            arrivalQueue.add(new Process(process));
        });
        this.averageTurnAroundTime = this.averageWaitingTime = this.totalProcessTime = 0;
    }
    
    /**
     * This function asserts that the output of the algorithm will be stored into the <code>processSchedule</code>
     * and that the <code>arrivalQueue</code> will be emptied long after.
     * @param type the non-preemptive algorithm to execute "firstComeFirstServe" | "shortestJobFirst" | "npPriority" (Case Insensitive)
     */
    public void nonPreemptive(String type){
        this.totalProcessTime = this.averageTurnAroundTime = this.averageWaitingTime = 0;
        ArrayList<Double> tatList = new ArrayList<>();
        ArrayList<Double> wtList = new ArrayList<>();
        LinkedList<Process> readyQueue = new LinkedList<>();
        
        Collections.sort(this.arrivalQueue, new ProcessComparator("firstcomefirstserve"));
        readyQueue.add(new Process(this.arrivalQueue.poll()));      
        
        while(!(this.arrivalQueue.isEmpty() && readyQueue.isEmpty())){
            for(int i = 0; i < this.arrivalQueue.size(); i++){
                if(processSchedule.isEmpty() && readyQueue.getFirst().getArrivalTime() == this.arrivalQueue.get(i).getArrivalTime()){
                    readyQueue.add(new Process(this.arrivalQueue.poll()));
                }else if(this.arrivalQueue.get(i).getArrivalTime() <= this.totalProcessTime){
                    readyQueue.add(new Process(this.arrivalQueue.poll()));
                }
            }
            
            Collections.sort(readyQueue, new ProcessComparator(type)); 
            totalProcessTime += readyQueue.getFirst().getBurstTime();
            this.processSchedule.add(
                new Process(readyQueue.poll())
                    .setCompletionTime(this.totalProcessTime)
                    .computeTurnAroundTime()
                    .computeWaitingTime()
            );
        }    
        
        processSchedule.stream().map((process) -> {
            tatList.add(process.getTurnAroundTime());
            return process;
        }).forEachOrdered((process) -> {
            wtList.add(process.getWaitingTime());
        });
        this.averageTurnAroundTime = this.computeAverageTime(tatList);
        this.averageWaitingTime = this.computeAverageTime(wtList);
    }
    
    /**
     * The function is under development and should not be used.
     * @deprecated
     */
    @Deprecated
    public void Preemptive(){
        this.totalProcessTime = this.averageTurnAroundTime = this.averageWaitingTime = 0;
        ArrayList<Double> tatList = new ArrayList<>();
        ArrayList<Double> wtList = new ArrayList<>();
        LinkedList<Process> readyQueue = new LinkedList<>();
    }
    
    private double computeAverageTime(ArrayList<Double> timeList){
        double totalTime = 0;
        totalTime = timeList.stream().map((time) -> time).reduce(totalTime, (accumulator, _item) -> accumulator + _item);
        return totalTime/timeList.size();
    }

    @Override
    public String toString(){
        return "ProcessOutputList:" + processSchedule.toString() +
               "Total Process Time Consumed(" + Double.toString(this.totalProcessTime) + ")\n" +
               "Average Turn Around Time(" + Double.toString(this.averageTurnAroundTime) + ")\n" +
               "Average Waiting Time(" + Double.toString(this.averageWaitingTime) + ")\n";
    }
    
    /**
     * 
     * @return Null if the <code>nonPreemptive</code> function was not even invoked once.
     */
    public LinkedList<Process> getProcessSchedule() {
        return processSchedule;
    }

    /**
     * 
     * @return 0 if the <code>nonPreemptive</code> function was not even invoked once.
     */
    public double getTotalProcessTime() {
        return totalProcessTime;
    }

    /**
     * 
     * @return 0 if the <code>nonPreemptive</code> function was not even invoked once.
     */
    public double getAverageTurnAroundTime() {
        return averageTurnAroundTime;
    }

    /**
     * 
     * @return 0 if the <code>nonPreemptive</code> function was not even invoked once.
     */
    public double getAverageWaitingTime() {
        return averageWaitingTime;
    }
}
