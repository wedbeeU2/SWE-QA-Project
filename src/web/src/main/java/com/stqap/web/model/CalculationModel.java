package com.stqap.web.model;

import com.stqap.calculatorlogic.DescriptiveStatistics;
import com.stqap.calculatorlogic.LinearRegression;
import java.util.List;
import java.util.ArrayList;

public class CalculationModel {
    private final DescriptiveStatistics descriptiveStats;
    private final LinearRegression regression;
    
    public CalculationModel() {
        this.descriptiveStats = new DescriptiveStatistics();
        this.regression = new LinearRegression();
    }

    public double performMeanCalculation(String input) {
        List<Double> numbers = parseInput(input);
        return descriptiveStats.calculateMean(numbers);
    }

    public double performSampleStdDev(String input) {
        List<Double> numbers = parseInput(input);
        return descriptiveStats.calculateSampleStandardDeviation(numbers);
    }

    public double performPopulationStdDev(String input) {
        List<Double> numbers = parseInput(input);
        return descriptiveStats.calculatePopulationStandardDeviation(numbers);
    }

    public double performZScore(String input) {
        String[] parts = input.trim().split(",");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Please provide value, mean, and standard deviation");
        }
        double value = Double.parseDouble(parts[0].trim());
        double mean = Double.parseDouble(parts[1].trim());
        double stdDev = Double.parseDouble(parts[2].trim());
        return descriptiveStats.calculateZScore(value, mean, stdDev);
    }

    public String calculateLinearRegression(String input) {
        List<LinearRegression.Point> points = parsePointInput(input);
        LinearRegression.RegressionResult result = regression.calculateRegression(points);
        return String.format("y = %.15fx + %.15f", result.slope, result.intercept);
    }

    public double predictY(String input) {
        String[] parts = input.trim().split(",");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Please provide x, slope, and intercept");
        }
        double x = Double.parseDouble(parts[0].trim());
        double slope = Double.parseDouble(parts[1].trim());
        double intercept = Double.parseDouble(parts[2].trim());
        return regression.predictY(x, slope, intercept);
    }

    

    private List<LinearRegression.Point> parsePointInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Input cannot be empty");
        }

        List<LinearRegression.Point> points = new ArrayList<>();
        String[] lines = input.split("\n");
        
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                String[] parts = line.trim().split(",");
                if (parts.length != 2) {
                    throw new IllegalArgumentException("Invalid input format, please provide x,y pairs");
                }
                double x = Double.parseDouble(parts[0].trim());
                double y = Double.parseDouble(parts[1].trim());
                points.add(new LinearRegression.Point(x, y));
            }
        }
        
        return points;
    }

    private List<Double> parseInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Input cannot be empty");
        }

        List<Double> numbers = new ArrayList<>();
        String[] lines = input.split("\n");
        
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                try {
                    numbers.add(Double.parseDouble(line.trim()));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid number format");
                }
            }
        }
        
        return numbers;
    }
}