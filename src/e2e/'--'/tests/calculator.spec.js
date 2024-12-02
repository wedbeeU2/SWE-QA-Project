const { test, expect } = require('@playwright/test');

test.describe('CalculatorEndToEndTests', () => {
    test.beforeEach(async ({ page }) => {
        await page.goto('http://localhost:8080');
    });

    test('CalculatorWebUI_PageLoad_HasCorrectTitle', async ({ page }) => {
        //preq-E2E-TEST-5
        await expect(page).toHaveTitle('Calculator');
    });

    test('CalculatorWebUI_ComputeSampleStdDev_ReturnsCorrectResult', async ({ page }) => {
        //preq-E2E-TEST-6
        // Arrange
        const values = '9\n2\n5\n4\n12\n7\n8\n11\n9\n3\n7\n4\n12\n5\n4\n10\n9\n6\n9\n4';
        
        // Act
        await page.locator('textarea').fill(values);
        await page.getByText('Compute Sample Standard Deviation | one value per line').click();
        
        // Assert
        await expect(page.locator('#result')).toContainText('3.060787652326');
    });

    test('CalculatorWebUI_ComputePopStdDevEmptyList_ShowsError', async ({ page }) => {
        //preq-E2E-TEST-7
        // Arrange - leave input empty
        
        // Act
        await page.getByText('Compute Population Standard Deviation | one value per line').click();
        
        // Assert
        await expect(page.locator('.error')).toBeVisible();
    });

    test('CalculatorWebUI_ComputeSampleStdDevSingleValue_ShowsError', async ({ page }) => {
        //preq-E2E-TEST-8
        // Arrange
        await page.locator('textarea').fill('5');
        
        // Act
        await page.getByText('Compute Sample Standard Deviation | one value per line').click();
        
        // Assert
        await expect(page.locator('.error')).toBeVisible();
    });

    test('CalculatorWebUI_ComputeMean_ReturnsCorrectResult', async ({ page }) => {
        //preq-E2E-TEST-9
        // Arrange
        const values = '9\n2\n5\n4\n12\n7\n8\n11\n9\n3\n7\n4\n12\n5\n4\n10\n9\n6\n9\n4';
        
        // Act
        await page.locator('textarea').fill(values);
        await page.getByText('Compute Mean | one value per line').click();
        
        // Assert
        await expect(page.locator('#result')).toContainText('7');
    });

    test('CalculatorWebUI_ComputeZScore_ReturnsCorrectResult', async ({ page }) => {
        //preq-E2E-TEST-10
        // Arrange
        const values = '5.5, 7, 3.060787652326';
        
        // Act
        await page.locator('textarea').fill(values);
        await page.getByText('Compute Z Score | value, mean, stdDev on one line').click();
        
        // Assert
        await expect(page.locator('#result')).toContainText('-0.490069933097155');
    });

    test('CalculatorWebUI_ComputeLinearRegression_ReturnsCorrectFormula', async ({ page }) => {
        //preq-E2E-TEST-11
        // Arrange
        const values = '5,3\n3,2\n2,15\n1,12.3\n7.5,-3\n4,5\n3,17\n4,3\n6.42,4\n34,5\n12,17\n3,-1';
        
        // Act
        await page.locator('textarea').fill(values);
        await page.getByText('Compute Single Linear Regression Formula | one x,y pair per line').click();
        
        // Assert
        await expect(page.locator('#result')).toContainText('y = -0.045961532930936x + 6.933587781374592');
    });

    test('CalculatorWebUI_PredictY_ReturnsCorrectResult', async ({ page }) => {
        //preq-E2E-TEST-12
        // Arrange
        const values = '6, -0.04596, 6.9336';
        
        // Act
        await page.locator('textarea').fill(values);
        await page.getByText('Predict Y from Linear Regression Formula | x, m, b on one line').click();
        
        // Assert
        await expect(page.locator('#result')).toContainText('6.65784');
    });
});