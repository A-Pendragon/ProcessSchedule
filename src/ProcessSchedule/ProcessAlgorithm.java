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
 * @deprecated 
 * @author Chris
 */
public class ProcessAlgorithm {
    private final LinkedList<Process> processList;
    private final LinkedList<Process> processSchedule;
    private final LinkedList<Process> arrivalQueue;
    private final LinkedList<Process> requestQueue;
    private double totalProcessTime;
    private double averageTurnAroundTime;
    private double averageWaitingTime;
    
    /**
     * The constructor assumes that <code>processList</code> is not null and empty.
     * @param processList the list of process to be scheduled on a criteria.
     */
    public ProcessAlgorithm(LinkedList<Process> processList){
        this.averageTurnAroundTime = this.averageWaitingTime = this.totalProcessTime = 0;
        this.processList = new LinkedList<>();
        this.processSchedule = new LinkedList<>();
        this.arrivalQueue = new LinkedList<>();
        this.requestQueue = new LinkedList<>();
        processList.forEach((process) -> {
            this.processList.add(new Process(process));
        });
    }
    
    private void reset(){
        this.totalProcessTime = this.averageTurnAroundTime = this.averageWaitingTime = 0;
        this.processSchedule.removeAll(this.processSchedule);
        this.processList.forEach((process) -> {
            this.arrivalQueue.add(new Process(process));
        });
    }
    
    /**
     * This function asserts that the output of the algorithm will be stored into the <code>processSchedule</code>
     * and that the <code>arrivalQueue</code> will be emptied long after.
     * @param mode the non-preemptive algorithm to execute "firstComeFirstServe" | "shortestJobFirst" | "npPriority" (Case Insensitive)
     */
    public void schedule(String mode) throws InterruptedException{
        this.reset();
        String type = this.initAlgorithmType(mode);
        ProcessComparator processComparator = new ProcessComparator(mode);
        ArrayList<Double> tatList = new ArrayList<>();
        ArrayList<Double> wtList = new ArrayList<>();
        this.initReadyQueue();
        
        System.out.println("\n\nREADY_QUEUE" + this.requestQueue);
        System.out.print(processComparator.getMode());
        Thread.sleep(2000);
            
        while(!(this.arrivalQueue.isEmpty() && this.requestQueue.isEmpty())){
            this.refreshRequestQueue();
            
            System.out.println("ARRIVAL_QUEUE[2]" + this.arrivalQueue);
            System.out.println("\n\nREADY_QUEUE[2]" + this.requestQueue);
            System.out.println(this.totalProcessTime);
            Thread.sleep(2000);
            
            Collections.sort(this.requestQueue, processComparator);  
            Process currentProcess = this.requestQueue.getFirst().splitProcessBy( (type.equalsIgnoreCase("nonpreemptive")) ? "WHOLE_UNIT" : "SINGLE_TIME_UNIT" );
            //Process currentProcess = new Process(readyQueue.poll());
            this.totalProcessTime += currentProcess.getBurstTime();
           
            ///*
            //updates the processList, final output
            if(this.requestQueue.getFirst().getBurstTime() == 0){
                Process lastProcess = new Process(this.requestQueue.poll());
                this.processList.forEach((process) -> {
                    if(process.equals(lastProcess)){
                        process.setCompletionTime(this.totalProcessTime)
                            .computeTurnAroundTime()
                            .computeWaitingTime();
                    }
                });
            }
            System.out.println("ARRIVAL_QUEUE[3]" + this.arrivalQueue);
            System.out.println("\n\nREADY_QUEUE[3]" + this.requestQueue);
            System.out.println(this.totalProcessTime);
            Thread.sleep(2000);
            //updates the processSchedule, gantt chart
            this.processSchedule.add(new Process(currentProcess)
                .setCompletionTime(this.totalProcessTime)
                .computeTurnAroundTime()
                .computeWaitingTime()
            );
            //*/
            
            
            /*
            this.processList.forEach((process) -> {
                if(process.equals(currentProcess)){                      
                    this.processSchedule.add(process
                        .setCompletionTime(this.totalProcessTime)
                        .computeTurnAroundTime()
                        .computeWaitingTime()
                    );
                }
            });
            //*/
        }this.setAverageTime(tatList, wtList);
    }
    
    //<editor-fold defaultstate="collapsed" desc="Deprecated Function">
    /**
     * The function is under development and should not be used.
     * @see schedule function instead.
     * @deprecated 
     */
    public void Preemptive(String type){
        this.reset();
        ArrayList<Double> tatList = new ArrayList<>();
        ArrayList<Double> wtList = new ArrayList<>();
        LinkedList<Process> readyQueue = new LinkedList<>();
        //this.initReadyQueue(readyQueue);
        
        while(!(this.arrivalQueue.isEmpty() && readyQueue.isEmpty())){
            //this.refreshReadyQueue(readyQueue);
            Collections.sort(readyQueue, new ProcessComparator(type));
            Process currentProcess = readyQueue.getFirst().splitProcessBy("SINGLE_TIME_UNIT");
            this.totalProcessTime += currentProcess.getBurstTime();
            
            if(readyQueue.getFirst().getBurstTime() == 0){
                Process lastProcess = new Process(readyQueue.poll());
                this.processList.forEach((process) -> {
                    if(process.equals(lastProcess)){
                        process.setCompletionTime(this.totalProcessTime)
                            .computeTurnAroundTime()
                            .computeWaitingTime();
                    }
                });
            }
            
            this.processSchedule.add(new Process(currentProcess)
                .setCompletionTime(this.totalProcessTime)
                .computeTurnAroundTime()
                .computeWaitingTime()
            );
        }this.setAverageTime(tatList, wtList);
    }
    //</editor-fold>
    
    public String initAlgorithmType(String mode){
        if(mode.equalsIgnoreCase("firstcomefirstserve") || mode.equalsIgnoreCase("shortestjobfirst") || mode.equalsIgnoreCase("nppriority")){
            return "nonpreemptive";
        }else{
            return "preemptive";
        }
    }
    
    private void initReadyQueue(){ //check
        Collections.sort(this.arrivalQueue, new ProcessComparator("firstcomefirstserve"));
        this.requestQueue.add(new Process(this.arrivalQueue.poll())); 
    }
    
    private void refreshRequestQueue(){
        LinkedList<Process> tempQueue = new LinkedList<>();
        for(int i = 0; i < this.arrivalQueue.size(); i++){
            if(this.processSchedule.isEmpty() && this.requestQueue.getFirst().getArrivalTime() == this.arrivalQueue.get(i).getArrivalTime()){
                System.out.print("REFRESH_1");
                this.requestQueue.add(new Process(this.arrivalQueue.getFirst()));
            }else if(this.arrivalQueue.get(i).getArrivalTime() <= this.totalProcessTime){
                this.requestQueue.add(new Process(this.arrivalQueue.getFirst()));
                System.out.print("REFRESH_N");
            }else{
                continue;
            }
        }

    }
    
    private double computeAverageTime(ArrayList<Double> timeList){
        double totalTime = 0;
        totalTime = timeList.stream().map((time) -> time).reduce(totalTime, (accumulator, _item) -> accumulator + _item);
        return totalTime/timeList.size();
    }
    
    private void setAverageTime(ArrayList<Double> tatList, ArrayList<Double> wtList){
        this.processList.stream().map((process) -> {
            tatList.add(process.getTurnAroundTime());
            return process;
        }).forEachOrdered((process) -> {
            wtList.add(process.getWaitingTime());
        });
        this.averageTurnAroundTime = this.computeAverageTime(tatList);
        this.averageWaitingTime = this.computeAverageTime(wtList);
    }

    @Override
    public String toString(){
        return "ProcessList:" + this.processList.toString() +
               "\n\nProcessSchedule:" + this.processSchedule.toString() +
               "Total Process Time Consumed(" + Double.toString(this.totalProcessTime) + ")\n" +
               "Average Turn Around Time(" + Double.toString(this.averageTurnAroundTime) + ")\n" +
               "Average Waiting Time(" + Double.toString(this.averageWaitingTime) + ")\n";
    }
    
    /**
     * 
     * @return Null if the <code>schedule</code> function was not even invoked once.
     */
    public LinkedList<Process> getProcessSchedule() {
        return processSchedule;
    }

    /**
     * 
     * @return 0 if the <code>schedule</code> function was not even invoked once.
     */
    public double getTotalProcessTime() {
        return totalProcessTime;
    }

    /**
     * 
     * @return 0 if the <code>schedule</code> function was not even invoked once.
     */
    public double getAverageTurnAroundTime() {
        return averageTurnAroundTime;
    }

    /**
     * 
     * @return 0 if the <code>schedule</code> function was not even invoked once.
     */
    public double getAverageWaitingTime() {
        return averageWaitingTime;
    }
}
