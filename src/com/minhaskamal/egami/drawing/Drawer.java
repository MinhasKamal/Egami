/***********************************************************
* Developer: Minhas Kamal (minhaskamal024@gmail.com)       *
* Date: 07-May-2016                                        *
* License: MIT License                                     *
***********************************************************/

package com.minhaskamal.egami.drawing;

import java.util.ArrayList;
import com.minhaskamal.egami.matrix.Matrix;
import com.minhaskamal.egami.matrix.MatrixUtilities;

public class Drawer {
	public static final int HORIZONTAL_LINE = 0;
	public static final int VERTICAL_LINE = 1;
	
	
	//point draw
	
	public static Matrix drawPoint(Matrix matrix, ArrayList<int[]> positions){
		return drawPoint(matrix, positions, 1, 1);
	}
	
	public static Matrix drawPoint(Matrix matrix, ArrayList<int[]> positions,
			int pointWidth, int pointHeight){
		return drawPoint(matrix, positions, pointWidth, pointHeight, MatrixUtilities.getBlackPixel(matrix));
	}
	
	public static Matrix drawPoint(Matrix matrix, ArrayList<int[]> positions, int pointWidth, int pointHeight,
			int[] pixel){
		
		--pointWidth;
		--pointHeight;
		
		int preWidth=pointWidth/2,
			postWidth=pointWidth/2+pointWidth%2,
			preHeight=pointHeight/2,
			postHeight=pointHeight/2+pointHeight%2;
		
		for(int[] position: positions){
			for(int i=-preHeight; i<=postHeight; i++){
				for(int j=-preWidth; j<=postWidth; j++){
					matrix.pixels[position[0]+i-1][position[1]+j-1] = pixel;
				}
			}
		}
		
		return matrix;
	}
	
	public static Matrix drawObject(Matrix matrix, ArrayList<ArrayList<int[]>> objects){
		System.out.println(objects.size());
		int i=0;
		for(ArrayList<int[]>object: objects){
			matrix = drawPoint(matrix, object);
			System.out.println(i++);
		}
		
		return matrix;
	}
	
	//line draw
	
	public static Matrix drawLine(Matrix matrix, int[] linePositions, int lineAlignment){
		if(lineAlignment==HORIZONTAL_LINE){
			return drawHorizaontalLine(matrix, linePositions);
		}else{
			return drawVerticalLine(matrix, linePositions);
		}
	}
	
	public static Matrix drawLine(Matrix matrix, int[] linePositions, int lineAlignment,
			int lineStartPosition, int lineStopPosition, int[] pixelColor){
		
		if(lineAlignment==0){	//horizontal
			return drawHorizaontalLine(matrix, linePositions, lineStartPosition, lineStopPosition,
					pixelColor);
		}else{
			return drawVerticalLine(matrix, linePositions, lineStartPosition, lineStopPosition,
					pixelColor);
		}
	}
	
	public static Matrix drawHorizaontalLine(Matrix matrix, int[] linePositions){
		return drawHorizaontalLine(matrix, linePositions, 0, matrix.pixels[0].length, MatrixUtilities.getBlackPixel(matrix));
	}
	
	public static Matrix drawVerticalLine(Matrix matrix, int[] linePositions){
		return drawVerticalLine(matrix, linePositions, 0, matrix.pixels.length, MatrixUtilities.getBlackPixel(matrix));
	}
	
	public static Matrix drawHorizaontalLine(Matrix matrix, int[] linePositions,
			int lineStartPosition, int lineStopPosition, int[] pixelColor){
		
		for(int i=0; i<linePositions.length; i++){
			for(int j=lineStartPosition; j<lineStopPosition; j++){
				matrix.pixels[linePositions[i]][j] = pixelColor;
			}
		}
		return matrix;
	}
	
	public static Matrix drawVerticalLine(Matrix matrix, int[] linePositions,
			int lineStartPosition, int lineStopPosition, int[] pixelColor){
		
		for(int i=0; i<linePositions.length; i++){
			for(int j=lineStartPosition; j<lineStopPosition; j++){
				matrix.pixels[j][linePositions[i]] = pixelColor;
			}
		}
		return matrix;
	}
	
	/**
	 * d = x*cosT + y*sinT (T- angle in degree)
	 */
	public static Matrix drawLine(Matrix matrix, int d, int T, int[] pixelColor){
		
		double m = 0;
		int c = 0;
		if(T!=0){
			m = -1 / Math.tan(Math.toRadians(T));
			c = (int) (d/Math.sin( Math.toRadians(T) ));
		}
		
		return drawLine(matrix, m, c, pixelColor);
	}
	
	/**
	 * y = m*x + c
	 */
	public static Matrix drawLine(Matrix matrix, double m, int c, int[] pixelColor){
		
		if(m>=-1 && m<=1){
			int y=0;
			for(int x=0; x<matrix.pixels[0].length; x++){
				y = (int) (m*x + c);
				
				if(y>=0 && y<matrix.pixels.length){
					matrix.pixels[y][x] = pixelColor;
				}
			}
		}else{
			int x=0;
			for(int y=0; y<matrix.pixels.length; y++){
				x = (int) ((y-c)/m);
				
				if(x>=0 && x<matrix.pixels.length){
					matrix.pixels[y][x] = pixelColor;
				}
			}
		}
		
		return matrix;
	}
	
	//grid draw
	public static Matrix drawGrid(Matrix matrix, int rowStart, int colStart, double rowLength, double colLength,
			double rowGap, double colGap, int numberOfRows, int numberOfCols){
		
		return drawGrid(matrix, rowStart, colStart, rowLength, colLength, rowGap, colGap, numberOfRows, numberOfCols,
				0, 0, MatrixUtilities.getBlackPixel(matrix));
	}
	
	public static Matrix drawGrid(Matrix matrix, int rowStart, int colStart, double rowLength, double colLength,
			double rowGap, double colGap, int numberOfRows, int numberOfCols, 
			double rowShift, double colShift, int[] pixelColor){
		
		for(int i=0; i<numberOfRows; i++){
			double rawRow = rowStart + (rowLength+rowGap)*i;
			
			for(int j=0; j<numberOfCols; j++){
				double rawCol = colStart + (colLength+colGap)*j;

				int row = (int) (rawRow + rawCol*colShift);
				int col = (int) (rawCol + rawRow*rowShift);
				
				drawGrid(matrix, row, col, rowLength, colLength, pixelColor);
			}
		}
		
		return matrix;
	}
	
	public static Matrix drawGrid(Matrix matrix, int row, int col, double rowLength, double colLength,
			int[] pixelColor){
		
		for(int k=1; k<colLength; k++){
			matrix.pixels[row][k+col] = pixelColor;
			matrix.pixels[(int) (row+rowLength)][k+col] = pixelColor;
		}
		for(int k=0; k<=rowLength; k++){
			matrix.pixels[k+row][col] = pixelColor;
			matrix.pixels[k+row][(int) (col+colLength)] = pixelColor;
		}
		
		return matrix;
	}
}
