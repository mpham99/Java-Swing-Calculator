/*
Filename: CalculatorViewController.java
Author: Minh Duc Pham
Course: CST8221 - JAP, Lab Section: 313
Assignment #: 1 - Part 2
Date: November 1st 2019
Professor: Daniel Cormier
Purpose: Building and operating the calculator GUI
Class list: CalculatorViewController, Controller
 */

package calculator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * Purpose: This class set the GUI of the calculator
 * @author Minh Duc Pham
 * @version 1.2
 * @see javax.swing java.awt 
 * @since 1.8.0_221
 */
public class CalculatorViewController extends JPanel {
  //Local variables
  private JTextField display1; //the calculator display1 reference
  private JTextField display2; //the calculator display2 reference
  private JLabel error; //the mode/error display label reference
  private JButton dotButton; //the decimal point (dot) button reference
  private JButton [] hexButtons; //reference to container for alphabetical hex buttons
  
  /*
   * The check box, radio buttons and group buttons used to be declared inside the Constructor
   * , but in order to manipulate the buttons, these members declaration must be moved so that 
   * the Controller classes (Controller, keyController, mouseController) have access to it.
   */
  private ButtonGroup groupButtons; //Move groupButtons declaration to have access inside Controller classes
  private JRadioButton radio1; //Move declaration to have access inside Controller classes
  private JRadioButton radio2; //Move declaration to have access inside Controller classes
  private JRadioButton radio3; //Move declaration to have access inside Controller classes
  private JCheckBox checkBox; //Move the declaration to have access inside the Controller classes
  
  /*
   * The following members are checkpoint and String that will be used to
   * display the calculator properly and return the right value.
   */
  private String textDisplay1 = ""; //String for the display1
  private String textDisplay2 = ""; //String for the display2
  private String tracking = ".00";  //Tracking value of the mode buttons
  private String in = ""; //Pass the string value of each button to called methods
  private boolean operatorButtonPressed = false; //Boolean if the operator button is Pressed
  private boolean firstOperatorPress = false;  //Boolean true if the operator button is pressed for the 1st time
  private boolean secondOperatorPress = false; //Boolean true if the operator button is pressed before =
  private boolean backspaceTrack = false; //Boolean true if backspace button is operational (after =)
  
  CalculatorModel model = new CalculatorModel();

  //Default constructor
  public CalculatorViewController() {
    //Font to customized the button font
    Font newFont;

    //Set the black matte border
    this.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.black));

    //Set the main panel layout
    this.setLayout(new BorderLayout());

    //Handler for the buttons
    Controller handler = new Controller();
    keyController kHandler = new keyController();
    mouseController mHandler = new mouseController();
   
    //Add the key listener
    this.addKeyListener(kHandler);
    this.setFocusable(true);      

    //Create mode/error display label
    error = new JLabel(); //new JLabel for the error display
    error.setPreferredSize(new Dimension(52,55));
    error.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 5, Color.BLACK));
    error.setText("F");
    error.setHorizontalAlignment(SwingConstants.CENTER);
    error.setVerticalAlignment(SwingConstants.CENTER);
    error.setOpaque(true);
    error.setBackground(Color.YELLOW);
    newFont = new Font(error.getFont().getName(), error.getFont().getStyle(), 20);
    error.setFont(newFont);

    //Create calculator display
    int width1, width2; //width size for the two displays
    JPanel displayPanel = new JPanel(); //new JPanel for the two JTextField displays
    displayPanel.setLayout(new BorderLayout());
    display1 = new JTextField();
    display2 = new JTextField("0.0");

    //Set the size and other configurations for display1 and display2
    display1.setColumns(14);
    display2.setColumns(14);
    width1 = (int) display1.getSize().getWidth();
    width2 = (int) display2.getSize().getWidth();
    display1.setPreferredSize(new Dimension(width1, 30));
    display2.setPreferredSize(new Dimension(width2, 30));
    display1.setBorder(new LineBorder (Color.BLACK, 0));
    display2.setBorder(new LineBorder (Color.BLACK, 0));
    display1.setEditable(false);
    display2.setEditable(false);
    display1.setHorizontalAlignment(SwingConstants.RIGHT);
    display2.setHorizontalAlignment(SwingConstants.RIGHT);

    //Add the display1 and display2 to the displayPanel
    displayPanel.add(display1, BorderLayout.NORTH);
    displayPanel.add(display2, BorderLayout.SOUTH);

    //Create backspace button
    JButton backspace = new JButton(); //new JButton for the backspace
    backspace.setText("\u21DA");
    backspace.addActionListener(handler);
    backspace.setActionCommand("Backspace");
    backspace.setPreferredSize(new Dimension(52, 55));
    backspace.setOpaque(false);
    backspace.setContentAreaFilled(false);
    backspace.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 1, Color.BLACK));
    backspace.setToolTipText("Backspace (Alt-B)");
    backspace.setMnemonic('B');
    newFont = new Font(backspace.getFont().getName(), backspace.getFont().getStyle(), 20);
    backspace.setFont(newFont);

    //Create modePanel
    JPanel modePanel = new JPanel(); //New JPanel for the two JPanel buttons/check box
    modePanel.setLayout(new BorderLayout());
    modePanel.setBorder(new EmptyBorder(10, 0, 10, 0));
    modePanel.setBackground(Color.BLACK);

    //Create group buttons
    JPanel buttonPanel = new JPanel(); //New JPanel for the three radio buttons
    buttonPanel.setLayout(new GridLayout());
    JPanel boxPanel = new JPanel(); //New JPanel for the check box
    boxPanel.setLayout(new GridLayout());
    groupButtons = new ButtonGroup();// Button group for all four mode buttons
    radio1 = new JRadioButton(".0", false);
    radio2 = new JRadioButton(".00", true);
    radio3 = new JRadioButton("Sci", false);
    checkBox = new JCheckBox("Hex", false);

    //Add to the group button and the frame
    groupButtons.add(radio1);
    groupButtons.add(radio2);
    groupButtons.add(radio3);
    groupButtons.add(checkBox);
    boxPanel.add(checkBox);
    buttonPanel.add(radio1);
    buttonPanel.add(radio2);
    buttonPanel.add(radio3);

    //Configuring the radio button
    radio1.setBackground(Color.YELLOW);
    radio2.setBackground(Color.YELLOW);
    radio3.setBackground(Color.YELLOW);
    checkBox.setBackground(Color.GREEN);

    //Add Action Listener for the button
    radio1.addActionListener(handler);
    radio2.addActionListener(handler);
    radio3.addActionListener(handler);
    checkBox.addActionListener(handler);
    checkBox.addMouseListener(mHandler);
    
    //Set Action command for the button
    radio1.setActionCommand(".0");
    radio2.setActionCommand(".00");
    radio3.setActionCommand("Sci");
    checkBox.setActionCommand("Hex");

    //Add buttons panel and box to mode panel
    modePanel.add(buttonPanel, BorderLayout.EAST);
    modePanel.add(boxPanel, BorderLayout.WEST);

    //Panel holds calculator display, mode/error label, backspace button
    JPanel topPanel = new JPanel(); //New JPanel for the display mode buttons, error label and the backspace
    this.add(topPanel, BorderLayout.NORTH);
    topPanel.setLayout(new BorderLayout());
    topPanel.setBackground(Color.YELLOW);
    topPanel.add(error, BorderLayout.WEST);
    topPanel.add(displayPanel, BorderLayout.CENTER);
    topPanel.add(backspace, BorderLayout.EAST);
    topPanel.add(modePanel, BorderLayout.SOUTH);

    //Button for * and /
    JButton multiplyButton; 
    JButton divideButton;
    multiplyButton = createButton("*", "*", Color.BLACK, Color.CYAN, handler);
    divideButton = createButton("/", "/", Color.BLACK, Color.CYAN, handler);
    multiplyButton.setPreferredSize(new Dimension(48, 45));
    divideButton.setPreferredSize(new Dimension(48 ,45));

    //Panel holds operator * and / button
    JPanel leftPanel = new JPanel(); //New JPanel for the * and / button, stays in the left
    leftPanel.setLayout(new GridLayout(2, 1, 3, 3));
    this.add(leftPanel, BorderLayout.WEST);
    leftPanel.setBackground(Color.BLACK);
    leftPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 4, Color.BLACK));
    leftPanel.add(multiplyButton);
    leftPanel.add(divideButton);

    //Button for + and -
    JButton addButton;
    JButton minusButton;
    addButton = createButton("+", "+", Color.BLACK, Color.CYAN, handler);
    minusButton = createButton("-", "-", Color.BLACK, Color.CYAN, handler);
    addButton.setPreferredSize(new Dimension(48, 45));
    minusButton.setPreferredSize(new Dimension(48 ,45));

    //Panel holds operator + and - button
    JPanel rightPanel = new JPanel(); //New JPanel for the + and -, stays in the right 
    rightPanel.setLayout(new GridLayout(2, 1, 3, 3));
    rightPanel.setBorder(BorderFactory.createMatteBorder(0, 4, 0, 0, Color.BLACK));
    this.add(rightPanel, BorderLayout.EAST);
    rightPanel.setBackground(Color.BLACK);
    rightPanel.add(addButton);
    rightPanel.add(minusButton);

    //Button for C and =
    JButton clearButton;
    JButton equalButton;
    clearButton = createButton("C", "Clear", Color.BLACK, Color.RED, handler);
    equalButton = createButton("=", "=", Color.BLACK, Color.YELLOW, handler);

    //Panel for the keypads button
    JPanel keypad = new JPanel(); //New JPanel for the keypads only
    keypad.setLayout(new GridLayout(6, 3, 3, 3));

    //For - loop to create hexadecimal and numbers buttons
    char buttonText[] = new char[] {'A', 'B', 'C', 'D', 'E', 'F', '7', '8', '9', '4', '5', 
        '6', '1', '2', '3', '.', '0', '\u00B1'}; //An array of char for all the buttons'text
    int y = 0;//counter for the hexButtons array
    hexButtons = new JButton[6];//hexButtons array
    for (int i = 0; i < 18; i++) {
      if(Character.isLetter(buttonText[i]) == true) {
        hexButtons[y] = createButton(Character.toString(buttonText[i]), 
            Character.toString(buttonText[i]),
            Color.WHITE,
            Color.BLUE,
            handler);
        hexButtons[y].setEnabled(false);
        keypad.add(hexButtons[y]);
        y++;

      }else if(Character.isDigit(buttonText[i]) == true) {
        keypad.add(createButton(Character.toString(buttonText[i]),
            Character.toString(buttonText[i]),
            Color.BLACK,
            Color.BLUE,
            handler));

      }else if(buttonText[i] == '.') {
        dotButton = createButton(Character.toString(buttonText[i]),
            Character.toString(buttonText[i]),
            Color.BLACK,
            Color.MAGENTA,
            handler);
        keypad.add(dotButton);
      }else
        keypad.add(createButton(Character.toString(buttonText[i]),
            "Plus/Minus",
            Color.BLACK,
            Color.MAGENTA, 
            handler));
    }

    //Panel for the keypads
    JPanel keypadPanel = new JPanel(); //New JPanel for the keypads with C and = buttons
    keypadPanel.setLayout(new BorderLayout());
    this.add(keypadPanel, BorderLayout.CENTER);
    keypadPanel.add(clearButton, BorderLayout.NORTH);
    keypadPanel.add(equalButton, BorderLayout.SOUTH);
    keypadPanel.add(keypad, BorderLayout.CENTER);

  }

  /**
   * Purpose: This class listen and handle to ActionEvent when a button is presse/clicked
   * @author Minh Duc Pham
   * @version 1.0
   * @see java.awt.event.ActionListener 
   * @since 1.8.0_221
   */
  //Private Inner Class Controller
  private class Controller implements ActionListener {
    
    /** Purpose: Handle the ActionEvent when user click on a buttons.
     *  @param ae the ActionEvent object.
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
      
      in = ae.getActionCommand();
      //If user press an number button
      if(Character.isDigit(in.charAt(0)) || in.equals(".")) {
        keypadHandler(in);
      }

      //Button selections
      switch(in) {
        //Mode buttons
        case "Hex":
          hexHandler();
          break;

        case ".0": case ".00": case "Sci":
          floatHandler(in);
          break;

        case "*": case "/": case "+": case "-": //done
          operatorHandler(in);
          break;

          //Backspace button
        case "Backspace": //Done
          backspaceHandler();
          break;

          //Hex buttons
        case "A": case "B": case "C": case "D": case "E": case "F": //done
          //Reset the screen if the operator is pressed
          hexButtonHandler(in);
          break;

          //+/- button
        case "Plus/Minus":
          pmHandler();
          break;

          //Equal button
        case "=": //done
          equalHandler();
          break;

          //Clear button
        case "Clear": //done
          clearHandler();
          break;

      }//end of switch
     CalculatorViewController.this.requestFocus(); 
    }//end of actionPerformed
    
  }//end of Controller


  /**
   * Purpose: This class listen and handle to KeyEvent when a button is pressed on the keyboard
   * @author Minh Duc Pham
   * @version 1.0
   * @see java.awt.event.KeyListener 
   * @since 1.8.0_221
   */
  //Private Inner Class Controller for KeyListener
  private class keyController implements KeyListener {
    /** Purpose: Handle the KeyListener when using keyboard input.
     *  @param ke the KeyEvent object.
     */
    @Override
    public void keyPressed(KeyEvent ke) {
      in = String.valueOf(ke.getKeyChar()).toUpperCase();
      
      if(Character.isDigit(in.charAt(0)) || in.equals(".")) {
        keypadHandler(in);
      }

      switch(in) {
        case "*": case "/": case "+": case "-":
          operatorHandler(in);
          break;
          //Hex buttons
        case "A": case "B": case "C": case "D": case "E": case "F":
          //Reset the screen if the operator is pressed
          if(tracking.contentEquals("Hex"))
            hexButtonHandler(in);
          break;
        case "=":
          equalHandler();
          break;
      }
      
      switch(ke.getKeyCode()) {
        case KeyEvent.VK_BACK_SPACE:
          backspaceHandler();
          break;
        case KeyEvent.VK_DELETE:
          clearHandler();
          break;
        case KeyEvent.VK_ENTER:
          equalHandler();
          break;
      }
      
      if(ke.isAltDown()) {
       switch(ke.getKeyChar()) {
         case 'm':
           pmHandler();
           break;
         case 'h':
           hexHandler();
           groupButtons.setSelected(checkBox.getModel(), true);
           break;
         case 'j':
           floatHandler(".0");
           groupButtons.setSelected(radio1.getModel(), true);
           break;
         case 'k':
           floatHandler(".00");
           groupButtons.setSelected(radio2.getModel(), true);
           break;
         case 'l':
           floatHandler("Sci");
           groupButtons.setSelected(radio3.getModel(), true);
           break;
       }
      }
      
    }//end of keyPressed

    @Override
    public void keyTyped(KeyEvent ke) {}
    @Override
    public void keyReleased(KeyEvent ke){}

  }//end of keyController
  
  /**
   * Purpose: This class listen and handle to MouseEvent when a button is pressed on the keyboard
   * @author Minh Duc Pham
   * @version 1.0
   * @see java.awt.event.MouseListener 
   * @since 1.8.0_221
   */
  //Private Inner Class Controller for MouseListener
  private class mouseController implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent me) {
      if (me.getClickCount() == 2) {
        floatHandler(".00");
        groupButtons.setSelected(radio2.getModel(), true);
      }
      CalculatorViewController.this.requestFocus();
    }
    @Override
    public void mouseExited(MouseEvent me) {
      CalculatorViewController.this.requestFocus();
    }
    
    @Override
    public void mousePressed(MouseEvent me) {CalculatorViewController.this.requestFocus();}
    @Override
    public void mouseEntered(MouseEvent me) {}
    @Override
    public void mouseReleased(MouseEvent me) {CalculatorViewController.this.requestFocus();}
  }
  
  /** Purpose: Handle the numpad 
   *  @param input The string returned for each button
   */
  private void keypadHandler(String input) {
    //Reset the screen if the operator is pressed
    if(!model.getError()) {
      if(firstOperatorPress == true) {
        secondOperatorPress = true;
      }

      if (operatorButtonPressed == true) {
        textDisplay2 = "";
        operatorButtonPressed = false;
      }
      textDisplay2 += input;
      display2.setText(textDisplay2);
    }
    backspaceTrack = true;
  }

  /** Purpose: Handle the Hex mode check box.
   */
  private void hexHandler() {
    if(!model.getError()) {
      model.setOperationMode("Hex");
      error.setBackground(Color.green);
      error.setText("H");
      textDisplay2 = "";
      textDisplay1 = "";
      display2.setText("0");
      display1.setText("");
      //Reset the firstPress check point
      firstOperatorPress = false;
      secondOperatorPress = false;
      dotButton.setEnabled(false);
      for(int i = 0; i < hexButtons.length; i++)
        hexButtons[i].setEnabled(true);
    }
    tracking = "Hex";
  }

  /** Purpose: Handle the float radio buttons.
   *  @param input The string returned for each button
   */
  private void floatHandler(String input) {
    if(!model.getError()) {
      model.setOperationMode("Dec");
      model.setPrecisionMode(input);
      error.setBackground(Color.yellow);
      error.setText("F");
      textDisplay2 = "";
      textDisplay1 = "";
      display2.setText("0.0");
      display1.setText("");
      //Reset the firstPress check point
      firstOperatorPress = false;
      secondOperatorPress = false;
      dotButton.setEnabled(true);
      for(int i = 0; i < hexButtons.length; i++)
        hexButtons[i].setEnabled(false);
    }
    tracking = input;
  }

  /** Purpose: Handle the operation buttons (+, -, /. *).
   *  @param input The string returned for each button.
   */
  private void operatorHandler(String input) {
    if(!model.getError()) {
      //Set the operand
      model.setOperand2(textDisplay2);

      //Set the 1st Display 
      if(!firstOperatorPress) {
        textDisplay1 = textDisplay2;
        firstOperatorPress = true;
      }

      /*If an operator button is pressed another time before user pressed "=", 
      calculate the number form two displays and put it in the display1 for
      further calculating*/
      if(secondOperatorPress) {
        model.setOperand1(textDisplay1);
        model.setOperand2(textDisplay2);
        textDisplay1 = model.getResult();
      }

      //Display the 1st display text with the operator
      //textDisplay1 += ae.getActionCommand();
      display1.setText(textDisplay1 + input);

      operatorButtonPressed = true;
      model.setOperation(input);  
    }
  }

  /** Purpose: Handle the backspace button
   */
  private void backspaceHandler() {
    if(backspaceTrack) {
      if(textDisplay2.length() > 0 && !model.getError()) {
        textDisplay2 = textDisplay2.substring(0, textDisplay2.length() - 1);
        display2.setText(textDisplay2);
      }
      if(textDisplay2.equals("-") || textDisplay2.contentEquals("")) {
        firstOperatorPress = false;
        switch (tracking) {
          case "Hex":
            display2.setText("0");
            break;
          default:
            display2.setText("0.0");
            break;
        }
      }
    }
  }

  /** Purpose: Handle the hexadecimals value buttons (from A to F).
   *  @param input The string returned by each button.
   */
  private void hexButtonHandler(String input) {
    if(!model.getError()) {
      if (operatorButtonPressed == true) {
        model.setOperand1(textDisplay2);
        textDisplay2 = "";
        operatorButtonPressed = false;
      }
      textDisplay2 += input;
      display2.setText(textDisplay2);  
    }
    backspaceTrack = true;
  }

  /** Purpose: Handle the Plus/Minus button.
   */
  private void pmHandler() {
    if(!model.getError() && !display2.getText().isEmpty()) {
      if(display2.getText().charAt(0) == '-')
        textDisplay2 = display2.getText().substring(1);
      else
        textDisplay2 = '-' + display2.getText();
      display2.setText(textDisplay2);
    }
  }

  /** Purpose: Handle the equal button.
   */
  private void equalHandler() {
    //If the user keep pressing "=" multiple times
    if(!model.getError()) {
      if(textDisplay1.equals(""))
        model.setOperand1(display2.getText());
      //If the user hit operator buttons multiple times and then click "=" 
      else if(secondOperatorPress == true) {
        model.setOperand1(display1.getText().replaceAll(".$", ""));
        model.setOperand2(display2.getText());
      }
      //Normal cases
      else
        model.setOperand1(display2.getText());

      //Display the result
      textDisplay2 = model.getResult();
      display2.setText(textDisplay2);

      //Check if error is happened
      if (model.getError() == true) {
        error.setBackground(Color.red);
        error.setText("E");
      }else {
        textDisplay1 = "";
        display1.setText(textDisplay1);
      }
     
      firstOperatorPress = false;
      secondOperatorPress = false;
      backspaceTrack = false;
    }
  }

  /** Purpose: Handle the clear button.
   */
  private void clearHandler() {
    model.resetError();

    //Reset the error panel
    switch (tracking) {
      case "Hex":
        hexHandler();
        textDisplay2 = "0";
        break;
      case ".0":
        floatHandler(tracking);
        textDisplay2 = "0.0";
        break;
      case ".00":
        floatHandler(tracking);
        textDisplay2 = "0.0";
        break;
      case "Sci":
        floatHandler(tracking);
        textDisplay2 = "0.0";
        break;
    
    }

    //Reset the firstPress check point
    firstOperatorPress = false;
    secondOperatorPress = false;

    //Reset the text field 
    textDisplay1 = "";
    display1.setText(textDisplay1);
    display2.setText(textDisplay2);
    textDisplay2 = "";
  
    //Reset the operand
    model.setOperand1("0");
    model.setOperand2("0");
    
    //Reset operation 
    model.setOperation("+");
  }


  /**
   * Purpose: This method creates new button using the argument as configuration
   * @param text The text showed on the button
   * @param ac The ActionCommand for the button
   * @param fg The foreground color of the button
   * @param bg The background color of the button
   * @param handler The ActionListener object of the button
   * @return JButton The newly created button 
   */
  private JButton createButton (String text, String ac, Color fg, Color bg, ActionListener handler) {
    //Create new button
    JButton newButton = new JButton();

    //Add text to button
    newButton.setText(text);

    //Set background and foreground colors of the button
    newButton.setForeground(fg);
    newButton.setBackground(bg);

    //Set action command for the button
    if(ac != null) {
      newButton.setActionCommand(ac);
    }

    //Set button font to 20
    Font newFont = new Font(newButton.getFont().getName(), newButton.getFont().getStyle(), 20);
    newButton.setFont(newFont);

    //Set action event listener for the Button
    newButton.addActionListener(handler);
    newButton.setFocusable(false);
    return newButton;
  }
}//end of CalculatorViewController class
