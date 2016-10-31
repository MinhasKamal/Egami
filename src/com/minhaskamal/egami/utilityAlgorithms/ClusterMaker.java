/***********************************************************
* Developer: Minhas Kamal (minhaskamal024@gmail.com)       *
* Date: Sep-2016                                           *
* License: MIT License                                     *
***********************************************************/

package com.minhaskamal.egami.utilityAlgorithms;

import java.util.ArrayList;
import java.util.Random;

public class ClusterMaker {
	
	/////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static int[][] kMeansClustering(int[][] dataPoints, int k){
		int[][] clusterMeans = new int[k][dataPoints[0].length];
		Random random = new Random();
		for(int i=0; i<k; i++){
			clusterMeans[i] = dataPoints[random.nextInt(dataPoints.length-1)].clone();
		}
		
		int[][] newClusterMeans;		
		do{
			int[] pointInClusters = assignPointToCluster(dataPoints, clusterMeans);
			newClusterMeans = calculateClusterMeans(dataPoints, pointInClusters, k);
		}while(change(clusterMeans, newClusterMeans));
		
		return clusterMeans;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static int[][] meanShiftClustering(int[][] dataPoints, int radious, int n){
		int[][] clusterMeans = new int[n][dataPoints[0].length];
		Random random = new Random();
		for(int i=0; i<n; i++){
			clusterMeans[i] = dataPoints[random.nextInt(dataPoints.length-1)].clone();
		}
		
		int[][] newClusterMeans;
		do{
			double[][] distaces = calculateAllDistances(dataPoints, clusterMeans);
			newClusterMeans = calculateClusterMeans(dataPoints, distaces, radious);
		}while(change(clusterMeans, newClusterMeans));
		
		return cleanDuplicacy(clusterMeans);
	}
	
	private static int[][] cleanDuplicacy(int[][] clusterMeans){
		ArrayList<int[]> cleanClusterMeans = new ArrayList<int[]>();
		cleanClusterMeans.add(clusterMeans[0]);
		
		boolean arrayMatched;
		for(int i=1; i<clusterMeans.length; i++){
			arrayMatched = false;
			
			for(int[] clusterMean: cleanClusterMeans){
				boolean matched=true;
				for(int p=0; p<clusterMean.length; p++){
					if(clusterMean[p]!=clusterMeans[i][p]){
						matched = false;
						break;
					}
				}
				if(matched){
					arrayMatched = true;
					break;
				}
			}
			
			if(!arrayMatched){
				cleanClusterMeans.add(clusterMeans[i]);
			}
		}
		
		return cleanClusterMeans.toArray(new int[][]{{}});
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static int[] assignPointToCluster(int[][] dataPoints, int[][] clusterMeans){
		double[][] distaces = calculateAllDistances(dataPoints, clusterMeans);
		int[] pointInCluster = findPointInCluster(distaces);
		
		return pointInCluster;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////
	
	private static double[][] calculateAllDistances(int[][] dataPoints, int[][] clusterMeans){
		double[][] distaces = new double[dataPoints.length][clusterMeans.length];
		
		for(int i=0; i<dataPoints.length; i++){
			for(int j=0; j<clusterMeans.length; j++){
				distaces[i][j] = calculateDistance(dataPoints[i], clusterMeans[j]);
			}
		}
		
		return distaces;
	}
	
	private static double calculateDistance(int[] point1, int[] point2){
		double distance = 0;
		
		for(int i=0; i<point1.length; i++){
			distance += Math.pow(point1[i]-point2[i], 2);
		}
		distance = Math.sqrt(distance);
		
		return distance;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	private static int[] findPointInCluster(double[][] distaces){
		int[] pointInCluster = new int[distaces.length];
		
		for(int i=0; i<pointInCluster.length; i++){
			pointInCluster[i] = findIndexOfMinValue(distaces[i]);
		}
		
		return pointInCluster;
	}
	
	private static int findIndexOfMinValue(double[] values){
		int minIndex = 0;
		double minValue = values[minIndex];
		
		for(int i=1; i<values.length; i++){
			if(minValue>values[i]){
				minIndex=i;
				minValue=values[minIndex];
			}
		}
		
		return minIndex;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static int[][] calculateClusterMeans(int[][] dataPoints, int[] pointInClusters, int k){
		int[][] clusterMeans = new int[k][dataPoints[0].length];
		
		double [][] clusterSum = new double[clusterMeans.length][clusterMeans[0].length];
		int[] clusterMembers = new int[clusterMeans.length];
		for(int i=0; i<dataPoints.length; i++){
			for(int j=0; j<dataPoints[0].length; j++){
				clusterSum[pointInClusters[i]][j] += dataPoints[i][j];
			}
			clusterMembers[pointInClusters[i]] ++;
		}
		
		for(int i=0; i<clusterMeans.length; i++){
			for(int j=0; j<clusterMeans[0].length; j++){
				clusterMeans[i][j] = (int) (clusterSum[i][j]/clusterMembers[i]);
			}
		}
		
		return clusterMeans;
	}
	
	private static int[][] calculateClusterMeans(int[][] dataPoints, double[][] distaces, int radious){
		int[][] clusterMeans = new int[distaces[0].length][dataPoints[0].length];
		
		double [][] clusterSum = new double[clusterMeans.length][clusterMeans[0].length];
		int[] clusterMembers = new int[clusterMeans.length];
		
		for(int i=0; i<clusterMeans.length; i++){
			for(int j=0; j<distaces.length; j++){
				if(distaces[j][i]<=radious){
					for(int k=0; k<dataPoints[0].length; k++){
						clusterSum[i][k] += dataPoints[j][k];
					}
					clusterMembers[i] ++;
				}
			}
		}
		
		for(int i=0; i<clusterMeans.length; i++){
			for(int j=0; j<clusterMeans[0].length; j++){
				clusterMeans[i][j] = (int) (clusterSum[i][j]/clusterMembers[i]);
			}
		}
		
		return clusterMeans;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static boolean change(int[][] clusterMeans, int[][] newClusterMeans){
		boolean isChanged = false;
		
		for(int i=0; i<clusterMeans.length; i++){
			for(int j=0; j<clusterMeans[0].length; j++){
				if(clusterMeans[i][j] != newClusterMeans[i][j]){
					clusterMeans[i][j] = newClusterMeans[i][j];
					isChanged = true;
				}
			}
		}
		
		return isChanged;
	}

}
