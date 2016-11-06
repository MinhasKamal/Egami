package test;

import java.util.ArrayList;

import com.minhaskamal.egami.connectedComponentAnalysis.ConnectedComponentLabeler;
import com.minhaskamal.egami.drawing.Drawer;
import com.minhaskamal.egamiLight.Matrix;
import com.minhaskamal.egamiLight.MatrixUtilities;

public class ConnectedComponentLabelerTest extends EgamiTest{

	public static void main(String[] args) {
		new ConnectedComponentLabelerTest().runTest();
	}
	
	@Override
	public void testMethod() throws Exception {
		Matrix matrix = new Matrix(FOOD_IMG, Matrix.BLACK_WHITE);
		matrix.write(DESKTOP+"in.png");		

		ConnectedComponentLabeler connectedComponentLabeler= new ConnectedComponentLabeler(matrix);
		connectedComponentLabeler.extractPixelGroup(100, 5, 5, 50, 50);
		ArrayList<ArrayList<int[]>> pixelGroups = connectedComponentLabeler.getConnectedPixelGroups();
		   
		Matrix matrix2 = MatrixUtilities.createNewMatrix(matrix, Matrix.MAX_PIXEL);
		matrix2 = Drawer.drawObject(matrix2, pixelGroups);
		
		matrix2.write(DESKTOP+"lol.png");
	}

}
