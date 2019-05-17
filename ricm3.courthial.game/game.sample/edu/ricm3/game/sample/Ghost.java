package edu.ricm3.game.sample;

import java.awt.image.BufferedImage;

public class Ghost extends Sprite {
	long m_lastMove;

	Ghost(Model model, BufferedImage sprite, int rows, int columns, int x, int y, float scale, boolean enable_coll) {
		super(model, sprite, rows, columns, x, y, scale, enable_coll);
		m_scale = scale;
		movable = true;
		enable_collision = true;
		splitSprite();
	}
	
	@Override
	void step(long now) {
		long elapsed = now - m_lastMove;
		if (elapsed > 10L) {
			m_lastMove = now;
			
			// gravité
			gravite();

			// déplacement
			m_dx = 1;
			if(collision(m_dx, 0))
				jump();
			move(m_dx, m_dy);

			// explosion
			explode(now);
		}
	}

}
