package al.edu.cit.algorithms;

import al.edu.cit.entities.Process;
import al.edu.cit.entities.ProcessStatus;
import al.edu.cit.os.SchedulingAlgorithm;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class FCFS implements SchedulingAlgorithm {
    private AlgorithmType algorithmType = AlgorithmType.NonPreemptive;
    private Queue<Process> queue = new LinkedList<>();
    private Process runningProcess;

    private List<Process> getProcessesNotInQueue(List<Process> processes) {
        return processes
                .stream()
                .filter(process -> !queue.contains(process))
                .collect(Collectors.toList());
    }

    @Override
    public Process schedule(List<Process> processes) {
        queue.addAll(getProcessesNotInQueue(processes));

        if (runningProcess != null && runningProcess.status != ProcessStatus.Completed)
            return runningProcess;

        if (runningProcess != null && runningProcess.status == ProcessStatus.Completed)
            queue.poll();

        runningProcess = queue.peek();
        return runningProcess;
    }
}
