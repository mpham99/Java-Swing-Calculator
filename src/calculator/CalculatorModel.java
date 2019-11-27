/*
Filename: CalculatorModel.java
Author: Minh Duc Pham
Course: CST8221 - JAP, Lab Section: 313
Assignment #: 1 - Part 2
Date: November 1st 2019
Professor: Daniel Cormier
Purpose: Responsible for the calculator's calculation
Class list: CalculatorModel
 */

package calculator;

/**
 * Purpose: This class take the user input for calculation and provide the right number formats
 * @author Minh Duc Pham
 * @version 1.2
 * @see javax.swing java.awt 
 * @since 1.8.0_221
 */
public class CalculatorModel {
  //Declare private variables
  private String operand1; //For operand 1
  private String operand2; //For operand 2
  private String operation; //For the operation (+, -, *, /)
  private String operationMode; //For the operation mode
  private String precisionMode; //For the precision mode
  private String result; //For the result
  private boolean errorState; //For the error state

  //Default constructor
  public CalculatorModel() {
    operand1 = "0";
    operand2 = "0";
    operation = "";
    operationMode = "";
    precisionMode = ".00";
    errorState = false;
    result = "";
  }

  /** Purpose: Set the value of operand1.
   *  @param value the value will be set for operand1.
   */
  public void setOperand1(String value) {
    operand1 = value;
  }

 /**
  * Purpose: Set the value of operand2
  * @param value the value will be set for operand2
  */
  public void setOperand2(String value) {
    operand2 = value;
  }

  /**
   * Purpose: Set the arithmetic operations for the calc 
   * @param operationPressed the arithmetic operation
   */
  public void setOperation(String operationPressed) {
    operation = operationPressed;
  }


  /**
   * Purpose: Set the operation mode for the calc, Hex (Int) or Float
   * @param modePressed the operation mode
   */
  public void setOperationMode(String modePressed) {
    operationMode = modePressed;
  }

  /**
   * Purpose: Set the precision mode for the calc in Float mode
   * @param precision the precision mode
   */
  public void setPrecisionMode(String precision) {
    precisionMode = precision;
  }

  /**
   * Purpose: Check the error post and prior to the calculation, and return the result
   * @return String the result to the calculation
   */
  public String getResult() {
    if(!operand1.isEmpty() || !operand2.isEmpty() || !operation.isEmpty())
      result = this.calculate();
    this.setError(); 
    return result;
  }
 

  /**
   * Purpose: Check the invalid cases and set the error state of the calculation
   */
  public void setError() {
    //Undefined result
    if(operand1.equals("0") && operand2.equals("0") && operation.equals("/")) {
      errorState = true;
      result = "Result is undefined";
    }
    
    //Divided by zero
    else if(operand2.equals("0") && operation.equals("/")) {
      errorState = true;
      result = "Cannot divide by zero";
    } 
   
    //NaN and Infinity cases
    else if(result.equals("Infinity") || result.equals("NaN")) 
      errorState = true;
    
  }

  /**
   * Purpose: Reset the error state to default
   */
  public void resetError() {
    errorState = false;
  }
  
  /**
   * Purpose: Get the error state 
   * @return boolean the error state
   */
  public boolean getError() {
    return errorState;
  }

  /**
   * Purpose: Do the math and return the result in the right format
   * @return String the result in the right format
   */
  private String calculate() {
    String result = "";
    //Float Mode
    this.setError();
    
    if(operationMode != "Hex" && !errorState) {
      double doubleOp1 = 0.0;
      double doubleOp2 = 0.0;
      double doubleResult = 0.0;

      //Convert String to Double
      try {
        doubleOp1 = Double.parseDouble(operand1);
        doubleOp2 = Double.parseDouble(operand2);
      } catch (NumberFormatException e) {
        errorState = true;
        return "Number is improperly formatted";
      }

      //Do the calculate number
      switch(operation) {
        case "+":
          doubleResult = doubleOp1 + doubleOp2;
          break;
        case "-":
          doubleResult = doubleOp1 - doubleOp2;
          break;
        case "*":
          doubleResult = doubleOp1 * doubleOp2;
          break;
        case "/":
          doubleResult = doubleOp1 / doubleOp2;
          break;          
      }

      //Return the right format
      switch(precisionMode) {
        case ".0":
          result = String.format("%.1f", doubleResult);
          break;
        case ".00":
          result = String.format("%.2f", doubleResult);
          break;
        case "Sci":
          result = String.format("%E", doubleResult);
          break;
      }
    }

    //Integer(Hexadecimal) Mode
    if(operationMode == "Hex" && !errorState) {
      int intOp1 = 0;
      int intOp2 = 0;
      int intResult = 0;
      //Convert String to Integer
      try {
        intOp1 = Integer.parseInt(operand1, 16);
        intOp2 = Integer.parseInt(operand2, 16); 
      }catch (NumberFormatException e) {
        errorState = true;
        return "Number is improperly formatted";
      }
      
      if(intOp1 == 0 && operation == "/")
        return "Cannot divide by zero";
      
      //Do the calculate number
      switch(operation) {
        case "+":
          intResult = intOp1 + intOp2;
          break;
        case "-":
          intResult = intOp1 - intOp2;
          break;
        case "*":
          intResult = intOp1 * intOp2;
          break;
        case "/":
          intResult = intOp1 / intOp2;
          break;          
      }
     if(intOp1 < 0 || intOp2 <0)
       result = Integer.toString(intResult);
     else
       result = Integer.toHexString(intResult).toUpperCase();
      
    }
    
    //Result is more than 14 digits
    if(result.length() >= 14) {
      errorState = true;
      result = "The result is longer than the display!";
    }
    return result;
  }//end of calculate

}//end of CalculateModel

