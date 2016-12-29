/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProcessSchedule;

import java.util.Collections;
import java.util.LinkedList;

/**
 *
 * @author Chris
 */
public class ProcessOperation {
    private final LinkedList<Process> processInitStorage;
    private final LinkedList<Process> processComputation;
    private final LinkedList<Process> processSchedule;
    private final LinkedList<Process> arrivalQueue;
    private final LinkedList<Process> requestQueue;
    private double totalProcessTime;
    private double averageTurnAroundTime;
    private double averageWaitingTime;
    public int timeQuantum;
    
    public ProcessOperation(LinkedList<Process> processList, int timeQuantum){
        this.averageTurnAroundTime = this.averageWaitingTime = this.totalProcessTime = 0;
        this.processInitStorage = new LinkedList<>();
        this.processComputation = new LinkedList<>();
        this.processSchedule = new LinkedList<>();
        this.arrivalQueue = new LinkedList<>();
        this.requestQueue = new LinkedList<>();
        processList.forEach((process) -> {
            this.processInitStorage.add(new Process(process));
        });
        this.timeQuantum = timeQuantum;
    }
    
    private void reset(){
        this.averageTurnAroundTime = this.averageWaitingTime = this.totalProcessTime = 0;
        this.processComputation.removeAll(this.processComputation);
        this.processSchedule.removeAll(this.processSchedule);
        this.processInitStorage.forEach((process) -> {
            this.processComputation.add(new Process(process));
        });
        this.processComputation.forEach((process) -> {
            this.arrivalQueue.add(process);
        });
    }
    
    
    private void initRequestQueue(){
        Collections.sort(this.arrivalQueue, new ProcessComparator("firstcomefirstserve"));
        this.requestQueue.add(this.arrivalQueue.removeFirst());
        LinkedList<Process> tempQueue = new LinkedList<>();
        for(int i = 0; i < this.arrivalQueue.size(); i++){
            if(this.requestQueue.getFirst().getArrivalTime() == this.arrivalQueue.get(i).getArrivalTime()){
                this.requestQueue.add(this.arrivalQueue.get(i));
                tempQueue.add(this.arrivalQueue.get(i));
            }
        }this.arrivalQueue.removeAll(tempQueue);
        
        System.out.println("ARRIVAL" + this.arrivalQueue);
        System.out.println("REQUEST" + this.requestQueue);
    }
    
    private void initProcessComputation(){
        Collections.sort(this.requestQueue, new ProcessComparator("firstcomefirstserve"));
        Process firstProcess = this.requestQueue.removeFirst();
            

        //this.totalProcessTime += firstProcess.getBurstTime();
        
        this.processSchedule.add(firstProcess);
        for(int i = 0; i < this.processComputation.size(); i++){
            if(this.processComputation.get(i).equals(firstProcess)){
                this.processComputation.add(firstProcess
                    .setCompletionTime(this.totalProcessTime)
                    .computeTurnAroundTime()
                    .computeWaitingTime()
                );
                break;
            }
        }
        
        
    }
    
    public void nonPreemptiveSchedule(String mode){
        this.reset();
        this.initRequestQueue();
        this.initProcessComputation();
        ProcessComparator processComparator = new ProcessComparator(mode);
        
        while(!(this.arrivalQueue.isEmpty() && this.requestQueue.isEmpty())){
            LinkedList<Process> tempQueue = new LinkedList<>();
            for(int i = 0; i < this.arrivalQueue.size(); i++){
                if(this.arrivalQueue.get(i).getArrivalTime() <= this.totalProcessTime){
                    this.requestQueue.add(this.arrivalQueue.get(i));
                    tempQueue.add(this.arrivalQueue.get(i));
                }
            }this.arrivalQueue.removeAll(tempQueue);
            
            System.out.println("\nARRIVAL" + this.arrivalQueue);
            System.out.println("REQUEST" + this.requestQueue);
            System.out.println("SCHED" + this.processSchedule);
            
            Collections.sort(this.requestQueue, processComparator);
            System.out.println("\nARRIVAL" + this.arrivalQueue);
            System.out.println("REQUEST" + this.requestQueue);
            System.out.println("SCHED" + this.processSchedule);
            Process currentProcess = this.requestQueue.removeFirst();
            System.out.println("\nARRIVAL" + this.arrivalQueue);
            System.out.println("REQUEST" + this.requestQueue);
            System.out.println("SCHED" + this.processSchedule);
            
            this.totalProcessTime += currentProcess.getBurstTime();
            this.processSchedule.add(currentProcess);
            for(int i = 0; i < this.processComputation.size(); i++){
                if(this.processComputation.get(i).equals(currentProcess)){
                    this.processComputation.add(currentProcess
                        .setCompletionTime(this.totalProcessTime)
                        .computeTurnAroundTime()
                        .computeWaitingTime()
                    );
                    break;
                }
            }
            
            System.out.println("\nARRIVAL" + this.arrivalQueue);
            System.out.println("REQUEST" + this.requestQueue);
            System.out.println("SCHED" + this.processSchedule);
        }
        
    }
    
    @Override
    public String toString(){
        return "\nProcessList:" + this.processComputation.toString() +
               "\n\nProcessSchedule:" + this.processSchedule.toString() +
               "Total Process Time Consumed(" + Double.toString(this.totalProcessTime) + ")\n" +
               "Average Turn Around Time(" + Double.toString(this.averageTurnAroundTime) + ")\n" +
               "Average Waiting Time(" + Double.toString(this.averageWaitingTime) + ")\n";
    }
}
