package com.stqap.web.model;

public class CalculationResultModel {
    private final boolean success;
    private final String result;
    private final String error;

    private CalculationResultModel(boolean success, String result, String error) {
        this.success = success;
        this.result = result;
        this.error = error;
    }

    public static CalculationResultModel success(String result) {
        return new CalculationResultModel(true, result, null);
    }

    public static CalculationResultModel error(String error) {
        return new CalculationResultModel(false, null, error);
    }

    public boolean isSuccess() { return success; }
    public String getResult() { return result; }
    public String getError() { return error; }
}
