package com.stqap.web.controller;

import com.stqap.calculatorlogic.model.CalculationResult;
import com.stqap.calculatorlogic.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CalculatorController {
    
    @Autowired
    private CalculatorService calculatorService;
    
    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @PostMapping("/calculate/{type}")
    @ResponseBody
    public String calculate(@PathVariable String type, @RequestParam String values) {
        try {
            return switch (type) {
                case "mean" -> calculatorService.calculateMean(parseValues(values)).getFormattedResult();
                case "sample-std" -> calculatorService.calculateSampleStandardDeviation(parseValues(values)).getFormattedResult();
                case "pop-std" -> calculatorService.calculatePopulationStandardDeviation(parseValues(values)).getFormattedResult();
                case "zscore" -> handleZScore(values);
                case "regression" -> calculatorService.calculateLinearRegression(Arrays.asList(values.split("\n"))).getFormattedResult();
                case "predict-y" -> handlePredictY(values);
                default -> "Error: Unknown calculation type";
            };
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    
    private String handleZScore(String values) {
        String[] parts = values.trim().split(",");
        if (parts.length != 3) {
            return "Error: Please enter value, mean, and standard deviation separated by commas";
        }
        try {
            double value = Double.parseDouble(parts[0].trim());
            double mean = Double.parseDouble(parts[1].trim());
            double stdDev = Double.parseDouble(parts[2].trim());
            return calculatorService.calculateZScore(value, mean, stdDev).getFormattedResult();
        } catch (NumberFormatException e) {
            return "Error: Invalid number format";
        }
    }
    
    private String handlePredictY(String values) {
        String[] parts = values.trim().split(",");
        if (parts.length != 3) {
            return "Error: Please enter x value, slope (m), and intercept (b) separated by commas";
        }
        try {
            double x = Double.parseDouble(parts[0].trim());
            double slope = Double.parseDouble(parts[1].trim());
            double intercept = Double.parseDouble(parts[2].trim());
            return calculatorService.predictY(x, slope, intercept).getFormattedResult();
        } catch (NumberFormatException e) {
            return "Error: Invalid number format";
        }
    }
    
    private List<Double> parseValues(String input) {
        return Arrays.stream(input.split("\n"))
            .filter(line -> !line.trim().isEmpty())
            .map(line -> {
                try {
                    return Double.parseDouble(line.trim());
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid number format: " + line.trim());
                }
            })
            .collect(Collectors.toList());
    }
}