package com.stqap.calculatorlogic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class LogicUnitTests {
    private DescriptiveStatistics stats;
    private LinearRegression regression;

    @BeforeEach
    void setUp() {
        stats = new DescriptiveStatistics();
        regression = new LinearRegression();
    }

    // Sample Standard Deviation Tests
    @Test
    void SampleStandardDeviation_ValidListOfSamples_ReturnsCorrectDeviation() {
        //preq-UNIT-TEST-2
        
        // Arrange
        List<Double> values = Arrays.asList(2.0, 4.0, 4.0, 4.0, 5.0, 5.0, 7.0, 9.0);
        double expected = 2.138089935299395;

        // Act
        double result = stats.calculateSampleStandardDeviation(values);

        // Assert
        assertEquals(expected, result, 0.000000000000001);
    }

    @Test
    void SampleStandardDeviation_ListOfZeros_ReturnsZero() {
        //preq-UNIT-TEST-2
        
        // Arrange
        List<Double> values = Arrays.asList(0.0, 0.0, 0.0, 0.0);

        // Act
        double result = stats.calculateSampleStandardDeviation(values);

        // Assert
        assertEquals(0.0, result, 0.000000000000001);
    }

    @Test
    void SampleStandardDeviation_EmptyList_ThrowsException() {
        //preq-UNIT-TEST-2
        
        // Arrange
        List<Double> values = Collections.emptyList();

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, 
            () -> stats.calculateSampleStandardDeviation(values));
        assertEquals("Input list cannot be null or empty", exception.getMessage());
    }

    @Test
    void SampleStandardDeviation_NullList_ThrowsException() {
        //preq-UNIT-TEST-2
        
        // Arrange
        List<Double> values = null;

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, 
            () -> stats.calculateSampleStandardDeviation(values));
        assertEquals("Input list cannot be null or empty", exception.getMessage());
    }

    // Population Standard Deviation Tests
    @Test
    void PopulationStandardDeviation_ValidListOfTwoOrMore_ReturnsCorrectDeviation() {
        //preq-UNIT-TEST-3
        
        // Arrange
        List<Double> values = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);
        double expected = 1.414213562373095;

        // Act
        double result = stats.calculatePopulationStandardDeviation(values);

        // Assert
        assertEquals(expected, result, 0.000000000000001);
    }

    @Test
    void PopulationStandardDeviation_ListOfZeros_ReturnsZero() {
        //preq-UNIT-TEST-3
        
        // Arrange
        List<Double> values = Arrays.asList(0.0, 0.0, 0.0);

        // Act
        double result = stats.calculatePopulationStandardDeviation(values);

        // Assert
        assertEquals(0.0, result, 0.000000000000001);
    }

    @Test
    void PopulationStandardDeviation_EmptyList_ThrowsException() {
        //preq-UNIT-TEST-3
        
        // Arrange
        List<Double> values = Collections.emptyList();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, 
            () -> stats.calculatePopulationStandardDeviation(values));
    }

    @Test
    void PopulationStandardDeviation_SingleValue_ThrowsException() {
        //preq-UNIT-TEST-3
        
        // Arrange
        List<Double> values = Collections.singletonList(5.0);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, 
            () -> stats.calculatePopulationStandardDeviation(values));
        assertEquals("Input list must contain at least two values", exception.getMessage());
    }

    // Mean Tests
    @Test
    void Mean_ValidList_ReturnsCorrectMean() {
        //preq-UNIT-TEST-4
        
        // Arrange
        List<Double> values = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);
        double expected = 3.0;

        // Act
        double result = stats.calculateMean(values);

        // Assert
        assertEquals(expected, result, 0.000000000000001);
    }

    @Test
    void Mean_EmptyList_ThrowsException() {
        //preq-UNIT-TEST-4
        
        // Arrange
        List<Double> values = Collections.emptyList();

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, 
            () -> stats.calculateMean(values));
        assertEquals("Input list cannot be null or empty", exception.getMessage());
    }

    // Z-Score Tests
    @Test
    void ZScore_ValidParameters_ReturnsCorrectScore() {
        //preq-UNIT-TEST-5
        
        // Arrange
        double value = 11.5;
        double mean = 7.0;
        double stdDev = 1.5811388300841898;
        double expected = 2.846049894151541;

        // Act
        double result = stats.calculateZScore(value, mean, stdDev);

        // Assert
        assertEquals(expected, result, 0.000000000000001);
    }

    @Test
    void ZScore_ZeroMean_ReturnsCorrectScore() {
        //preq-UNIT-TEST-5
        
        // Arrange
        double value = 5.0;
        double mean = 0.0;
        double stdDev = 2.0;
        double expected = 2.5;

        // Act
        double result = stats.calculateZScore(value, mean, stdDev);

        // Assert
        assertEquals(expected, result, 0.000000000000001);
    }

    @Test
    void ZScore_ZeroStdDev_ThrowsException() {
        //preq-UNIT-TEST-5
        
        // Arrange
        double value = 85.0;
        double mean = 70.0;
        double stdDev = 0.0;

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, 
            () -> stats.calculateZScore(value, mean, stdDev));
        assertEquals("Standard deviation cannot be zero", exception.getMessage());
    }

    // Linear Regression Tests
    @Test
    void LinearRegression_ValidXYParameters_ReturnsCorrectFormula() {
        //preq-UNIT-TEST-6
        
        // Arrange
        List<LinearRegression.Point> points = Arrays.asList(
            new LinearRegression.Point(1.0, 2.0),
            new LinearRegression.Point(2.0, 4.0),
            new LinearRegression.Point(3.0, 6.0),
            new LinearRegression.Point(4.0, 8.0)
        );

        // Act
        LinearRegression.RegressionResult result = regression.calculateRegression(points);

        // Assert
        assertEquals(2.0, result.slope, 0.000000000000001);
        assertEquals(0.0, result.intercept, 0.000000000000001);
    }

    @Test
    void LinearRegression_EmptyList_ThrowsException() {
        //preq-UNIT-TEST-6
        
        // Arrange
        List<LinearRegression.Point> points = Collections.emptyList();

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, 
            () -> regression.calculateRegression(points));
        assertEquals("No valid points provided", exception.getMessage());
    }

    @Test
    void LinearRegression_AllXValuesSame_ThrowsException() {
        //preq-UNIT-TEST-6
        
        // Arrange
        List<LinearRegression.Point> points = Arrays.asList(
            new LinearRegression.Point(2.0, 1.0),
            new LinearRegression.Point(2.0, 2.0),
            new LinearRegression.Point(2.0, 3.0)
        );

        // Act & Assert
        assertThrows(IllegalArgumentException.class, 
            () -> regression.calculateRegression(points));
    }

    @Test
    void LinearRegression_AllYValuesSame_ReturnsHorizontalLine() {
        //preq-UNIT-TEST-6
        
        // Arrange
        List<LinearRegression.Point> points = Arrays.asList(
            new LinearRegression.Point(1.0, 2.0),
            new LinearRegression.Point(2.0, 2.0),
            new LinearRegression.Point(3.0, 2.0)
        );

        // Act
        LinearRegression.RegressionResult result = regression.calculateRegression(points);

        // Assert
        assertEquals(0.0, result.slope, 0.000000000000001);
        assertEquals(2.0, result.intercept, 0.000000000000001);
    }

    @Test
    void LinearRegression_AllValuesZero_ThrowsException() {
        //preq-UNIT-TEST-6
        
        // Arrange
        List<LinearRegression.Point> points = Arrays.asList(
            new LinearRegression.Point(0.0, 0.0),
            new LinearRegression.Point(0.0, 0.0)
        );

        // Act & Assert
        assertThrows(IllegalArgumentException.class, 
            () -> regression.calculateRegression(points));
    }

    // Predict Y Tests
    @Test
    void PredictY_ValidParameters_ReturnsCorrectPrediction() {
        //preq-UNIT-TEST-7
        
        // Arrange
        double x = 2.5;
        double slope = 2.0;
        double intercept = 1.0;
        double expected = 6.0;

        // Act
        double result = regression.predictY(x, slope, intercept);

        // Assert
        assertEquals(expected, result, 0.000000000000001);
    }
    @Test
    void Mean_NullList_ThrowsException() {
        //preq-UNIT-TEST-4
        
        // Arrange
        List<Double> values = null;

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, 
            () -> stats.calculateMean(values));
        assertEquals("Input list cannot be null or empty", exception.getMessage());
    }

    @Test
    void PopulationStandardDeviation_NullList_ThrowsException() {
        //preq-UNIT-TEST-3
        
        // Arrange
        List<Double> values = null;

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, 
            () -> stats.calculatePopulationStandardDeviation(values));
        assertEquals("Input list cannot be null or empty", exception.getMessage());
    }

    @Test
    void LinearRegression_NullList_ThrowsException() {
        //preq-UNIT-TEST-6
        
        // Arrange
        List<LinearRegression.Point> points = null;

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, 
            () -> regression.calculateRegression(points));
        assertEquals("No valid points provided", exception.getMessage());
    }

    @Test
    void LinearRegression_ListWithNullPoint_ThrowsException() {
        //preq-UNIT-TEST-6
        
        // Arrange
        List<LinearRegression.Point> points = new ArrayList<>();
        points.add(new LinearRegression.Point(1.0, 2.0));
        points.add(null);
        points.add(new LinearRegression.Point(3.0, 4.0));

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, 
            () -> regression.calculateRegression(points));
        assertEquals("Points cannot be null", exception.getMessage());
    }
    @Test
    void Mean_SingleValue_ReturnsValue() {
        //preq-UNIT-TEST-4
        
        // Arrange
        List<Double> values = Collections.singletonList(5.0);
        double expected = 5.0;

        // Act
        double result = stats.calculateMean(values);

        // Assert
        assertEquals(expected, result, 0.000000000000001);
    }

    @Test
    void SampleStandardDeviation_SingleValue_ReturnsError() {
        //preq-UNIT-TEST-2
        
        // Arrange
        List<Double> values = Collections.singletonList(5.0);

        // Assert
        Exception exception = assertThrows(IllegalArgumentException.class, 
            () -> stats.calculateSampleStandardDeviation(values));
        assertEquals("Input must contain at least two values", exception.getMessage());
    }

    @Test
    void PopulationStandardDeviation_ExtremeValues_ReturnsCorrectResult() {
        //preq-UNIT-TEST-3
        
        // Arrange
        List<Double> values = Arrays.asList(-1000.0, 1000.0);
        double expected = 1000.0;

        // Act
        double result = stats.calculatePopulationStandardDeviation(values);

        // Assert
        assertEquals(expected, result, 0.000000000000001);
    }
    @Test
    void Mean_InfinityValues_ThrowsException() {
        //preq-UNIT-TEST-4
        
        // Arrange
        List<Double> values = Arrays.asList(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY);
        
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, 
            () -> stats.calculateMean(values));
        assertEquals("Cannot calculate mean", exception.getMessage());
    }
    @Test
    void Mean_InfinityValue_ThrowsException() {
        //preq-UNIT-TEST-4
        
        // Arrange
        List<Double> values = Arrays.asList(Double.POSITIVE_INFINITY); // Only infinity value
        
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, 
            () -> stats.calculateMean(values));
        assertEquals("Cannot calculate mean", exception.getMessage());
    }

    @Test
    void Mean_NaNValue_ThrowsException() {
        //preq-UNIT-TEST-4
        
        // Arrange
        List<Double> values = Arrays.asList(Double.NaN); // Only NaN value

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, 
            () -> stats.calculateMean(values));
        assertEquals("Cannot calculate mean", exception.getMessage());
    }
    @Test
    void Mean_AllValuesInfinite_ThrowsException() {
        //preq-UNIT-TEST-4
        
        // Arrange
        // Create a list where every value is infinite, so loop completes but count stays 0
        List<Double> values = new ArrayList<>();
        values.add(Double.POSITIVE_INFINITY);
        values.add(Double.POSITIVE_INFINITY);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, 
            () -> stats.calculateMean(values));
        assertEquals("Cannot calculate mean", exception.getMessage());
    }
    @Test
    void Mean_ZeroCount_ThrowsException() {
        //preq-UNIT-TEST-4
        
        // Arrange
        // Create a list with a null value to skip both the infinite check and count increment
        List<Double> values = new ArrayList<>();
        values.add(null);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, 
            () -> stats.calculateMean(values));
        assertEquals("Cannot calculate mean", exception.getMessage());
    }
    @Test
    void Mean_NoValidValues_ThrowsException() {
        //preq-UNIT-TEST-4
        
        // Arrange
        List<Double> values = Arrays.asList(Double.NaN, Double.POSITIVE_INFINITY, null);
        
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, 
            () -> stats.calculateMean(values));
        assertEquals("Cannot calculate mean", exception.getMessage());
    }
}