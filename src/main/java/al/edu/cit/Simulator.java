package al.edu.cit;

import al.edu.cit.algorithms.FCFS;
import al.edu.cit.algorithms.MultiLevelFeedbackQueue;
import al.edu.cit.algorithms.MultiLevelQueue;
import al.edu.cit.algorithms.RoundRobin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.thymeleaf.TemplateEngine;

@SpringBootApplication
public class Simulator {
    public static void main(String[] args) {
        SpringApplication.run(Simulator.class, args);

    }
}
