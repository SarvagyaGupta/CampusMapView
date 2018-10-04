package hw9;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import hw5.DirectedEdge;
import hw8.CampusPaths;
import hw8.MyPoint;

/**
 * The CampusGraphicsView is the View of the GUI
 * 
 * @author Sarvagya Gupta
 */
public class CampusGraphicsView extends JPanel {
	
	private static final long serialVersionUID = 1L;
	/** The constant that specifies the path of campus image file */
	private static final String MAP_PATH = "src/hw8/data/campus_map.jpg";
	
	/** The campus to be showed */
	private CampusPaths campus;
	
	/** The image of the campus */
	private BufferedImage campusImage;
	
	/** The path from one building to another */
	private List<DirectedEdge<Double, MyPoint>> path;
	
	/** The dimension of the campus image */
	private static final Dimension SIZE = new Dimension(1025 * 2, 750 * 2);
	
	/**
	 * Generates the view of the GUI
	 * 
	 * @param campus The campus to be shown
	 */
	public CampusGraphicsView(CampusPaths campus) {
		this.campus = campus;
		
		try {
			campusImage = ImageIO.read(new File(MAP_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Resets the view of the GUI
	 */
	public void clear() {
		path = null;
		repaint();
	}

	/**
	 * Asks the model to search for the path between start and end
	 * 
	 * @param start The current location
	 * @param end The destination location
	 */
	public void dijkstraSearch(String start, String end) {
		path = campus.dijkstraSearch(start, end);
		repaint();
	}

	/**
	 * Repaints the GUI to display the accurate data to the user
	 * 
	 * @param g The Graphics used to draw on the GUI
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D pen = (Graphics2D) g;
		
		// Draw the campus map image
		pen.drawImage(campusImage, 0, 0, SIZE.width, SIZE.height, 0, 0, 
				campusImage.getWidth(), campusImage.getHeight(), null);
		
		if (path != null && path.size() > 0) {
			// Get the start and end position of the path
			DirectedEdge<Double, MyPoint> startPosition = path.get(0);
			DirectedEdge<Double, MyPoint> endPosition = path.get(path.size() - 1);
			
			// Change the stroke of the pen to a bigger size
			pen.setStroke(new BasicStroke(5));
			
			// Draw the path
			MyPoint prev = translate(startPosition.getDestinationNode());
			for (DirectedEdge<Double, MyPoint> edge: path) {
				MyPoint current = translate(edge.getDestinationNode());
				pen.drawLine(prev.getX().intValue(), prev.getY().intValue(), 
						current.getX().intValue(), current.getY().intValue());
				prev = current;
			}
			
			// Draw ovals to indicate start and end position
			MyPoint startTranslate = translate(startPosition.getDestinationNode());
			MyPoint endTranslate = translate(endPosition.getDestinationNode());
			
			pen.setColor(Color.BLUE);
			pen.drawOval(startTranslate.getX().intValue() - 10, 
					startTranslate.getY().intValue() - 10, 40, 40);
			
			pen.setColor(Color.YELLOW);
			pen.drawOval(endTranslate.getX().intValue() - 10,
					endTranslate.getY().intValue() - 10, 40, 40);
		}
	}

	/**
	 * Translates the MyPoint to the corresponding MyPoint in the GUI
	 * 
	 * @param node The MyPoint to be translated
	 * @return A MyPoint, p, that has the point ready to be drawn in the GUI
	 */
	private MyPoint translate(MyPoint node) {
		return new MyPoint(node.getX() * SIZE.width / campusImage.getWidth(),
				node.getY() * SIZE.height / campusImage.getHeight());
	}
	
	
}
