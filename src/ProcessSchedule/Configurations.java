package ProcessSchedule;

public class Configurations {
    
    private String type;
    private int count;
    
    public Configurations(String type, int count) {
        this.type = type;
        this.count = count;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public int getCount() {
        return count;
    }
    
    public void setCount(int count) {
        this.count = count;
    }        
    
    public boolean isRoundRobin() {
        return getType().equals("Round Robin");
    }
    
    public boolean isPriority() {
        return (getType().equals("Non-Preemptive Priority") || getType().equals("Preemptive Priority"));
    }
}
