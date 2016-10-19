/***********************************************************
* Developer: Minhas Kamal (minhaskamal024@gmail.com)       *
* Date: Dec-2015                                           *
* Modification Date: 16-Oct-2016                           *
* License: MIT License                                     *
***********************************************************/


package com.minhaskamal.egami.convolution;

import com.minhaskamal.egamiLight.Matrix;
import com.minhaskamal.egamiLight.MatrixEditor;

public class ConvolutionRunner {
	public static Matrix applyMask(Matrix matrix, double[][] mask, boolean preserveSize) {
		if(preserveSize==true){
			int verticalBreadth = (mask.length-1)/2, //top and bottom border
				horizontalBreadth = (mask[0].length-1)/2; //left and right border
			matrix = MatrixEditor.createBorder(matrix, horizontalBreadth, verticalBreadth, Matrix.MAX_PIXEL);
		}
		return new Matrix(applyMask(matrix.pixels, mask));
	}
	
	public static int[][][] applyMask(int[][][] pixels, double[][] mask){
		int borderHorizontal = (mask.length-1)/2, //top and bottom border
			borderVertical = (mask[0].length-1)/2; //left and right border
		int newRows = pixels.length-borderHorizontal-borderHorizontal,
			newCols = pixels[0].length-borderVertical-borderVertical,
			type = pixels[0][0].length;
		
		int[][][] newPixels = new int[newRows][newCols][type];
		int[] newPixel = new int[type];
		
		for(int i=0; i<newRows; i++) {
			for(int j=0; j<newCols; j++) {

				for(int k=0; k<type; k++) {
					newPixel[k] = 0;

					for(int m=0; m<mask.length; m++) {
						for(int n=0; n<mask[0].length; n++) {
							newPixel[k] += (mask[m][n] * pixels[i+m][j+n][k]);
						}
					}
				}
				
				newPixels[i][j] =  newPixel.clone();
			}
		}
		
		return newPixels;
	}
	
	
	/**
	 * 0, -1, 0 <br>
	 * -1, 4+a, -1 <br>
	 * 0, -1, 0 <br>
	 */
	public static double[][] getHighboostFilter(int a){
		double[][] mask = new double[][]
				{{0, -1, 0},
		        {-1, 4+a, -1},
		        {0, -1, 0}};
		
		return mask;
	}
	
	/**
	 * 0, -1, 0 <br>
	 * -1, 4, -1 <br>
	 * 0, -1, 0 <br>
	 */
	public static double[][] getLaplacianFilter(){
		return getHighboostFilter(0);
	}
	
	/**
	 * 0, -1, 0 <br>
	 * -1, 5, -1 <br>
	 * 0, -1, 0 <br>
	 */
	public static double[][] getSharpenFilter(){
		return getHighboostFilter(1);
	}
	
	/**
	 * angle==0? top-sobel <br>
	 * angle==90? left-sobel <br>
	 * angle==180? bottom-sobel <br>
	 * angle==270? right-sobel <br>
	 * @param angle rotates anti-clockwise
	 * @return 3*3 sobel mask
	 */
	public static double[][] getSobelFilter(int angle){
		double[][] mask = new double[3][3];
		
		int[][] cycle = new int[][]{
			{0, 2},
			{0, 1},
			{0, 0},
			{1, 0},
			{2, 0},
			{2, 1},
			{2, 2},
			{1, 2}
		};
		
		int[] values = new int[]{
			1, 2, 1, 0, -1, -2, -1, 0
		};
		
		angle = angle%360;
		int startingPoint = angle/45;

		
		for(int i=0, point=startingPoint; i<cycle.length; i++, point++){
			if(point>=cycle.length){
				point=0;
			}
			
			mask[cycle[point][0]][cycle[point][1]] = values[i];
		}
		
		return mask;
	}
	
	/**
	 * @param size size of mask, like- 3, 5, 7.
	 * @param sigma generally 1
	 * @return for size=3 and sigma=1 <br>
	 * 0.075, 0.124, 0.075 <br>
	 * 0.124, 0.204, 0.124 <br>
	 * 0.075, 0.124, 0.075 <br>
	 */
	public static double[][] getGaussianFilter(int size, int sigma){
		double[][] mask = new double[size][size];
		
		double divider1 = 2*Math.PI*sigma*sigma;
		double divider2 = 2*sigma*sigma;
		double sum=0;
		
		int distanceX, distanceY;
		for(int i=0; i<mask.length; i++){	//calculate gaussian
			distanceY = i-(mask.length/2);
			for(int j=0; j<mask[i].length; j++){
				distanceX = j-(mask[i].length/2);
				
				mask[i][j] = Math.exp( -(distanceX*distanceX+distanceY*distanceY)/(divider2) ) / divider1;
				sum+=mask[i][j];
			}
		}
		
		for(int i=0; i<mask.length; i++){	//normalization
			for(int j=0; j<mask[i].length; j++){
				mask[i][j] /= sum;
			}
		}
		
		return mask;
	}
	
	/**
	 * @return 5*5 mask
	 * 0.003, 0.013, 0.022, 0.013, 0.003 <br>
	 * 0.013, 0.06, 0.098, 0.06, 0.013 <br>
	 * 0.022, 0.098, 0.162, 0.098, 0.022 <br>
	 * 0.013, 0.06, 0.098, 0.06, 0.013 <br>
	 * 0.003, 0.013, 0.022, 0.013, 0.003 <br>
	 */
	public static double[][] getGaussianFilter(){
		return getGaussianFilter(5, 1);
	}
	
	/**
	 * -2, -1, 0 <br>
	 * -1, 1, 1 <br>
	 * 0, 1, 2 <br>
	 */
	public static double[][] getEmbossFilter(){
		double[][] mask = new double[][]
				{{-2, -1, 0, 0, 0, 0, 0},
		        {-1, 1, 1, 1, 1, 1, 1},
		        {0, 1, 2, 0, 0, 0, 0}};
		
		return mask;
	}
	
	/**
	 * It is also a laplacian filter.
	 * -1, -1, -1 <br>
	 * -1, 8, -1 <br>
	 * -1, -1, -1 <br>
	 */
	public static double[][] getEdgeFilter(){
		double[][] mask = new double[][]
				{{-1, -1, -1},
		        {-1, 8, -1},
		        {-1, -1, -1}};
		
		return mask;
	}
	
	/**
	 * @param size
	 * @return
	 */
	public static double[][] getMeanFilter(int size){
		double[][] mask = new double[size][size];
		
		for(int i=0; i<mask.length; i++){
			for(int j=0; j<mask[i].length; j++){
				mask[i][j] = 1;
			}
		}
		
		return mask;
	}
	
	
}
