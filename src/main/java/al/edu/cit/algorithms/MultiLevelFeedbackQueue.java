package al.edu.cit.algorithms;

import al.edu.cit.entities.Process;
import al.edu.cit.entities.ProcessStatus;
import al.edu.cit.entities.ProcessType;
import al.edu.cit.os.SchedulingAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MultiLevelFeedbackQueue implements SchedulingAlgorithm {
    private final int queue1Quantum = 2;
    private final int queue2Quantum = 3;

    protected RoundRobin queueAlg1 = new RoundRobin(queue1Quantum);
    protected RoundRobin queueAlg2 = new RoundRobin(queue2Quantum);
    protected FCFS queueAlg3 = new FCFS();

    protected List<Process> queue1 = new ArrayList<>();
    protected List<Process> queue2 = new ArrayList<>();
    protected List<Process> queue3 = new ArrayList<>();

    Process queue1RunningProcess;
    Process queue2RunningProcess;

    int queue1Runtime;
    int queue2Runtime;

    protected void addProcessesToQueues(List<Process> processes) {
        queue1.addAll(processes
                .stream()
                .filter(process -> process.type == ProcessType.System)
                .collect(Collectors.toList()));

        queue2.addAll(processes
                .stream()
                .filter(process -> process.type == ProcessType.Interactive)
                .collect(Collectors.toList()));

        queue3.addAll(processes
                .stream()
                .filter(process -> process.type == ProcessType.Batch)
                .collect(Collectors.toList()));
    }

    protected boolean areStillRunning(List<Process> processes) {
        return processes
                .stream()
                .anyMatch(process -> process.status == ProcessStatus.Running);
    }

    protected List<Process> newProcesses(List<Process> processes) {
        return processes
                .stream()
                .filter(process -> !queue1.contains(process)
                                || !queue2.contains(process)
                                || !queue3.contains(process))
                .collect(Collectors.toList());
    }

    private void reorganizeQueue1() {
        Process runningProcess = queueAlg1.lastRunProcess;
        int currentRuntime = queueAlg1.finishedRuntime;

        if (currentRuntime > queue1Runtime) {
            queue1.remove(runningProcess);
            queue2.add(runningProcess);
        }

        queue1RunningProcess = runningProcess;
        queue1Runtime = currentRuntime;
    }

    private void reorganizeQueue2() {
        Process runningProcess = queueAlg2.lastRunProcess;
        int currentRuntime = queueAlg2.finishedRuntime;

        if (currentRuntime > queue2Runtime) {
            queue2.remove(runningProcess);
            queue3.add(runningProcess);
        }

        queue2RunningProcess = runningProcess;
        queue2Runtime = currentRuntime;
    }

    protected void reorganizeQueues() {
        reorganizeQueue1();
        reorganizeQueue2();
    }

    @Override
    public Process schedule(List<Process> processes) {
        addProcessesToQueues(newProcesses(processes));

        reorganizeQueues();

        if (areStillRunning(queue1))
            return queueAlg1.schedule(queue1);

        if (areStillRunning(queue2))
            return queueAlg2.schedule(queue2);

        if (areStillRunning(queue3))
            return queueAlg3.schedule(queue3);

        return null;
    }
}
