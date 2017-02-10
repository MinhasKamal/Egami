/***********************************************************
* Developer: Minhas Kamal (minhaskamal024@gmail.com)       *
* Date: Dec-2015                                           *
* Modification Date: 05-Oct-2016                           *
* License: MIT License                                     *
***********************************************************/


package com.minhaskamal.egami.matrix;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class MatrixEditor {
	
////----crop----////
	
	
	public static Matrix crop(Matrix matrix, int top, int right, int down, int left){
		return matrix.subMatrix(top, matrix.getRows()-down, left, matrix.getCols()-right);
	}
	
	
////----border----////
	
	
	public static Matrix createBorder(Matrix matrix, int breadth, int pixelValue){
		return createBorder(matrix, breadth, breadth, pixelValue);
	}
	
	/**
	 * 
	 * @param horizontalBreadth left and right side breadth
	 * @param verticalBreadth top and bottom side breadth
	 * @param borderPixel
	 * @return
	 */
	public static Matrix createBorder(Matrix matrix, int horizontalBreadth, int verticalBreadth, int pixelValue){
		int[] borderPixel = MatrixUtilities.getCustomPixel(matrix, pixelValue);
		
		return createBorder(matrix, verticalBreadth, horizontalBreadth, verticalBreadth, horizontalBreadth, borderPixel);
	}
	
	public static Matrix createBorder(Matrix matrix, int topBreadth, int rightBreadth, int bottomBreadth, int leftBreadth,
			int borderPixel[]){
		if(borderPixel.length!=matrix.getType()){
			return matrix;
		}
		
		int rows = matrix.getRows(),
			cols = matrix.getCols();
			
		int newRows = rows+topBreadth+bottomBreadth,
			newCols = cols+rightBreadth+leftBreadth;
		
		Matrix matrix2 = new Matrix(newRows, newCols, matrix.getType());
		
		for(int i=0; i<newRows; i++){
			for(int j=0; j<newCols; j++){
				matrix2.pixels[i][j] = borderPixel.clone();
			}
		}
		
		for(int i=0; i<rows; i++){
			for(int j=0; j<cols; j++){
				matrix2.pixels[i+topBreadth][j+leftBreadth] = matrix.pixels[i][j];
			}
		}
		
		return matrix2;
	}
	
	
////----rotate----////
	
//full rotate//
	/**
	 * Rotate Matrix 90 degree to the left.
	 * @param matrix
	 * @return
	 */
	public static Matrix rotateLeft(Matrix matrix){
		int newRow = matrix.getCols();
		int newCol = matrix.getRows();
		
		Matrix matrix2 = new Matrix(newRow, newCol, matrix.getType());
		for(int i=0; i<newRow; i++){
			int i2 = newRow-1-i;
			for(int j=0; j<newCol; j++){
				matrix2.pixels[i2][j] = matrix.pixels[j][i].clone();
			}
		}
		
		return matrix2;
	}
	
	/**
	 * Rotate Matrix 90 degree to the right.
	 * @param matrix
	 * @return
	 */
	public static Matrix rotateRight(Matrix matrix){
		int newRow = matrix.getCols();
		int newCol = matrix.getRows();
		
		Matrix matrix2 = new Matrix(newRow, newCol, matrix.getType());
		for(int j=0; j<newCol; j++){
			int j2 = newCol-1-j;
			for(int i=0; i<newRow; i++){
				matrix2.pixels[i][j2] = matrix.pixels[j][i].clone();
			}
		}
		
		return matrix2;
	}
		
//flip//
	/**
	 * Flip a Matrix top to bottom.
	 * @param matrix
	 * @return
	 */
	public static Matrix flipVertical(Matrix matrix){
		int row = matrix.getRows();
		int col = matrix.getCols();
		
		Matrix matrix2 = new Matrix(row, col, matrix.getType());
		for(int i=0; i<row; i++){
			int i2 = row-1-i;
			for(int j=0; j<col; j++){
				matrix2.pixels[i2][j] = matrix.pixels[i][j].clone();
			}
		}
		
		return matrix2;
	}

	/**
	 * Flip a Matrix left to right.
	 * @param matrix
	 * @return
	 */
	public static Matrix flipHorizontal(Matrix matrix){
		int row = matrix.getRows();
		int col = matrix.getCols();
		
		Matrix matrix2 = new Matrix(row, col, matrix.getType());
		for(int j=0; j<col; j++){
			int j2 = col-1-j;
			for(int i=0; i<row; i++){
				matrix2.pixels[i][j2] = matrix.pixels[i][j].clone();
			}
		}
		
		return matrix2;
	}
	
//skew//
	public static Matrix skewHorizontal(Matrix matrix, double angle){
		int row = matrix.getRows();
		int col = matrix.getCols();
		
		angle %=90;
		double tanX = Math.tan(Math.toRadians(angle));
		Matrix matrix2 = new Matrix(row, (int)(col + (row*Math.abs(tanX))), matrix.getType());
		
		int x=0;
		if(angle<0){
			x = (int) (row*Math.abs(tanX));
		}
		for(int i=0; i<row; i++){
			int skew = (int) (i*tanX) + x;
			for(int j=0; j<col; j++){
				matrix2.pixels[i][j+skew] = matrix.pixels[i][j].clone();
			}
		}
		
		return matrix2;
	}
	
	
	public static Matrix skewVertical(Matrix matrix, double angle){
		int row = matrix.getRows();
		int col = matrix.getCols();
		
		angle %= 90;
		double tanX = Math.tan(Math.toRadians(angle));
		Matrix matrix2 = new Matrix((int)(row + (col*Math.abs(tanX))), col, matrix.getType());
		
		int y=0;
		if(angle<0){
			y = (int) (col*Math.abs(tanX));
		}
		for(int j=0; j<col; j++){
			int skew = (int) ((col-1-j)*tanX) + y;
			for(int i=0; i<row; i++){
				matrix2.pixels[i+skew][j] = matrix.pixels[i][j].clone();
			}
		}
		
		return matrix2;
	}
	
//rotate//
	
	public static Matrix rotate(Matrix matrix, double angle){
		angle %= 360;
		if(angle<0){
			angle += 360;
		}
		
		Matrix matrix2 = fullRotate(matrix, angle);
		
		angle %= 90;
		if(angle==0){
			return matrix2;
		}
		
		Matrix matrix3 = smallRotate(matrix2, angle);
	    int cropTop = (int) Math.floor( matrix2.getRows() * (1-Math.cos(Math.toRadians(angle))) );
	    
		return matrix3.subMatrix(cropTop, matrix3.getRows(), 0, matrix3.getCols());
	}
	
	/**
	 * If no rotation happens then returns the same Matrix.
	 */
	private static Matrix fullRotate(Matrix matrix, double angle){
		Matrix matrix2;
		if(angle>=270){
			matrix2 = rotateLeft(matrix);
		}else if(angle>=180){
			matrix2 = rotateRight(rotateRight(matrix));
		}else if(angle>=90){
			matrix2 = rotateRight(matrix);
		}else{
			matrix2 = matrix;
		}
		
		return matrix2;
	}
	
	/**
	 * Returns new Matrix.
	 */
	private static Matrix smallRotate(Matrix matrix, double angle){
		BufferedImage originalBufferedImage = Matrix.matrixToBufferedImage(matrix);
		BufferedImage rotatedBufferedImage = new BufferedImage
				(originalBufferedImage.getWidth(), originalBufferedImage.getHeight(), originalBufferedImage.getType());
		
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.rotate(Math.toRadians(angle), 0, rotatedBufferedImage.getHeight());
	    AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_BILINEAR);
	    rotatedBufferedImage = affineTransformOp.filter(originalBufferedImage, null);
	    
		Matrix matrix2 = Matrix.bufferedImageToMatrix(rotatedBufferedImage, matrix.getType());
		return matrix2;
	}

	
////----resize----////
	
	
	public static Matrix resize(Matrix matrix, int newWidth, int newHeight){
		BufferedImage originalBufferedImage = Matrix.matrixToBufferedImage(matrix);
		BufferedImage resizedBufferedImage = new BufferedImage(newWidth, newHeight, originalBufferedImage.getType());

		Graphics2D g = resizedBufferedImage.createGraphics();
    	g.drawImage(originalBufferedImage, 0, 0, newWidth, newHeight, null); 
    	g.dispose();
    	
    	return Matrix.bufferedImageToMatrix(resizedBufferedImage, matrix.getType());
    }
	
	public Matrix resize(Matrix matrix, double percentage){
		return resize(matrix, percentage, percentage);
	}
	
	public Matrix resize(Matrix matrix, double widthPercentage, double heightPercentage){
		int newWidth = (int) (matrix.getCols()*widthPercentage),
		newHeight = (int) (matrix.getRows()*heightPercentage);
		
		return resize(matrix, newWidth, newHeight);
	}
}
