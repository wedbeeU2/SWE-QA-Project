package com.stqap.swe_qa_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.stqap.swe_qa_project.service.CalculatorService;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TestController {
    
    @Autowired
    private CalculatorService calculatorService;
    
    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @PostMapping("/calculate/mean")
    @ResponseBody
    public String calculateMean(@RequestParam String values) {
        try {
            List<Double> numbers = parseValues(values);
            if (numbers.isEmpty()) {
                return "Error: Please enter at least one value";
            }
            double result = calculatorService.calculateMean(numbers);
            return String.format("Mean: %.15f", result);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    
    @PostMapping("/calculate/sample-std")
    @ResponseBody
    public String calculateSampleStd(@RequestParam String values) {
        try {
            List<Double> numbers = parseValues(values);
            if (numbers.isEmpty()) {
                return "Error: Please enter at least one value";
            }
            double result = calculatorService.calculateSampleStandardDeviation(numbers);
            return String.format("Sample Standard Deviation: %.15f", result);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    
    @PostMapping("/calculate/pop-std")
    @ResponseBody
    public String calculatePopulationStd(@RequestParam String values) {
        try {
            List<Double> numbers = parseValues(values);
            if (numbers.size() < 2) {
                return "Error: Please enter at least two values";
            }
            double result = calculatorService.calculatePopulationStandardDeviation(numbers);
            return String.format("Population Standard Deviation: %.15f", result);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    
    @PostMapping("/calculate/zscore")
    @ResponseBody
    public String calculateZScore(@RequestParam String values) {
        try {
            // Remove any whitespace and split by comma
            String[] parts = values.trim().split(",");
            if (parts.length != 3) {
                return "Error: Please enter exactly three numbers separated by commas";
            }

            try {
                double value = Double.parseDouble(parts[0].trim());
                double mean = Double.parseDouble(parts[1].trim());
                double stdDev = Double.parseDouble(parts[2].trim());

                if (stdDev == 0) {
                    return "Error: Standard deviation cannot be zero";
                }

                double result = calculatorService.calculateZScore(value, mean, stdDev);
                return String.format("Z-Score: %.15f", result);
            } catch (NumberFormatException e) {
                return "Error: Please enter valid numbers";
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    
    @PostMapping("/calculate/regression")
    @ResponseBody
    public String calculateRegression(@RequestParam String values) {
        try {
            // Split input into lines and filter out empty lines
            List<String> pairs = Arrays.stream(values.split("\n"))
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .collect(Collectors.toList());

            if (pairs.isEmpty()) {
                return "Error: Please enter at least one x,y pair";
            }

            // Validate each pair
            for (String pair : pairs) {
                String[] parts = pair.split(",");
                if (parts.length != 2) {
                    return "Error: Each line should contain exactly two numbers separated by a comma";
                }
                try {
                    Double.parseDouble(parts[0].trim());
                    Double.parseDouble(parts[1].trim());
                } catch (NumberFormatException e) {
                    return "Error: Invalid number format in pair: " + pair;
                }
            }

            CalculatorService.RegressionResult result = calculatorService.calculateLinearRegression(pairs);
            return String.format("y = %.15fx + %.15f", result.getSlope(), result.getIntercept());
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    
    @PostMapping("/calculate/predict-y")
    @ResponseBody
    public String predictY(@RequestParam String values) {
        try {
            // Remove any whitespace and split by comma
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
                return "Error: Please enter valid numbers";
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
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