package org.usfirst.frc.team5495;

/**
 * A simple single-pole IIR filter.
 * 
 * To use this, simply create an instance, then add values to the filter. Then, call
 * {@link get} to grab the output.
 * 
 * Ex:
 * 
 * <pre>
 * Filter filter = new Filter(.25); 
 * for (int i=0; i<20; i++){
 *   filter.add(1);
 *   System.out.println(filter.get());
 * }
 * </pre>
 * 
 * Higher coefficient values will yield less filtering. Lower values will lead to
 * more filtering and a slower response rate.
 * 
 * @author Ethan
 */
public class Filter {
	private double value;
	private double coefficent;
	
	public Filter(double coefficent, double startingValue){
		assert coefficent < 1;
		assert coefficent > 0;
		
		this.coefficent = coefficent;
		this.value = startingValue;
	}
	
	public Filter(double coefficent){
		this(coefficent, 0);
	}
	
	public void add(double sample){
		value = value * (1-coefficent) + sample * coefficent;
	}
	
	public double get(){
		return value;
	}
	
	private void test(){
		for (int i=0; i<20; i++){
			add(1);
			System.out.println(get());
		}
	}
}
