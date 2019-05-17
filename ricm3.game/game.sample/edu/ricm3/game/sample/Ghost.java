package edu.ricm3.game.sample;

import java.awt.image.BufferedImage;

public class Ghost extends Sprite {
	long m_lastMove;

	Ghost(Model model, BufferedImage sprite, int rows, int columns, int x, int y, float scale, boolean enable_coll) {
		super(model, sprite, rows, columns, x, y, scale, enable_coll);
		m_scale = scale;
		movable = true;
		enable_collision = false;
		splitSprite();
	}
	
	@Override
	void step(long now) {

			// gravité
			//gravite();

			// déplacement
			move(m_dx, m_dy);

			// explosion
			explode(now);
	}

}
