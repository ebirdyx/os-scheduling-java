package al.edu.cit.os;

import al.edu.cit.entities.Process;

import java.util.List;

public interface SchedulingAlgorithm {
    Process schedule(List<Process> processes);
}
