/*
 * Educational software for a basic game development
 * Copyright (C) 2018  Pr. Olivier Gruber
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.ricm3.game.sample;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

public class Square extends Component {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Model m_model;
	Color m_color;
	int m_w, m_h;
	int m_x, m_y;
	int m_red, m_green, m_blue;
	long m_lastResize;
	long m_lastMove;
	int gap;
	boolean enable_collision;

	Square(Model m, int x, int y, int width, int height, int r, int g, int b, boolean enable_coll) {
		m_model = m;
		m_x = x;
		m_y = y;
		m_w = width;
		m_h = height;
		m_red = r;
		m_green = g;
		m_blue = b;
		enable_collision = enable_coll;
		m_color = new Color(m_red, m_green, m_blue);
	}

	/**
	 * Simulation step.
	 * 
	 * @param now is the current time in milliseconds.
	 */
	void step(long now) {
	}

	void changeColor(int dc) {
		m_red = (m_red + dc) % 255;
		m_green = (m_green + dc) % 255;
		m_blue = (m_blue + dc) % 255;
		m_color = new Color(m_red, m_green, m_blue);
	}

	/**
	 * paints this square on the screen.
	 * 
	 * @param g
	 */
	@Override
	public void paint(Graphics g) {
		g.setColor(m_color);
		g.drawRect(0, 0, m_w, m_h);
		g.fillRect(0, 0, m_w, m_h);

		if (Options.STROBBING_SQUARES) {
			gap--;
			if (gap <= 0)
				gap = 20;

			int h = m_h;
			int w = m_w;
			g.setColor(Color.blue);
			g.fillRect(0, 0, w, h);

			g.setColor(Color.red);
			for (int i = 0; i < w; i += gap)
				g.drawLine(0 + i, 0, 0 + w - i, 0 + h);
			for (int i = 0; i < h; i += gap)
				g.drawLine(0, 0 + i, 0 + w, 0 + h - i);
		}
	}

}
