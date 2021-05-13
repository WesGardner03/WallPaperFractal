package edu.texas.se.swing.sample;

import java.awt.Color;  

import java.lang.Math;

import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.util.ArrayList;

/**
* defines a Pixel class which displays a rectangle in a 
* specific place, size, and color
*
*/
class Pixel extends JComponent {
		int xPosition;
		int yPosition;
		Color pixelColor = new Color(0,0,0);
	  public void paint(Graphics g) {  
        g.setColor(pixelColor);  
        g.fillRect(xPosition, yPosition, 5, 5);  
      }  
      /*
      * creates a pixel of specified position and color
      *@param x is the x postition of the left of the object
      *@param y is the y position of the top of the object
      *@param a is the red value of the pixel's color
      *@param b is the green value of the pixel's color (a bit confusing)
      *@param c is the blue value of the pixel's color
      */
      public Pixel(int x, int y, int a, int b, int c){
	  	xPosition = x;
		yPosition = y;	
		pixelColor = new Color(a,b,c);
	  }
	  
}
/*
* defines a Complex Number
* this class allows for complex numbers to be created and modified 
* these complex numbers can added, multiplied, and the modulus can be taken
* this class probably isn't the cleanest way to work with complex numbers but it works
*/
class ComplexNumber {
	double a;
	double b;
	
	public ComplexNumber(double a, double b){ //a is the real portion of the number, b is the complex
		this.a = a;
		this.b = b;
	}
	public double getA(){
		return a;
	}
	public double getB(){
		return b;
	}
	/**
	* takes two complex numbers and multiplies them
	*@param j first complex number
	*@param k second complex number
	*@return product of the complex numbers
	*/
	public ComplexNumber multiply(ComplexNumber j, ComplexNumber k){
		double newA = j.getA()*k.getA() - j.getB()*k.getB();
		double newB = j.getA()*k.getB() + j.getB()*k.getA();
		return new ComplexNumber(newA, newB);
	}
	/**
	* takes two complex numbers and adds them
	*@param j first complex number
	*@param k second complex number
	*@return sum of the complex numbers
	*/
	public ComplexNumber add(ComplexNumber j, ComplexNumber k){
		return new ComplexNumber(j.getA() + k.getA(), j.getB() + k.getB());
	}
	/**
	* Calculates the modulus (distance from origin in the complex plane) of a complex number
	*@param c complex number
	*@return the modulus of the complex number
	*/
	public int modulus(ComplexNumber c){
		return (int) (Math.sqrt(c.getA()*c.getA() + c.getB()*c.getB()));
	}
}

/**
* Provides a group of methods used in creating different patterns
*/
class EquationBank {
	
	public boolean pattern1(int x, int y){ // pattern1 and 2 were created mainly just to play 
		return (x*y)%7 <= 5;           // around with WallPaperGenerator class,
					       // currently vestigial code
	
	public boolean pattern2(int x, int y){
		return ((int)(1000*Math.sin(x)) <= y);
	}
	
	/**
	* For any given point in the complex plane returns how quickly the mandlebrot equation (z=z^2 +c) diverges
	* Note: this method adjusts the input numbers for the specific display of this project, so input (i,n) 
	* will be converted into numbers(a,b) 
	*@param i real component of a complex number
	*@param n imaginary component of a complex number
	*@returns the number of iterations is takes for the mandlebrot set to diverge, limited to 30 cycles
	*/
	public int mandlebrot(int i, int n){
		int counter = 0;
		
		double a = i/300.0 -2; //the constants adjust the scale of the mandelbrot set so it displays the full set on screen
		double b = -(n/300.0 -1.3); //hypothetically these constants could be controlled and edited to create zoom and drag functions
		
		ComplexNumber c = new ComplexNumber(a,b);				
		
		ComplexNumber z = new ComplexNumber(0,0);				
		
		
		while ((z.modulus(z) < 2) && (counter < 30)){
			z = z.add(z.multiply(z, z), c);
			counter++;
		}
		return counter;
	}
	
}
/**
* Contains the main method
* Generates the Mandlebrot set and displays it 
*/
public class WallPaperGenerator extends JFrame {

	

	
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ArrayList<Pixel> BMP = new ArrayList<Pixel>(); //creates arraylist to store all of the pixel objects
				
					WallPaperGenerator frame = new WallPaperGenerator();
					EquationBank e1 = new EquationBank(); //allows access to the mandlebrot method
					
					for (int i = 0; i < 1000; i += 5){ //nested for loop iterates through 2d graphics display
						for (int n = 0; n < 1000; n += 5){
							int t = e1.mandlebrot(i, n); //helps decide color of the pixel based on mandlebrot set
							BMP.add(new Pixel(i,n,(int)(255*(1-t/30.0)),t,255/t)); //creates new pixel for each x,y value
						}
					}
					for (Pixel pix : BMP) { //displays all pixels generated
   						frame.add(pix);
   						frame.setVisible(true); 
					}
					
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Creates the frame where the image is displayed
	 */
	public WallPaperGenerator() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 1000);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
