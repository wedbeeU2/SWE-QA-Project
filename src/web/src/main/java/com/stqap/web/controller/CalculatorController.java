package com.stqap.web.controller;

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
    public String calculate(@PathVariable("type") String type, @RequestParam("values") String values) {
        try {
            return switch (type) {
                case "mean" -> String.format("Mean: %.15f", calculatorService.calculateMean(parseValues(values)));
                case "sample-std" -> String.format("Sample Standard Deviation: %.15f", 
                    calculatorService.calculateSampleStandardDeviation(parseValues(values)));
                case "pop-std" -> String.format("Population Standard Deviation: %.15f", 
                    calculatorService.calculatePopulationStandardDeviation(parseValues(values)));
                case "zscore" -> handleZScore(values);
                case "regression" -> calculatorService.calculateLinearRegression(Arrays.asList(values.split("\n")));
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
            double result = calculatorService.calculateZScore(value, mean, stdDev);
            return String.format("Z-Score: %.15f", result);
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
            double result = calculatorService.predictY(x, slope, intercept);
            return String.format("Predicted Y = %.15f", result);
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