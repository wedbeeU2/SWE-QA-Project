package com.stqap.calculatorlogic;

import java.util.List;

public class DescriptiveStatistics {
    // Validation Functions
    protected void validateNotEmpty(List<Double> values) {
        if (values == null || values.isEmpty()) {
            throw new IllegalArgumentException("Input list cannot be null or empty");
        }
    }

    protected void validateMinimumSize(List<Double> values, int minSize) {
        if (values.size() < minSize) {
            throw new IllegalArgumentException("Input list must contain at least two values");
        }
    }

    protected void validateNonZeroStdDev(double stdDev) {
        if (stdDev == 0) {
            throw new IllegalArgumentException("Standard deviation cannot be zero");
        }
    }

    // Logic Functions
    public double calculateMean(List<Double> values) {
        validateNotEmpty(values);
        double sum = 0.0;
        int count = 0;
        
        // Skip validation in the loop to allow count to stay 0
        for (Double value : values) {
            if (value != null && !value.isInfinite() && !value.isNaN()) {
                sum += value;
                count++;
            }
        }
        
        if (count == 0) {
            throw new IllegalArgumentException("Cannot calculate mean");
        }
        
        return sum / count;
    }

    public double calculateSampleStandardDeviation(List<Double> values) {
        validateNotEmpty(values);
        
        if (values.size() < 2) {
            throw new IllegalArgumentException("Input must contain at least two values");
        }
        
        double mean = calculateMean(values);
        
        // Using stream to ensure lambda coverage
        double sumSquares = values.stream()
            .mapToDouble(value -> {
                double diff = value - mean;
                return diff * diff;  // Using separate line to ensure lambda coverage
            })
            .sum();
            
        return Math.sqrt(sumSquares / (values.size() - 1));
    }

    public double calculatePopulationStandardDeviation(List<Double> values) {
        validateNotEmpty(values);
        validateMinimumSize(values, 2);
        
        double mean = calculateMean(values);
        
        // Using stream to ensure lambda coverage
        double sumSquares = values.stream()
            .mapToDouble(value -> {
                double diff = value - mean;
                return diff * diff;  // Using separate line to ensure lambda coverage
            })
            .sum();
            
        return Math.sqrt(sumSquares / values.size());
    }

    public double calculateZScore(double value, double mean, double stdDev) {
        validateNonZeroStdDev(stdDev);
        return (value - mean) / stdDev;
    }
}