package al.edu.cit.algorithms;

import al.edu.cit.entities.Process;
import al.edu.cit.entities.ProcessStatus;
import al.edu.cit.os.SchedulingAlgorithm;

import java.util.List;

public class RoundRobin implements SchedulingAlgorithm {
    private int quantum;
    private int runtime = 0;
    public Process lastRunProcess;
    public int finishedRuntime = 0;

    public RoundRobin(int quantum) {
        this.quantum = quantum;
    }

    @Override
    public Process schedule(List<Process> processes) {
        if (processes.size() == 0) {
            lastRunProcess = null;
            return lastRunProcess;
        }

        if (lastRunProcess == null) {
            lastRunProcess = processes.get(0);
            return lastRunProcess;
        }

        if (runtime < quantum && lastRunProcess.status != ProcessStatus.Completed) {
            runtime++;
            return lastRunProcess;
        }

        finishedRuntime++;

        int indexLastRunProcess = processes.indexOf(lastRunProcess);
        if (indexLastRunProcess + 1 == processes.size())
            lastRunProcess = processes.get(0);
        else
            lastRunProcess = processes.get(indexLastRunProcess + 1);

        runtime = 0;
        return lastRunProcess;
    }
}
