/*
Filename: Calculator.java
Author: Minh Duc Pham
Course: CST8221 - JAP, Lab Section: 313
Assignment #: 1 - Part 2
Date: November 1st 2019
Professor: Daniel Cormier
Purpose: Building and operating the calculator GUI
*/

package calculator;

import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 * Purpose: This class called the method and launch the application window
 * @author Minh Duc Pham
 * @version 1.2
 * @see javax.swing java.awt
 * @since 1.8.0_221
 */
public class Calculator {

  /**
   * Purpose: The application main method
   * @param args Program command line argument
   */
  public static void main (String [] args) {
    //Set the duration of the splash screen to 5 seconds
    int duration = 1000;

    // Create the screen
    CalculatorSplashScreen splashWindow = new CalculatorSplashScreen(duration);
    CalculatorViewController mainPanel = new CalculatorViewController();

    //Show the Splash screen
    splashWindow.showSplashWindow();
    //Create and display the main application GUI
    EventQueue.invokeLater(new Runnable(){
      @Override
      public void run(){
        JFrame frame = new JFrame("Calculator");
        frame.setMinimumSize(new Dimension(380, 540));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.setContentPane(mainPanel);
        frame.setVisible(true);
      }
    });
  }
}