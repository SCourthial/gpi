package edu.ricm3.game.sample;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Iterator;

/*Commun à toutes les entités du monde virtuel (joueur et ennemis)*/

public class Sprite {
	BufferedImage m_sprite;
	int m_nrows, m_ncols;
	int m_step;
	int m_idx;
	int m_nsteps;
	int m_w, m_h, w, h;
	int m_x, m_y;
	int xinit, yinit;
	int m_centerX, m_centerY;
	int m_dx, m_dy;
	int k;
	int descend;
	float m_scale;
	long m_lastMove, m_lastJump, m_lastGravite;
	long elapsedMove, elapsedJump, elapsedGravite;
	boolean m_canExplode;
	boolean m_explode;
	boolean movable;
	boolean jump;
	boolean enable_collision;
	BufferedImage[] m_sprites;
	Explosion m_explosion;
	Model m_model;
	
	Sprite(Model model, BufferedImage sprite, int rows, int columns, int x, int y, float scale, boolean enable_coll){
		m_model = model;
		m_sprite = sprite;
		m_ncols = columns;
		m_nrows = rows;
		m_lastMove = 0;
		m_x = x;
		m_y = y;
		xinit = m_x;
		yinit = m_y;
		m_dx = 0;
		m_dy = 0;
		m_scale = scale;
		k = 0;
		jump = false;
		enable_collision = enable_coll;
		splitSprite();
		w = (int)(scale * m_w);
		h = (int)(scale * m_h);
	}
	
	/*
	 * This is about splitting the animated sprite into the basic sub-images
	 * constituting the animation.
	 */
	void splitSprite() {
		int width = m_sprite.getWidth(null);
		int height = m_sprite.getHeight(null);
		m_sprites = new BufferedImage[m_nrows * m_ncols];
		m_w = width / m_ncols;
		m_h = height / m_nrows;
		m_step = 8;
		for (int i = 0; i < m_nrows; i++) {
			for (int j = 0; j < m_ncols; j++) {
				int x = j * m_w;
				int y = i * m_h;
				m_sprites[(i * m_ncols) + j] = m_sprite.getSubimage(x, y, m_w, m_h);
			}
		}
	}
	
	void setExplosion(BufferedImage sprite, int rows, int columns) {
		m_explosion = new Explosion(m_model, sprite, rows, columns);
	}
	
	/* Vérifie si il y a collision avec une entité du monde
	 * avec le Sprite s si il se déplace de x et y.
	 */
	boolean collision(int x, int y) {
		if(enable_collision) {
			boolean coll;
			Iterator<Square> itr = m_model.m_squares.iterator();
			while (itr.hasNext()) {
				coll = true;
				Square s = itr.next();
				if(s.enable_collision) {
					if(m_y + y > s.m_y + s.m_h || m_y + h + y < s.m_y) {	//collision
						coll = false;
					}else if(m_x + x > s.m_x + s.m_w || m_x + w + x < s.m_x) {
						coll = false;
					}
					if (coll == true)
						return true;
				}
			}
		}
		return false;
	}
	
	boolean collisionEnemy(int x, int y) {
		if(enable_collision) {
			boolean coll;
			Iterator<Sprite> itr = m_model.m_sprites.iterator();
			while (itr.hasNext()) {
				coll = true;
				Sprite sp = itr.next();
				if(m_y + y > sp.m_y + sp.h || m_y + h + y < sp.m_y) {	//collision
					coll = false;
				}else if(m_x + x > sp.m_x + sp.w || m_x + w + x < sp.m_x) {
					coll = false;
				}
				if (coll == true)
					return true;
			}
		}
		return false;
	}
	
	/*Fait bouger le personnage.*/
	void move(int x, int y) {
			if(movable) {
				if(collisionEnemy(x, y)) {
					m_canExplode = true;
				}else {
					if (!collision(x, 0)) {
						m_x += x;
					}
					if (!collision(0, y)) {
						m_y += y;
					}
				}
			}
	}
	
	/*Fait sauter le personnage.*/
	void jump() {
			if(movable && jump) {
				if (m_dy == 0) {
					m_dy = 1;
				}
				if (collision(0, m_dy)) {
					m_dy = -20;
					move(0, m_dy);
				} 
			}
	}
	
	/*Définit le fonctionnement de la gravité*/
	void gravite() {
			if (movable) {
				if (!collision(0, m_dy)) {
					if (m_dy < 20) {
						m_dy++;
					}
				}else {
					m_dy = 0;
				}
			}
	}
	
	/*Explosion de l'entité*/
	void explode(long now) {
		if (m_explode) {
			m_explosion.step(now); 
			movable = false;
			return; 
		}
		if (m_canExplode) { //explosion
			m_explode = true; 
			int w = (int)(m_scale * m_w);
			int h = (int)(m_scale * m_h);
			int x = m_x + w/2; int y = m_y + h/2;
			m_explosion.setPosition(x, y, 1F);
		}
	}
	
	void step(long now) {
		
	}
	
	/**
	 * paints this square on the screen.
	 * 
	 * @param g
	 */
	void paint(Graphics g) {
		if (m_explode) {
			m_explosion.paint(g);
			if (m_explosion.done()) {
				movable = true;
				m_explode = false;
				m_canExplode = false;
				m_x = xinit;
				m_y = yinit;
				m_dx = 0;
				m_dy = 0;
			}
		} else {
			Image img = m_sprites[m_idx];
			int w = (int) (m_scale * m_w);
			int h = (int) (m_scale * m_h);
			g.drawImage(img, 0, 0, w, h, null);
		}
	}
}
