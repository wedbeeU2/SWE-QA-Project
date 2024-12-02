package com.stqap.web.controller;

import com.stqap.web.model.CalculationModel;
import com.stqap.web.model.CalculationResultModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class CalculatorController {
    
    private final CalculationModel calculationModel;
    
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
                    String.format("Mean: %.15f", calculationModel.performMeanCalculation(values));
                case "sample-std" -> 
                    String.format("Sample Standard Deviation: %.15f", calculationModel.performSampleStdDev(values));
                case "pop-std" -> 
                    String.format("Population Standard Deviation: %.15f", calculationModel.performPopulationStdDev(values));
                case "zscore" -> 
                    String.format("Z-Score: %.15f", calculationModel.performZScore(values));
                case "regression" ->
                    calculationModel.calculateLinearRegression(values);
                case "predict-y" ->
                    String.format("Predicted Y: %.15f", calculationModel.predictY(values));
                default -> throw new IllegalArgumentException("Unknown calculation type");
            };
            return CalculationResultModel.success(result);
        } catch (Exception e) {
            return CalculationResultModel.error(e.getMessage());
        }
    }
}