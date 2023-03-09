package al.edu.cit.controllers;

import al.edu.cit.services.SimulatorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import al.edu.cit.entities.Process;

import java.util.List;

@Controller
@RestController
@AllArgsConstructor
public class SimulatorController {
    private SimulatorService simulator;

    @PostMapping("/simulate")
    public List<Process> simulate(@RequestBody SimulateRequest request) {
        return simulator.simulate(request.getAlgorithm());
    }
}
