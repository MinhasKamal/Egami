/***********************************************************
* Developer: Minhas Kamal (minhaskamal024@gmail.com)       *
* Date: 01-Apr-2016                                        *
* License: MIT License                                     *
***********************************************************/

package com.minhaskamal.egami.connectedComponentAnalysis;

import java.util.ArrayList;
import java.util.LinkedList;

import com.minhaskamal.egamiLight.Matrix;
import com.minhaskamal.egamiLight.MatrixEditor;
import com.minhaskamal.egamiLight.MatrixTypeConverter;

public class ConnectedComponentLabeler {

	private Matrix matrix;
	private ArrayList<ArrayList<int[]>> connectedPixelGroups;
	
	public ConnectedComponentLabeler(Matrix matrix) {
		this.matrix = MatrixTypeConverter.convert(matrix, Matrix.BLACK_WHITE);
		this.connectedPixelGroups = new ArrayList<>();
	}
	
	
	public void extractPixelGroup(int threshold, int minWidth, int minHeight, int maxWidth, int maxHeight){
		extractPixelGroup(threshold, minWidth, minHeight, maxWidth, maxHeight,
				Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	
	public void extractPixelGroup(int threshold,
			int minWidth, int minHeight, int maxWidth, int maxHeight,
			int minNumberOfPixels, int maxNumberOfPixels, int minPixelPercentage, int maxPixelPercentage){
		
		int rows = this.matrix.getRows(),
			cols = this.matrix.getCols();
		
		
		Matrix borderedMatrix = MatrixEditor.createBorder(this.matrix, 1, Matrix.MAX_PIXEL);
		
		for(int i=0; i<rows; i++){
			for(int j=0; j<cols; j++){

				if(borderedMatrix.pixels[i][j][0] < threshold){
					ArrayList<int[]> connectedDots = getConnectedPixelGroup(borderedMatrix, threshold, i, j);
					int[] minXMaxXMinYMaxY = getMinXMaxXMinYMaxY(connectedDots);
					
					int width = minXMaxXMinYMaxY[1]-minXMaxXMinYMaxY[0]+1,
						height = minXMaxXMinYMaxY[3]-minXMaxXMinYMaxY[2]+1,
						numberOfPixels = connectedDots.size(),
						pixelPercentage = numberOfPixels*100/(width*height);
					
					if(width>minWidth && width<maxWidth && height>minHeight && height<maxHeight &&
							numberOfPixels>minNumberOfPixels && numberOfPixels<maxNumberOfPixels &&
							pixelPercentage>minPixelPercentage && pixelPercentage<maxPixelPercentage){
						
						this.connectedPixelGroups.add(connectedDots);
					}
				}
				
			}
		}
		
		return ;
	}
	
	private ArrayList<int[]> getConnectedPixelGroup(Matrix matrix, int threshold, int i, int j){
		ArrayList<int[]> blackDots = new ArrayList<>();
		LinkedList<int[]> blackDotStack = new LinkedList<>();

		blackDotStack.addLast(new int[]{i, j});
		matrix.pixels[i][j][0] = Matrix.MAX_PIXEL;
		while(!blackDotStack.isEmpty()){
			int[] blackDot = blackDotStack.pop();
			blackDots.add(blackDot);

			for(int k=-1; k<2; k++){
				for(int l=-1; l<2; l++){

					if(matrix.pixels[blackDot[0]+k][blackDot[1]+l][0] < threshold){
						blackDotStack.addLast( new int[]{blackDot[0]+k, blackDot[1]+l} );
						matrix.pixels[blackDot[0]+k][blackDot[1]+l][0] = Matrix.MAX_PIXEL;
					}
				}
			}
		}

		return blackDots;
	}
	
	/**
	 * Calculates boundary of connected dots.
	 * @param connectedDots
	 * @return
	 */
	private int[] getMinXMaxXMinYMaxY(ArrayList<int[]> connectedDots){
		int minX=connectedDots.get(0)[0],
			maxX=connectedDots.get(0)[0],
			minY=connectedDots.get(0)[1],
			maxY=connectedDots.get(0)[1];
		
		for(int i=1; i<connectedDots.size(); i++){
			if(connectedDots.get(i)[0] < minX){
				minX = connectedDots.get(i)[0];
			}else if(connectedDots.get(i)[0] > maxX){
				maxX = connectedDots.get(i)[0];
			}

			if(connectedDots.get(i)[1] < minY){
				minY = connectedDots.get(i)[1];
			}else if(connectedDots.get(i)[1] > maxY){
				maxY = connectedDots.get(i)[1];
			}
		}
		
		return new int[]{minX, maxX, minY, maxY};
	}
	
	/////////////////////////////////////////////////////////////////////////////////////
	
	public ArrayList<ArrayList<int[]>> getConnectedPixelGroups(){
		return this.connectedPixelGroups;
	}
	
	public void setConnectedPixelGroups(ArrayList<ArrayList<int[]>> connectedPixelGroups){
		this.connectedPixelGroups = connectedPixelGroups;
	}
	
	public ArrayList<int[]> getConnectedPixelGroupPositions(){
		ArrayList<int[]> connectedPixelGroupPositions = new ArrayList<>();
		
		for(int i=0; i<this.connectedPixelGroups.size(); i++){
			connectedPixelGroupPositions.add(this.connectedPixelGroups.get(i).get(0));
		}
		
		return connectedPixelGroupPositions;
	}
	
	public Matrix getFilteredMatrix(){
		int rows = this.matrix.getRows(),
			cols = this.matrix.getCols();
		
		Matrix filteredMatrix = new Matrix(rows, cols, Matrix.BLACK_WHITE);
		for(int i=0; i<rows; i++){
			for(int j=0; j<cols; j++){
				filteredMatrix.pixels[i][j][0] = Matrix.MAX_PIXEL;
			}
		}
		
		for(int i=0; i<this.connectedPixelGroups.size(); i++){
			drawPixelGroup(filteredMatrix, this.connectedPixelGroups.get(i), Matrix.MIN_PIXEL);
		}
		
		return filteredMatrix;
	}
	
	private void drawPixelGroup(Matrix matrix, ArrayList<int[]> connectedDots, int pixel){
		for(int[] connectedDot: connectedDots){
			matrix.pixels[connectedDot[0]-1][connectedDot[1]-1][0] = pixel;
		}
	}
	
}
