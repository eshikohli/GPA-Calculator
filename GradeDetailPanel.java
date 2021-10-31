//This whole code was written independently

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.IOException;


public class GradeDetailPanel extends JPanel implements ActionListener {
   
   
   private JTextField studentNameTf;
   private JTextField gradeTf;
   private JTextField courseTypeTf;
   private JTextField finalSubjectTf;
   private JTextField resultTf;
   
   private JLabel studentNameLbl;
   private JLabel gradeLbl;
   private JLabel courseTypeLbl;
   private JLabel finalSubjectLbl;
   private JLabel resultLbl;
   private JLabel refLinkLbl;

   
   private static String studentNameEntered = "";
   
   private JComboBox gradeCb;
   private JButton b1;
  
  
      
   private static JFrame frame;
   
   private static String  gradeList[] = {"A", "A-", "B+", "B", "B-", "C+", "C", "C-","D+", "D", "F"};
   private static ArrayList<Double> gpaList = new ArrayList<Double>();
   private static HashMap<String,Double> gradeGpaMap =new HashMap<String,Double>();   
   

   
  public GradeDetailPanel()  {

    //Initializing the labels and text fields                                  
              
      studentNameLbl = new JLabel("Student Name: ");
      gradeLbl = new JLabel("Grade: ");
      courseTypeLbl = new JLabel("What type of course is it (Type A for AP course, H for Honors course, N for Normal course): ");
      finalSubjectLbl = new JLabel("Is this the last grade you are entering (Type Y for Yes, N ofor No):");
      resultLbl = new JLabel("Result:  ");
                  
      studentNameTf = new JTextField(50);
      gradeCb =new JComboBox(gradeList);
      courseTypeTf = new JTextField(20);
      finalSubjectTf = new JTextField(20);
      resultTf = new JTextField(20);
      resultTf.setEditable(false);
      b1 = new JButton("Calculate GPA");
     
   //Retaining the Students name if entered before
      if(!isNullOrEmpty(studentNameEntered)){
         studentNameTf.setText(studentNameEntered);
         studentNameTf.setEditable(false);
      }
   
   //Placing the labels and text box created above on the UI 
      
      Box vBox = Box.createVerticalBox();   
         
      vBox.add(studentNameLbl);
      vBox.add(studentNameTf);
      
      vBox.add(gradeLbl);
      vBox.add(gradeCb);
      
      vBox.add(courseTypeLbl);
      vBox.add(courseTypeTf);
      
      vBox.add(finalSubjectLbl);
      vBox.add(finalSubjectTf);
      
      vBox.add(b1);
      b1.addActionListener(this);
     
       setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
       add(vBox); 
       
       setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
       add(Box.createHorizontalStrut(10));
       Box hBox = Box.createVerticalBox();
   
       hBox.add(resultLbl);
       hBox.add(resultTf);
       add(hBox);
      
   }
   
  public void actionPerformed(ActionEvent evt) {
  
         String studentName = studentNameTf.getText();
        String grade = gradeCb.getSelectedItem().toString();
        String courseType = courseTypeTf.getText();
        String finalSubject = finalSubjectTf.getText();
        
        double totalGpa = 0.0;
         
        if(!(isNullOrEmpty(studentName)  || isNullOrEmpty(grade))){
         //Build the list of GPA points
          gpaList.add(calcGpaPoint(grade, courseType)); 
         }
         if( finalSubject.equals("Y")){
         //Display the calculated GPA
            totalGpa = calcGpa(gpaList);
            resultTf.setText("Student " + studentName + " has a GPA of "  +  totalGpa);
         }
         else {
         //Refresh the frame 
            studentNameEntered = studentName;
        
            frame.setVisible(false);
            frame.dispose(); 
           createFrame();
         }
  }
  
 private static boolean isNullOrEmpty(String str) {
        if(str != null && !str.isEmpty())
            return false;
        return true;
    }
    
  private static void setGradeGpaMap() {
      gradeGpaMap.put("A", 4.0);
      gradeGpaMap.put("A-", 3.7);
      gradeGpaMap.put("B+", 3.3);
      gradeGpaMap.put("B", 3.0);
      gradeGpaMap.put("B-", 2.7);
      gradeGpaMap.put("C+", 2.3);
      gradeGpaMap.put("C", 2.0);
      gradeGpaMap.put("C-", 1.7);
      gradeGpaMap.put("D+", 1.3);
      gradeGpaMap.put("D", 1.0);
      gradeGpaMap.put("F", 0.07);
     
  }  
  private double  calcGpaPoint(String grade, String courseType) {
      if(isNullOrEmpty(courseType)) {
         //default is Normal
         courseType = "N";
      }

      double gpa = gradeGpaMap.get(grade).doubleValue();
      if(courseType.equals("A") ) {
            gpa += 1.0;
      } 
      if(courseType.equals("H") ) {
            gpa +=  0.5;
      } 
     return gpa;
  }
  
  private double  calcGpa(ArrayList<Double> gpa) {
      double gpaSum = 0.0;
      int courseCount = gpa.size();
      
      for (int i = 0; i < courseCount; i++) { 
            gpaSum += gpa.get(i).doubleValue();      
      }
      return gpaSum/courseCount;
  }

 
  private  void createFrame() {
      //Intializes the grade to GPA 4.0 point scale mapping
      setGradeGpaMap();
      
      //Intializes the frame and adds pannel 
      frame = new JFrame("My GPA Calculator");
      frame.setSize(400, 400);
      frame.setLocation(50,50);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
       GradeDetailPanel gp = new GradeDetailPanel();
       gp.setOpaque(true);
       frame.setContentPane(gp);
       frame.pack();
       frame.setVisible(true);
  }
  
  
   public static void main(String args[]){
         
          new GradeDetailPanel().createFrame();
          
   }


}