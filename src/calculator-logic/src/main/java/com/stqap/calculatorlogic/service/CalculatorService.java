package com.stqap.calculatorlogic.service;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class CalculatorService {
    
    private static final int DECIMAL_PLACES = 15;

    public double calculateMean(List<Double> values) {
        if (values == null || values.isEmpty()) {
            throw new IllegalArgumentException("Input list cannot be null or empty");
        }
        
        double sum = values.stream()
                .mapToDouble(Double::doubleValue)
                .sum();
        
        return roundToDecimalPlaces(sum / values.size());
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
    
    public String calculateLinearRegression(List<String> xyPairs) {
        if (xyPairs == null || xyPairs.isEmpty()) {
            throw new IllegalArgumentException("No valid points provided");
        }

        List<Point> points = xyPairs.stream()
                .map(this::parsePoint)
                .toList();

        double sumX = points.stream().mapToDouble(p -> p.x).sum();
        double sumY = points.stream().mapToDouble(p -> p.y).sum();
        double sumXY = points.stream().mapToDouble(p -> p.x * p.y).sum();
        double sumXX = points.stream().mapToDouble(p -> p.x * p.x).sum();
        int n = points.size();

        double slope = (n * sumXY - sumX * sumY) / (n * sumXX - sumX * sumX);
        double intercept = (sumY - slope * sumX) / n;

        return String.format("y = %.15fx + %.15f", 
            roundToDecimalPlaces(slope), 
            roundToDecimalPlaces(intercept));
    }
    
    public double predictY(double x, double slope, double intercept) {
        return roundToDecimalPlaces(slope * x + intercept);
    }
    
    private double roundToDecimalPlaces(double value) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(DECIMAL_PLACES, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    private static class Point {
        double x;
        double y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

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
}
