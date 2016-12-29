/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProcessSchedule;

import java.util.Comparator;

/**
 *
 * @author Chris
 */
public class ProcessComparator implements Comparator<Process> {
    private final String mode;
    
    public ProcessComparator(String mode){
        if(mode.equalsIgnoreCase("shortestremainingtime")){
            mode = "shortestjobfirst";
        }if(mode.equalsIgnoreCase("ppriority")){
            mode = "nppriority";
        }this.mode = mode;
    }

    @Override
    public int compare(Process p, Process p1) {
        int returnType = 0;
        if(mode.equalsIgnoreCase("firstcomefirstserve")){
            int compareArrivalTime = Double.valueOf(p.getArrivalTime()).compareTo(p1.getArrivalTime());
            if(compareArrivalTime == 0){
                returnType = Integer.valueOf(p.getProcessNo()).compareTo(p1.getProcessNo());
            }else{
                returnType = compareArrivalTime;  
            }
        }else if(mode.equalsIgnoreCase("shortestjobfirst")){
            int compareBurstTime = Double.valueOf(p.getBurstTime()).compareTo(p1.getBurstTime());
            if(compareBurstTime == 0){
                returnType = Integer.valueOf(p.getProcessNo()).compareTo(p1.getProcessNo());
            }else{
                returnType = compareBurstTime;  
            }
        }else if(mode.equalsIgnoreCase("nppriority")){
            int comparePriority = Integer.valueOf(p1.getPriority()).compareTo(p.getPriority());
            if(comparePriority == 0){
                returnType = Integer.valueOf(p.getProcessNo()).compareTo(p1.getProcessNo());
            }else{
                returnType = comparePriority;  
            }
        }else{
            throw new UnsupportedOperationException();
        }
        return returnType;
    }

    public String getMode() {
        return mode;
    }
    
}
