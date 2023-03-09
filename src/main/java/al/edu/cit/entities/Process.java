package al.edu.cit.entities;

import java.util.ArrayList;
import java.util.List;

public class Process {
    public String name;
    public int arrivalTime;
    public int burstTime;
    public int remainingBurstTime;
    public ProcessType type;
    public ProcessStatus status;
    public int completionTime;
    public int turnAroundTime;
    public int waitingTime;

    public List<RunTime> runtime = new ArrayList<>();

    public Process(String name, int arrivalTime, int burstTime, ProcessType type) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingBurstTime = burstTime;
        this.type = type;
        this.status = ProcessStatus.Running;
    }

    public void run(int timeline) {
        runtime.add(new RunTime(timeline -1 , timeline ));

        remainingBurstTime--;

        if (remainingBurstTime == 0) {
            completionTime = timeline;
            turnAroundTime = completionTime - arrivalTime;
            waitingTime = turnAroundTime - burstTime;
            status = ProcessStatus.Completed;
        }
    }
}
