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




class Pixel extends JComponent {
		int xPosition;
		int yPosition;
		Color pixelColor = new Color(0,0,0);
	  public void paint(Graphics g) {  
        g.setColor(pixelColor);  
        g.fillRect(xPosition, yPosition, 5, 5);  
      }  
      public Pixel(int x, int y, int a, int b, int c){
	  	xPosition = x;
		yPosition = y;	
		pixelColor = new Color(a,b,c);
	  }
	  
}

class ComplexNumber {
	double a;
	double b;
	
	public ComplexNumber(double a, double b){
		this.a = a;
		this.b = b;
	}
	public double getA(){
		return a;
	}
	public double getB(){
		return b;
	}
	public ComplexNumber multiply(ComplexNumber j, ComplexNumber k){
		double newA = j.getA()*k.getA() - j.getB()*k.getB();
		double newB = j.getA()*k.getB() + j.getB()*k.getA();
		return new ComplexNumber(newA, newB);
	}
	public ComplexNumber add(ComplexNumber j, ComplexNumber k){
		return new ComplexNumber(j.getA() + k.getA(), j.getB() + k.getB());
	}
	public int modulus(ComplexNumber c){
		return (int) (Math.sqrt(c.getA()*c.getA() + c.getB()*c.getB()));
	}
}


class EquationBank {
	
	public boolean pattern1(int x, int y){
		return (x*y)%7 <= 5;
	}
	
	public boolean pattern2(int x, int y){
		return ((int)(1000*Math.sin(x)) <= y);
	}
	
	public int mandlebrot(int i, int n){
		int counter = 0;
		
		double a = i/300.0 -2;
		double b = -(n/300.0 -1.3);
		
		ComplexNumber c = new ComplexNumber(a,b);				
		
		ComplexNumber z = new ComplexNumber(0,0);				
		
		
		while ((z.modulus(z) < 2) && (counter < 30)){
			z = z.add(z.multiply(z, z), c);
			counter++;
		}
		return counter;
	}
	
}

public class WallPaperGenerator extends JFrame {

	

	
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ArrayList<Pixel> BMP = new ArrayList<Pixel>();
				
					WallPaperGenerator frame = new WallPaperGenerator();
					EquationBank e1 = new EquationBank();
					
					for (int i = 0; i < 1000; i += 5){
						for (int n = 0; n < 1000; n += 5){
							int t = e1.mandlebrot(i, n);
							BMP.add(new Pixel(i,n,(int)(255*(1-t/30.0)),t,255/t));
						}
					}
					for (Pixel pix : BMP) { 
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
	 * Create the frame.
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
