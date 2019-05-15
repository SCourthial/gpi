package ricm3.gpi.gui.widgets;

import ricm3.gpi.gui.Color;
import ricm3.gpi.gui.Graphics;
import ricm3.gpi.gui.KeyListener;
import ricm3.gpi.gui.MouseListener;
import ricm3.gpi.gui.layout.Component;
import ricm3.gpi.gui.layout.Container;
import ricm3.gpi.gui.layout.Location;

/**
 * A canvas to draw on.
 * 
 * The intended usage is to subclass this canvas, usually listening to mouse and
 * keyboard events
 * 
 * The default painting for a canvas is to fill the canvas with the background
 * color. Your subclass may override this behavior.
 * 
 * @author Pr. Olivier Gruber (olivier dot gruber at acm dot org)
 */

public class Canvas extends Component {
	
	int x1, y1, x2, y2;

	public Canvas(Container parent) {
		super(parent);
		x1 = 0;
		y1 = 0;
		x2 = 0;
		y2 = 0;
	}

	@Override
	public void paint(Graphics g) {
			System.out.println("non");
			g.setColor(m_bgColor);
			Location l = new Location(0, 0);
			this.toGlobal(l);
			g.fillRect(l.x(), l.y(), m_width, m_height);
			g.setColor(m_fgColor);
			g.drawLine(x1, y1, x2, y2);
			System.out.println("What?!!");
	}
	
	public static class CL implements MouseListener {
		boolean m_down;
		String m_msg;
		Canvas m_c;

		public CL(Canvas c,String msg) {
			m_c = c;
			m_msg = msg;
		}
	
		@Override
		public void mouseMoved(int x, int y) {
			m_c.x2 = x;
			m_c.y2 = y;
			if (m_down) {
				m_c.repaint();
			}
		}
	
		@Override
		public void mousePressed(int x, int y, int buttons) {
			m_down = true;
			m_c.x1 = x;
			m_c.y1 = y;
		}
	
		@Override
		public void mouseReleased(int x, int y, int buttons) {
			m_down = false;
		}
	
		@Override
		public void mouseEntered(int x, int y) {
			System.out.println("Canvas entered");
		}
	
		@Override
		public void mouseExited() {
			System.out.println("Canvas exited");
		}
	}
	
	public static class KL implements KeyListener {
		
		boolean m_down;
		String m_msg;
		Canvas m_c;

		public KL(Canvas c,String msg) {
			m_c = c;
			m_msg = msg;
		}

		@Override
		public void keyPressed(char k, int code) {
		}

		@Override
		public void keyReleased(char k, int code) {
			if (code == KeyListener.VK_C) {
				m_c.x1 = 0;
				m_c.y1 = 0;
				m_c.x2 = 0;
				m_c.y2 = 0;
				m_c.repaint();
			}
			if (code == KeyListener.VK_SPACE) {
				if (m_down) {
					m_c.repaint();
					m_down = false;
				}else {
					m_c.x1 = m_c.x2;
					m_c.y1 = m_c.y2;
					m_down = true;
				}
			}
		}
	}
}
