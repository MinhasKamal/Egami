/***********************************************************
* Developer: Minhas Kamal (minhaskamal024@gmail.com)       *
* Date: 27-Nov-2016                                        *
* License: MIT License                                     *
***********************************************************/

package com.minhaskamal.egami.morphologicalOperation;

import com.minhaskamal.egamiLight.Matrix;


public class MorphologicalOperator {

	
	public static Matrix erode(Matrix matrix, int radious, int threshold){
		Matrix matrix2 = new Matrix(erode(matrix.pixels, threshold));
		for(int i=1; i<radious; i++){
			matrix2 = new Matrix(erode(matrix2.pixels, threshold));
		}
		
		return matrix2;
	}
	
	private static int[][][] erode(int[][][] pixels, int threshold){
	    int[][][] pixels2 = new int[pixels.length][pixels[0].length][Matrix.BLACK_WHITE];
	    
	    for (int i=1; i<pixels.length-1; i++){
	        for (int j=1; j<pixels[i].length-1; j++){
	            if (pixels[i][j][0]>threshold){
	            	pixels2[i][j][0] = Matrix.MAX_PIXEL;
	                pixels2[i-1][j][0] = Matrix.MAX_PIXEL;
	                pixels2[i][j-1][0] = Matrix.MAX_PIXEL;
	                pixels2[i+1][j][0] = Matrix.MAX_PIXEL;
	                pixels2[i][j+1][0] = Matrix.MAX_PIXEL;
	            }
	        }
	    }
	    
	    return pixels2;
	}
	
	public static Matrix dilate(Matrix matrix, int radious, int threshold){
		Matrix matrix2 = new Matrix(erode(matrix.pixels, threshold));
		for(int i=1; i<radious; i++){
			matrix2 = new Matrix(dilate(matrix2.pixels, threshold));
		}
		
		return matrix2;
	}
	
	private static int[][][] dilate(int[][][] pixels, int threshold){
	    int[][][] pixels2 = new int[pixels.length][pixels[0].length][Matrix.BLACK_WHITE];
	    
	    for (int i=0; i<pixels.length; i++){
	        for (int j=0; j<pixels[i].length; j++){
	        	pixels2[i][j][0] = Matrix.MAX_PIXEL;
	        }
	    }
	    
	    for (int i=1; i<pixels.length-1; i++){
	        for (int j=1; j<pixels[i].length-1; j++){
	            if (pixels[i][j][0]<threshold){
	            	pixels2[i][j][0] = Matrix.MIN_PIXEL;
	                pixels2[i-1][j][0] = Matrix.MIN_PIXEL;
	                pixels2[i][j-1][0] = Matrix.MIN_PIXEL;
	                pixels2[i+1][j][0] = Matrix.MIN_PIXEL;
	                pixels2[i][j+1][0] = Matrix.MIN_PIXEL;
	            }
	        }
	    }
	    
	    return pixels2;
	}
}
