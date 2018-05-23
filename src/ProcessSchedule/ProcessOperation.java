package ProcessSchedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class ProcessOperation {
    
    private final LinkedList<Process> processInitStorage;
    private final LinkedList<Process> processComputation;
    private final LinkedList<Process> processSchedule;
    private final LinkedList<Process> arrivalQueue;
    private final LinkedList<Process> requestQueue;
    private double totalProcessTime;
    private double averageTurnAroundTime;
    private double averageWaitingTime;
    private double unitIntervalPrecision;
    private double timeQuantum;
    
    public ProcessOperation(LinkedList<Process> processList) {
        this.averageTurnAroundTime = this.averageWaitingTime = this.totalProcessTime = 0;
        this.processInitStorage = new LinkedList<>();
        this.processComputation = new LinkedList<>();
        this.processSchedule = new LinkedList<>();
        this.arrivalQueue = new LinkedList<>();
        this.requestQueue = new LinkedList<>();
        processList.forEach((process) -> { this.processInitStorage.add(new Process(process)); });
    }
    
    public ProcessOperation(LinkedList<Process> processList, int processPrecision) {
        Process.precision = processPrecision;
        this.averageTurnAroundTime = this.averageWaitingTime = this.totalProcessTime = 0;
        this.processInitStorage = new LinkedList<>();
        this.processComputation = new LinkedList<>();
        this.processSchedule = new LinkedList<>();
        this.arrivalQueue = new LinkedList<>();
        this.requestQueue = new LinkedList<>();
        processList.forEach((process) -> { this.processInitStorage.add(new Process(process)); });
    }
    
    private void reset() {
        this.averageTurnAroundTime = this.averageWaitingTime = this.totalProcessTime = 0;
        this.processComputation.removeAll(this.processComputation);
        this.processSchedule.removeAll(this.processSchedule);
        this.processInitStorage.forEach((process) -> { this.processComputation.add(new Process(process)); });
        this.processComputation.forEach((process) -> { this.arrivalQueue.add(new Process(process)); });
    }
    
    private void initRequestQueue() {
        Collections.sort(this.arrivalQueue, new ProcessComparator("firstcomefirstserve"));
        this.requestQueue.add(this.arrivalQueue.removeFirst());
        LinkedList<Process> tempQueue = new LinkedList<>();
        for (int i = 0; i < this.arrivalQueue.size(); i++) {
            if (this.requestQueue.getFirst().getArrivalTime() == this.arrivalQueue.get(i).getArrivalTime()) {
                this.requestQueue.add(this.arrivalQueue.get(i));
                tempQueue.add(this.arrivalQueue.get(i));
            }
        }
        this.arrivalQueue.removeAll(tempQueue);
        this.totalProcessTime = this.requestQueue.getFirst().getArrivalTime();
    }
    
    private void setPrecision(){
        LinkedList<Integer> precisionList = new LinkedList<>();
        for (int i = 0; i < this.processInitStorage.size(); i++) {
            String process = Double.toString(this.processInitStorage.get(i).getBurstTime());
            precisionList.add(process.length() - process.indexOf(".") - 1);
        }
        int numberOfPrecision = Collections.max(precisionList);
        Process.precision = numberOfPrecision;
        double computedPrecision = 1;
        for (int i = 0; i < numberOfPrecision; i++) {
            computedPrecision /= 10;
        }
        this.unitIntervalPrecision = computedPrecision;
    }
    
    private void initNonPreemptiveComputation(String mode) {
        Collections.sort(this.requestQueue, new ProcessComparator(mode));
        Process firstProcess = this.requestQueue.removeFirst();
        this.totalProcessTime += firstProcess.getBurstTime();      
        this.processSchedule.add(firstProcess);
        for (int i = 0; i < this.processComputation.size(); i++) {
            if (this.processComputation.get(i).equals(firstProcess)) {
                this.processComputation.get(i).setCompletionTime(this.totalProcessTime)
                        .computeTurnAroundTime().computeWaitingTime();
                break;
            }
        }
    }
    
    public void nonPreemptiveSchedule(String mode){
        if (!(mode.equalsIgnoreCase("firstcomefirstserve") || mode.equalsIgnoreCase("shortestjobfirst") 
                || mode.equalsIgnoreCase("nppriority"))) { 
            throw new UnsupportedOperationException(); 
        }
        this.reset();
        this.initRequestQueue();
        this.setPrecision();
        this.initNonPreemptiveComputation(mode);
        ProcessComparator processComparator = new ProcessComparator(mode);
        while(!(this.arrivalQueue.isEmpty() && this.requestQueue.isEmpty())){
            LinkedList<Process> tempQueue = new LinkedList<>();
            for(int i = 0; i < this.arrivalQueue.size(); i++){
                if(this.arrivalQueue.get(i).getArrivalTime() <= this.totalProcessTime){
                    this.requestQueue.add(this.arrivalQueue.get(i));
                    tempQueue.add(this.arrivalQueue.get(i));                    
                }
            }
            this.arrivalQueue.removeAll(tempQueue); 
            if (this.requestQueue.isEmpty()) {
                this.requestQueue.add(this.arrivalQueue.removeFirst());
                this.totalProcessTime = this.requestQueue.getLast().getArrivalTime();
                continue;
            }
            Collections.sort(this.requestQueue, processComparator);
            Process currentProcess = this.requestQueue.removeFirst();
            this.totalProcessTime += currentProcess.getBurstTime();
            this.processSchedule.add( currentProcess.setCompletionTime(this.totalProcessTime)
                    .computeTurnAroundTime().computeWaitingTime() );
            for (int i = 0; i < this.processComputation.size(); i++) {
                if (this.processComputation.get(i).equals(currentProcess)) {
                    this.processComputation.get(i).setCompletionTime(this.totalProcessTime)
                            .computeTurnAroundTime().computeWaitingTime();
                    break;
                }
            }
        }
        this.setAverageTime();
    }
    
    private void initPreemptiveComputation(String mode) {
        LinkedList<Process> tempQueue = new LinkedList<>();
        this.requestQueue.forEach((process) -> { tempQueue.add(new Process(process)); });
        this.requestQueue.removeAll(this.requestQueue);
        tempQueue.forEach((process) -> { this.requestQueue.add(process); });
        tempQueue.removeAll(tempQueue);
        Collections.sort(this.requestQueue, new ProcessComparator(mode));
        Process firstProcess = new Process(this.requestQueue.getFirst());
        firstProcess.setBurstTime(this.unitIntervalPrecision);
        this.requestQueue.getFirst().setBurstTime(this.requestQueue.getFirst().getBurstTime()-this.unitIntervalPrecision);
        this.totalProcessTime += this.unitIntervalPrecision;      
        this.processSchedule.add(firstProcess);
        if(this.requestQueue.getFirst().getBurstTime() == 0){
            this.processComputation.get(this.requestQueue.removeFirst().getProcessNo()-1)
                .setCompletionTime(this.totalProcessTime)
                .computeTurnAroundTime()
                .computeWaitingTime();
            
        }
    } 
   
    public void preemptiveSchedule(String mode){
        if(!(mode.equalsIgnoreCase("shortestremainingtime") || mode.equalsIgnoreCase("ppriority"))) { 
            throw new UnsupportedOperationException(); 
        }
        this.reset();
        this.initRequestQueue();
        this.setPrecision();
        this.initPreemptiveComputation(mode);
        ProcessComparator processComparator = new ProcessComparator(mode);
        while(!(this.arrivalQueue.isEmpty() && this.requestQueue.isEmpty())){           
            LinkedList<Process> tempQueue = new LinkedList<>();
            for(int i = 0; i < this.arrivalQueue.size(); i++){
                if(this.arrivalQueue.get(i).getArrivalTime() <= this.totalProcessTime){
                    this.requestQueue.add(this.arrivalQueue.get(i));
                    tempQueue.add(this.arrivalQueue.get(i));
                }
            }
            this.arrivalQueue.removeAll(tempQueue);
            if(this.requestQueue.isEmpty()){
                this.requestQueue.add(this.arrivalQueue.removeFirst());
                this.totalProcessTime = this.requestQueue.getLast().getArrivalTime();
                continue;
            }
            Collections.sort(this.requestQueue, processComparator);
            Process currentProcess;
            if (this.requestQueue.getFirst().equals(this.processSchedule.getLast())) {
                currentProcess = this.processSchedule.getLast();
                currentProcess.setBurstTime(currentProcess.getBurstTime() + this.unitIntervalPrecision);
                this.requestQueue.getFirst().setBurstTime(this.requestQueue.getFirst()
                        .getBurstTime()-this.unitIntervalPrecision);
                this.totalProcessTime += this.unitIntervalPrecision;     
                currentProcess.setCompletionTime(this.totalProcessTime).computeTurnAroundTime().computeWaitingTime(); 
            } else {
                currentProcess = new Process(this.requestQueue.getFirst());
                currentProcess.setBurstTime(this.unitIntervalPrecision);
                this.requestQueue.getFirst().setBurstTime(this.requestQueue.getFirst()
                        .getBurstTime()-this.unitIntervalPrecision);
                this.totalProcessTime += currentProcess.getBurstTime();      
                this.processSchedule.add( currentProcess.setCompletionTime(this.totalProcessTime)
                        .computeTurnAroundTime().computeWaitingTime() );
            }
            
            if (this.requestQueue.getFirst().getBurstTime() == 0) {
                this.processComputation.get(this.requestQueue.removeFirst().getProcessNo()-1)
                    .setCompletionTime(this.totalProcessTime)
                    .computeTurnAroundTime()
                    .computeWaitingTime();
            }
        }
        this.setAverageTime();
    }
    
    private double initRoundRobin() {
        double timeDivider = this.timeQuantum;
        /*
        this.arrivalQueue.forEach((process) -> { this.requestQueue.add(process); });
        this.arrivalQueue.removeAll(this.arrivalQueue);
        */
        LinkedList<Process> tempQueue = new LinkedList<>(); 
        this.requestQueue.add(this.arrivalQueue.removeFirst());
        Process firstProcess = new Process(this.requestQueue.getFirst());
        Process relocateProcess = null;
        if (firstProcess.getBurstTime() <= timeDivider) {
            timeDivider -= firstProcess.getBurstTime();
            this.totalProcessTime += firstProcess.getBurstTime();
            this.processComputation.get(this.requestQueue.removeFirst().getProcessNo()-1).setCompletionTime(this.totalProcessTime).computeTurnAroundTime().computeWaitingTime();  
            timeDivider = this.timeQuantum;
//            if(timeDivider == 0){ timeDivider = this.timeQuantum; }
        } else {
            firstProcess.setBurstTime(timeDivider);
            this.totalProcessTime += firstProcess.getBurstTime();
            this.requestQueue.getFirst().setBurstTime(this.requestQueue.getFirst().getBurstTime() - timeDivider);
            relocateProcess = this.requestQueue.removeFirst();
            timeDivider = this.timeQuantum;
        }
        for (int i = 0; i < this.arrivalQueue.size(); i++) {
            if (this.arrivalQueue.get(i).getArrivalTime() <= this.totalProcessTime) {
                this.requestQueue.add(this.arrivalQueue.get(i));
                tempQueue.add(this.arrivalQueue.get(i));
            }
        }
        this.arrivalQueue.removeAll(tempQueue); 
        if (relocateProcess != null) { 
            this.requestQueue.add(relocateProcess); 
        }
        this.processSchedule.add(firstProcess);
        return timeDivider;
    }
    
    public void roundRobinSchedule(double timeQuantum){
        this.timeQuantum = timeQuantum;
        this.reset();
        this.initRequestQueue();
        this.setPrecision();
        double timeDivider = this.initRoundRobin();
        
        while (!(this.requestQueue.isEmpty() && this.arrivalQueue.isEmpty())){
            LinkedList<Process> tempQueue = new LinkedList<>(); 
            Process currentProcess = new Process(this.requestQueue.getFirst());
            Process relocateProcess = null;
            
            if (currentProcess.getBurstTime() <= timeDivider) {
                timeDivider -= currentProcess.getBurstTime();
                this.processComputation.get(this.requestQueue.removeFirst().getProcessNo()-1)
                        .setCompletionTime(this.totalProcessTime + currentProcess.getBurstTime() + currentProcess.getArrivalTime())
                        .computeTurnAroundTime().computeWaitingTime();
                this.totalProcessTime += currentProcess.getBurstTime();
                timeDivider = this.timeQuantum;
//                if(timeDivider == 0){ timeDivider = this.timeQuantum; }
            } else {
                currentProcess.setBurstTime(timeDivider);
                this.totalProcessTime += currentProcess.getBurstTime();
                this.requestQueue.getFirst().setBurstTime(this.requestQueue.getFirst().getBurstTime() - timeDivider);
                relocateProcess = this.requestQueue.removeFirst();
                timeDivider = this.timeQuantum;
            }
            for (int i = 0; i < this.arrivalQueue.size(); i++) {
                if (this.arrivalQueue.get(i).getArrivalTime() <= this.totalProcessTime) {
                    this.requestQueue.add(this.arrivalQueue.get(i));
                    tempQueue.add(this.arrivalQueue.get(i));
                }
            }
            this.arrivalQueue.removeAll(tempQueue); 
            if(relocateProcess != null){ this.requestQueue.add(relocateProcess); }
            this.processSchedule.add(currentProcess.setCompletionTime(this.totalProcessTime)
                    .computeTurnAroundTime().computeWaitingTime());
        }
        this.setAverageTime();
    }
   
    private void setAverageTime(){
        ArrayList<Double> tatList = new ArrayList<>();
        ArrayList<Double> wtList = new ArrayList<>();
        this.processComputation
            .stream().map((process) -> { tatList.add(process.getTurnAroundTime()); return process; })
            .forEachOrdered((process) -> { wtList.add(process.getWaitingTime()); });
        this.averageTurnAroundTime = this.computeAverageTime(tatList);
        this.averageWaitingTime = this.computeAverageTime(wtList);
        this.totalProcessTime = this.round(this.totalProcessTime);
    }
    
    private double computeAverageTime(ArrayList<Double> timeList){
        double totalTime = 0;
        
        totalTime = timeList.stream().map((time) -> time).reduce(totalTime, (accumulator, _item) -> accumulator + _item);
        return this.round(totalTime / timeList.size());
    }
    
    private double round(double number){
        int numberOfPrecision = 1;
        
        for(int i = 0; i < Process.precision; i++){
            numberOfPrecision *= 10;
        }
        return (double)Math.round(number * numberOfPrecision) / numberOfPrecision;
    }
    
    @Override
    public String toString(){
        String formatedString = this.processComputation.toString()
            .replace("[", " ") //remove the right bracket 
            .replace(",", "")  //remove the commas                                   
            .replace("]", ""); //remove the left bracket
        return formatedString;
    }

    public LinkedList<Process> getProcessInitStorage() {
        return processInitStorage;
    }

    public LinkedList<Process> getProcessComputation() {
        return processComputation;
    }

    public LinkedList<Process> getProcessSchedule() {
        return processSchedule;
    }

    public double getTotalProcessTime() {
        return totalProcessTime;
    }

    public double getAverageTurnAroundTime() {
        return averageTurnAroundTime;
    }

    public double getAverageWaitingTime() {
        return averageWaitingTime;
    }

    public double getTimeQuantum() {
        return timeQuantum;
    }        
}
