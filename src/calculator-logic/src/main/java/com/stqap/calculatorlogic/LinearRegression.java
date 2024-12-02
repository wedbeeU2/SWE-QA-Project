package com.stqap.calculatorlogic;

import java.util.List;

public class LinearRegression {
    // Validation Functions
    protected void validatePoints(List<Point> points) {
        if (points == null || points.isEmpty()) {
            throw new IllegalArgumentException("No valid points provided");
        }
        
        // Check for null points
        if (points.stream().anyMatch(p -> p == null)) {
            throw new IllegalArgumentException("Points cannot be null");
        }
    }

    protected void validateRegression(double denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Cannot calculate regression: all X values are the same");
        }
    }

    // Logic Functions
    public static class Point {
        public final double x;
        public final double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public static class RegressionResult {
        public final double slope;
        public final double intercept;

        public RegressionResult(double slope, double intercept) {
            this.slope = slope;
            this.intercept = intercept;
        }
    }

    public RegressionResult calculateRegression(List<Point> points) {
        validatePoints(points);

        double sumX = points.stream().mapToDouble(p -> p.x).sum();
        double sumY = points.stream().mapToDouble(p -> p.y).sum();
        double sumXY = points.stream().mapToDouble(p -> p.x * p.y).sum();
        double sumXX = points.stream().mapToDouble(p -> p.x * p.x).sum();
        int n = points.size();

        double denominator = (n * sumXX - sumX * sumX);
        validateRegression(denominator);

        double slope = (n * sumXY - sumX * sumY) / denominator;
        double intercept = (sumY - slope * sumX) / n;

        return new RegressionResult(slope, intercept);
    }

    public double predictY(double x, double slope, double intercept) {
        return slope * x + intercept;
    }
}