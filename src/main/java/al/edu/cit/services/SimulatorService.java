package al.edu.cit.services;

import al.edu.cit.algorithms.FCFS;
import al.edu.cit.algorithms.MultiLevelFeedbackQueue;
import al.edu.cit.algorithms.MultiLevelQueue;
import al.edu.cit.algorithms.RoundRobin;
import al.edu.cit.entities.ProcessType;
import al.edu.cit.entities.Process;
import al.edu.cit.os.OperatingSystem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SimulatorService {
    public List<Process> simulate(String algorithm) {
        Process p1 = new Process("P1", 0, 3, ProcessType.Batch);
        Process p2 = new Process("P2", 2, 4, ProcessType.Interactive);
        Process p3 = new Process("P3", 4, 2, ProcessType.Batch);
        Process p4 = new Process("P4", 5, 12, ProcessType.System);
        Process p5 = new Process("P5", 7, 8, ProcessType.System);

        OperatingSystem os = new OperatingSystem();

        os.submitProcess(p1);
        os.submitProcess(p2);
        os.submitProcess(p3);
        os.submitProcess(p4);
        os.submitProcess(p5);

        switch (algorithm) {
            case "Round Robin":
                os.execute(new RoundRobin(2));
                break;
            case "FCFS":
                os.execute(new FCFS());
                break;
            case "Multilevel Queue":
                os.execute(new MultiLevelQueue());
                break;
            case "Multilevel Feedback Queue":
                os.execute(new MultiLevelFeedbackQueue());
                break;
        }

        List<Process> processes = new ArrayList<>();
        processes.add(p1);
        processes.add(p2);
        processes.add(p3);
        processes.add(p4);
        processes.add(p5);

        return processes;
    }
}
