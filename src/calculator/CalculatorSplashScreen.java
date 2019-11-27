/*
Filename: CalculatorSplashScreen.java
Author: Minh Duc Pham
Course: CST8221 - JAP, Lab Section: 313
Assignment #: 1 - Part 2
Date: November 1st 2019
Professor: Daniel Cormier
Purpose: Building the calculator splash screen
*/

package calculator;

import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * Purpose: This class set the splash screen of the application
 * @author Minh Duc Pham
 * @version 1.2
 * @see javax.swing java.awt 
 * @since 1.8.0_221
 */
public class CalculatorSplashScreen extends JWindow {
  /** Splash screen duration */
  private int duration = 0;
  
  //Progress bar component
  private JProgressBar progressBar;
  private static final int PB_MINIMUM=0;
  private static final int PB_MAXIMUM=300;
  private int r, g, b;
  
  //Parameterized constructor
  public CalculatorSplashScreen(int duration) {
    this.duration = duration;
    r = 4;
    g = 151;
    b = 1;
  }
  
  /** Purpose: Updates the progress bar. Switches different display modes.
   *  @param newValue the new progress value.
   */
  public void updateProgressBar(int newValue) {
    int currentValue = progressBar.getValue();
    Color customColor = new Color(r, g, b);
    progressBar.setForeground(customColor);
    if(currentValue <100){
      // at the beginning switch to Indeterminate mode
      if(currentValue == 1){
        progressBar.setIndeterminate(true);
      }
      progressBar.setValue(newValue); 
      return; 
    }
    progressBar.setIndeterminate(false);
    progressBar.setStringPainted(true);
    progressBar.setValue(newValue); 
    //Constantly change the bar color
    customColor = new Color(r, g, b);
    progressBar.setForeground(customColor);
    if (r < 252 && b < 252) {
      r += 2;
      b += 2;
    }
    return;       
  }
  
  /**
   * Purpose: Displaying a splash screen before the launch of the application.
   */
  public void showSplashWindow() {
    //sizes of the image for splash screen
    int width =  630+10;
    int height = 630+10;

    JPanel content = new JPanel(new BorderLayout());
    content.setBackground(Color.GRAY);

    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (screen.width-width)/2;
    int y = (screen.height-height)/2;

    //set the location and the size of the window
    setBounds(x,y,width,height);
    //Use the image
    JLabel label = new JLabel(new ImageIcon(getClass().getResource("image.jpg")));

    JLabel demo = new JLabel("Minh Duc Pham - 040905103", JLabel.CENTER);
    demo.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 20));
    
    //Initialize the progress bar
    setLayout(new BorderLayout());
    progressBar = new JProgressBar();
    progressBar.setMinimum(PB_MINIMUM);
    progressBar.setMaximum(PB_MAXIMUM);
    progressBar.setPreferredSize(new Dimension(80,20));
    
    //Initialize the current
    content.add(label, BorderLayout.CENTER);
    content.add(demo, BorderLayout.SOUTH);
    // Add the progress bar
    content.add(progressBar, BorderLayout.NORTH);
    // create custom RGB color
    Color customColor = new Color(44, 197, 211);
    content.setBorder(BorderFactory.createLineBorder(customColor, 10));

    //replace the window content pane with the content JPanel
    setContentPane(content);

    //make the splash window visible
    setVisible(true);

    for (int i = PB_MINIMUM; i <= PB_MAXIMUM; i++) {
      final int percent=i;
      
      try {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
              updateProgressBar(percent);
            }
        });
        //make the program inactive for a while so that the GUI thread can do its work
        java.lang.Thread.sleep(15);
      } catch (InterruptedException e) {;}
    } 
    dispose();
  }

}//end of CalculatorSplashScreen
