package al.edu.cit.os;

import al.edu.cit.entities.Process;
import al.edu.cit.entities.ProcessStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OperatingSystem {
    List<Process> processes = new ArrayList<>();
    
    int timeline = 0;

    public void submitProcess(Process process) {
        processes.add(process);
    }
    
    private List<Process> runningProcesses() {
        return processes
                .stream()
                .filter(process -> process.arrivalTime <= timeline && process.status != ProcessStatus.Completed)
                .collect(Collectors.toList());
    }
    
    private boolean stillRunning() {
        return processes
                .stream()
                .anyMatch(process -> process.status == ProcessStatus.Running);
    }

    public void execute(SchedulingAlgorithm sa) {
        while (stillRunning()) {
            Process processToRun = sa.schedule(runningProcesses());

            if (processToRun != null) {
                processToRun.run(timeline);
            }

            timeline++;
        }
    }
}
