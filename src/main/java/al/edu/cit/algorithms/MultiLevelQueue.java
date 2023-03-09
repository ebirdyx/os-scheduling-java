package al.edu.cit.algorithms;

import al.edu.cit.entities.Process;
import al.edu.cit.entities.ProcessStatus;
import al.edu.cit.entities.ProcessType;
import al.edu.cit.os.SchedulingAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MultiLevelQueue implements SchedulingAlgorithm {
    private final int queue1Quantum = 2;
    private final int queue2Quantum = 3;

    protected RoundRobin queueAlg1 = new RoundRobin(queue1Quantum);
    protected RoundRobin queueAlg2 = new RoundRobin(queue2Quantum);
    protected FCFS queueAlg3 = new FCFS();

    protected List<Process> queue1 = new ArrayList<>();
    protected List<Process> queue2 = new ArrayList<>();
    protected List<Process> queue3 = new ArrayList<>();

    protected void addProcessesToQueues(List<Process> processes) {
        queue1.addAll(processes
                .stream()
                .filter(process -> process.type == ProcessType.System && !queue1.contains(process))
                .collect(Collectors.toList()));

        queue2.addAll(processes
                .stream()
                .filter(process -> process.type == ProcessType.Interactive && !queue2.contains(process))
                .collect(Collectors.toList()));

        queue3.addAll(processes
                .stream()
                .filter(process -> process.type == ProcessType.Batch && !queue3.contains(process))
                .collect(Collectors.toList()));
    }

    protected boolean areStillRunning(List<Process> processes) {
        return processes
                .stream()
                .anyMatch(process -> process.status == ProcessStatus.Running);
    }

    @Override
    public Process schedule(List<Process> processes) {
        addProcessesToQueues(processes);

        if (areStillRunning(queue1))
            return queueAlg1.schedule(queue1);

        if (areStillRunning(queue2))
            return queueAlg2.schedule(queue2);

        if (areStillRunning(queue3))
            return queueAlg3.schedule(queue3);

        return null;
    }
}
