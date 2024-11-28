package com.stqap.calculatorlogic.model;

public class CalculationResult {
    private final boolean success;
    private final String result;
    private final String error;
    private final CalculationType type;
    private final String formula;

    private CalculationResult(boolean success, String result, String error, CalculationType type, String formula) {
        this.success = success;
        this.result = result;
        this.error = error;
        this.type = type;
        this.formula = formula;
    }

    public static CalculationResult success(double result, CalculationType type) {
        return new CalculationResult(true, String.valueOf(result), null, type, null);
    }

    public static CalculationResult success(String formula, CalculationType type) {
        return new CalculationResult(true, null, null, type, formula);
    }

    public static CalculationResult error(String error, CalculationType type) {
        return new CalculationResult(false, null, error, type, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getResult() {
        return result;
    }

    public String getError() {
        return error;
    }

    public CalculationType getType() {
        return type;
    }

    public String getFormula() {
        return formula;
    }

    public String getFormattedResult() {
        if (!success) {
            return String.format("Error: %s", error);
        }

        return switch (type) {
            case MEAN -> String.format("Mean: %.15f", Double.parseDouble(result));
            case SAMPLE_STD_DEV -> String.format("Sample Standard Deviation: %.15f", Double.parseDouble(result));
            case POP_STD_DEV -> String.format("Population Standard Deviation: %.15f", Double.parseDouble(result));
            case Z_SCORE -> String.format("Z-Score: %.15f", Double.parseDouble(result));
            case LINEAR_REGRESSION -> formula;
            case PREDICT_Y -> String.format("Predicted Y: %.15f", Double.parseDouble(result));
        };
    }

    public enum CalculationType {
        MEAN("Mean"),
        SAMPLE_STD_DEV("Sample Standard Deviation"),
        POP_STD_DEV("Population Standard Deviation"),
        Z_SCORE("Z-Score"),
        LINEAR_REGRESSION("Linear Regression"),
        PREDICT_Y("Predict Y");

        private final String displayName;

        CalculationType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}