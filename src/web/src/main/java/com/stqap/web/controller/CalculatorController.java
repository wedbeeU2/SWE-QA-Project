package com.stqap.web.controller;

import com.stqap.web.model.CalculationModel;
import com.stqap.web.model.CalculationResultModel;

import java.text.DecimalFormat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class CalculatorController {
    
    private final CalculationModel calculationModel;

    private String formatNumber(double value) {
        DecimalFormat df = new DecimalFormat("#.###############");
        return df.format(value);
    }
    
    public CalculatorController() {
        this.calculationModel = new CalculationModel();
    }
    
    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @PostMapping("/calculate/{type}")
    @ResponseBody
    public CalculationResultModel calculate(
            @PathVariable(name = "type") String type, 
            @RequestParam(name = "values") String values) {
        try {
            String result = switch (type) {
                case "mean" -> 
                    formatNumber(calculationModel.performMeanCalculation(values));
                case "sample-std" -> 
                    formatNumber(calculationModel.performSampleStdDev(values));
                case "pop-std" -> 
                    formatNumber(calculationModel.performPopulationStdDev(values));
                case "zscore" -> 
                    formatNumber(calculationModel.performZScore(values));
                case "regression" ->
                    calculationModel.calculateLinearRegression(values);
                case "predict-y" ->
                    formatNumber(calculationModel.predictY(values));
                default -> throw new IllegalArgumentException("Unknown calculation type");
            };
            return CalculationResultModel.success(result);
        } catch (Exception e) {
            return CalculationResultModel.error(e.getMessage());
        }
    }
}