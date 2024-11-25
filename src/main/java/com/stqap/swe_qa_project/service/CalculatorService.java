package com.stqap.swe_qa_project.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalculatorService {
    
    private static final int DECIMAL_PLACES = 15;
    
    public double calculateMean(List<Double> values) {
        if (values == null || values.isEmpty()) {
            throw new IllegalArgumentException("Input list cannot be null or empty");
        }
        double mean = values.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElseThrow(() -> new IllegalArgumentException("Cannot calculate mean"));
        return roundToDecimalPlaces(mean);
    }
    
    public double calculateSampleStandardDeviation(List<Double> values) {
        if (values == null || values.isEmpty()) {
            throw new IllegalArgumentException("Input list cannot be null or empty");
        }
        double mean = calculateMean(values);
        double sum = values.stream()
                .mapToDouble(value -> Math.pow(value - mean, 2))
                .sum();
        return roundToDecimalPlaces(Math.sqrt(sum / (values.size() - 1)));
    }
    
    public double calculatePopulationStandardDeviation(List<Double> values) {
        if (values == null || values.size() < 2) {
            throw new IllegalArgumentException("Input list must contain at least two values");
        }
        double mean = calculateMean(values);
        double sum = values.stream()
                .mapToDouble(value -> Math.pow(value - mean, 2))
                .sum();
        return roundToDecimalPlaces(Math.sqrt(sum / values.size()));
    }
    
    public double calculateZScore(double value, double mean, double stdDev) {
        if (stdDev == 0) {
            throw new IllegalArgumentException("Standard deviation cannot be zero");
        }
        return roundToDecimalPlaces((value - mean) / stdDev);
    }
    
    private double roundToDecimalPlaces(double value) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(DECIMAL_PLACES, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    // Linear Regression
    public static class RegressionResult {
        private final double slope;
        private final double intercept;
        
        public RegressionResult(double slope, double intercept) {
            this.slope = slope;
            this.intercept = intercept;
        }
        
        public double getSlope() {
            return slope;
        }
        
        public double getIntercept() {
            return intercept;
        }
        
        public String getFormula() {
            return String.format("y = %.15fx + %.15f", slope, intercept);
        }
    }

    public RegressionResult calculateLinearRegression(List<String> xyPairs) {
        if (xyPairs == null || xyPairs.isEmpty()) {
            throw new IllegalArgumentException("No valid points provided");
        }

        List<Point> points = xyPairs.stream()
            .map(this::parsePoint)
            .collect(Collectors.toList());

        double sumX = points.stream().mapToDouble(p -> p.x).sum();
        double sumY = points.stream().mapToDouble(p -> p.y).sum();
        double sumXY = points.stream().mapToDouble(p -> p.x * p.y).sum();
        double sumXX = points.stream().mapToDouble(p -> p.x * p.x).sum();
        int n = points.size();

        double slope = (n * sumXY - sumX * sumY) / (n * sumXX - sumX * sumX);
        double intercept = (sumY - slope * sumX) / n;

        // Round the results
        slope = roundToDecimalPlaces(slope);
        intercept = roundToDecimalPlaces(intercept);

        return new RegressionResult(slope, intercept);
    }
    
    // Predict Y Value
    public double predictY(double x, double slope, double intercept) {
        return slope * x + intercept;
    }
    
    // Helper method to parse x,y pairs
    private Point parsePoint(String pair) {
        String[] parts = pair.trim().split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid point format: " + pair);
        }
        try {
            double x = Double.parseDouble(parts[0].trim());
            double y = Double.parseDouble(parts[1].trim());
            return new Point(x, y);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid numeric values in point: " + pair);
        }
    }
    
    private static class Point {
        double x;
        double y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

}
