/***********************************************************
* Developer: Minhas Kamal (minhaskamal024@gmail.com)       *
* Date: 18-Apr-2016                                        *
* License: MIT License                                     *
***********************************************************/

package com.minhaskamal.egami.utilityAlgorithms;

public class GlobalThresholdCalculator {

	public static int getOtsuThreshold(int[] values){
		
		int sumValue = 0,
			sumValueThreshold = 0;
		for(int i=0; i<values.length; i++){
			sumValue += values[i];
			sumValueThreshold += i*values[i];
		}
		
		int weightB = 0,
			weightF = 0;
		int sumB = 0,
			sumF = 0;
		double meanB = 0,
			meanF = 0;
		double betweenClassVariance = 0;
		
		double maxBetweenClassVariance = -999;
		int threshold = -999;
		for(int i=0; i<values.length; i++){
			weightB += values[i];
			weightF = sumValue - weightB;
			
			if(weightB==0) continue;
			if(weightF==0) break;
			
			sumB += i*values[i];
			sumF = sumValueThreshold-sumB;
			
			meanB = (double) sumB/weightB;
			meanF = (double) sumF/weightF;
			
			betweenClassVariance = weightB * weightF * (meanB-meanF)*(meanB-meanF);
			
			if(betweenClassVariance>maxBetweenClassVariance){
				maxBetweenClassVariance = betweenClassVariance;
				threshold = i;
			}
		}
		
		return threshold;
	}
	
	public static int getKnownDistributionThreshold(int[] values, int percentage){
		
		int threshold = -999;
		
		int sumValue = 0;
		for(int i=0; i<values.length; i++){
			sumValue += values[i];
		}
		
		int sumB = 0;
		for(int i=0; i<values.length; i++){
			sumB += values[i];
			
			if((sumB*100/sumValue) > percentage){
				threshold = i;
				break;
			}
		}
		
		return threshold;
	}
}
