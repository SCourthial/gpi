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
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;

import edu.ricm3.game.GameModel;

public class Model extends GameModel {
	LinkedList<Square> m_squares;
	BufferedImage m_cowboySprite;
	BufferedImage m_explosionSprite;
	Cowboy m_cowboys;
	LinkedList<Sprite> m_sprites;
	Random rand = new Random();
	Overhead m_overhead = new Overhead();
	Camera m_camera;

	public Model() {
		loadSprites();
		int i;

		m_squares = new LinkedList<Square>();
		m_squares.add(new Square(this, 0, 0, 30, 900, 0, 200 + rand.nextInt(55),
					70 + rand.nextInt(20), true));
		m_squares.add(new Square(this, 20 * 200, 0, 30, 900, 0, 200 + rand.nextInt(55),
				70 + rand.nextInt(20), true));
		for (i = 0; i < Options.NSQUARES; i++)
			m_squares.add(new Square(this, i * 200, 500 + rand.nextInt(100), 200, 200, 0, 200 + rand.nextInt(55),
					70 + rand.nextInt(20), true));
		m_squares.add(new Square(this, 400, 300, 200, 20, 150, 120, 30, true));

		m_sprites = new LinkedList<Sprite>();
		for (i = 0; i < Options.NGHOSTS; i++) {
			Sprite sp = new Ghost(this, m_cowboySprite, 4, 6, 700, 200, 3F, true);
			sp.setExplosion(m_explosionSprite, 11, 10);
			m_sprites.add(sp);
		}

		m_cowboys = new Cowboy(this, m_cowboySprite, 4, 6, 200, 200, 3F, true);
		m_cowboys.setExplosion(m_explosionSprite, 11, 10);
		
		m_camera = new Camera(this);
		
	}

	@Override
	public void shutdown() {

	}

	public Overhead getOverhead() {
		return m_overhead;
	}

	public Cowboy cowboys() {
		return m_cowboys;
	}

	public Iterator<Square> squares() {
		return m_squares.iterator();
	}

	public Iterator<Sprite> sprites() {
		return m_sprites.iterator();
	}

	/**
	 * Simulation step.
	 * 
	 * @param now is the current time in milliseconds.
	 */
	@Override
	public void step(long now) {

		m_overhead.overhead();

		// action des carrés
		Iterator<Square> iter = m_squares.iterator();
		while (iter.hasNext()) {
			Square s = iter.next();
			s.step(now);
		}

		// action des fantômes
		Iterator<Sprite> itr = m_sprites.iterator();
		while (itr.hasNext()) {
			Sprite g = itr.next();
			g.step(now);
		}

		// action du cowboy
		m_cowboys.step(now);
		
		m_camera.step(now);
		
	}

	private void loadSprites() {
		/*
		 * Cowboy with rifle, western style; png; 48x48 px sprite size Krasi Wasilev (
		 * http://freegameassets.blogspot.com)
		 */
		File imageFile = new File("game.sample/sprites/winchester.png");
		try {
			m_cowboySprite = ImageIO.read(imageFile);
		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(-1);
		}
		/*
		 * Long explosion set; png file; 64x64 px sprite size Krasi Wasilev (
		 * http://freegameassets.blogspot.com)
		 */
		imageFile = new File("game.sample/sprites/explosion01_set_64.png");
		try {
			m_explosionSprite = ImageIO.read(imageFile);
		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(-1);
		}
	}

}
