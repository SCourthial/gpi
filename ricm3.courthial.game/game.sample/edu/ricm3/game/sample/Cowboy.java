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

import java.awt.image.BufferedImage;

/**
 * This class is to illustrate a simple animated character. It does not much,
 * but it shows how to load an animation sprite and how to cycle through the
 * entire animation.
 * 
 * Pay attention to the finite state automaton, that is, the entire behavior
 * behaves as an automaton that progress a step at a time. Look at the method
 * step(long).
 * 
 * About the behavior of the cowboy. The cowboy can rotate around, that is the
 * animation. But since using arms may be dangerous, his gun may explode.
 * 
 * @author Pr. Olivier Gruber
 */

public class Cowboy extends Sprite {
	long m_lastMove;

	Cowboy(Model model, BufferedImage sprite, int rows, int columns, int x, int y, float scale, boolean enable_coll) {
		super(model, sprite, rows, columns, x, y, scale, enable_coll);
		xinit = m_x;
		yinit = m_y;
		movable = true;
		splitSprite();
	}

	@Override
	void move(int x, int y) {
		if (movable) {
			if (collisionEnemy(x, y)) {
				m_canExplode = true;
			} else {
				if (!collision(x, 0)) {
					if (m_dx < 1) {
						m_idx = 7 + (k % 3);
						if (elapsedMove > 100L && descend == 0) {
							k++;
						}
					} else {
						m_idx = 18 + (k % 4);
						if (elapsedMove > 100L && descend == 0) {
							k++;
						}
					}
					m_x += x;
				}
				if (!collision(0, y)) {
					m_y += y;
				}
			}
		}
	}

	/**
	 * Simulation step. This is essentially a finite state automaton. Here, you
	 * decide what happens as time flies.
	 * 
	 * @param now is the current time in milliseconds.
	 */
	@Override
	void step(long now) {
		long elapsed = now - m_lastMove;
		if (elapsed > 10L) {
			m_lastMove = now;
				
			// gravité
			gravite();

			if (jump)
				jump();

			// déplacement
			move(m_dx, m_dy);

			// explosion
			explode(now);
		}
	}

}
