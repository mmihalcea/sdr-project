package edu.sdr.electronics.controller;

import edu.sdr.electronics.dto.response.ChartResponse;
import edu.sdr.electronics.service.ChartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chart")
@AllArgsConstructor
public class ChartController {

    private final ChartService chartService;

    @GetMapping
    public ResponseEntity<ChartResponse> getChartData() {
        return new ResponseEntity<>(chartService.getChartData(), HttpStatus.OK);
    }
}
