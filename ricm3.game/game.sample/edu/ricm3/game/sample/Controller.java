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

import java.awt.Button;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;

import edu.ricm3.game.GameController;

/**
 * This class is to illustrate the most simple game controller. It does not
 * much, but it shows how to obtain the key strokes, mouse buttons, and mouse
 * moves.
 * 
 * With ' ', you see what you should never do, SLEEP. With '+' and '-', you can
 * add or remove some simulated overheads.
 * 
 * @author Pr. Olivier Gruber
 */

public class Controller extends GameController implements ActionListener {

	Model m_model;
	View m_view;
	Button m_strobesOn;
	//Button m_plus, m_minus;
	Music m_player;
	boolean q_pressed, d_pressed;

	public Controller(Model model, View view) {
		m_model = model;
		m_view = view;
		q_pressed = false;
		d_pressed = false;
	}

	/**
	 * Simulation step. Warning: the model has already executed its step.
	 * 
	 * @param now is the current time in milliseconds.
	 */
	@Override
	public void step(long now) {
		m_model.step(now);
		m_view.step(now);
	}

	@Override
	public void keyTyped(KeyEvent e) {
//    if (Options.ECHO_KEYBOARD)
//      System.out.println("KeyTyped: " + e);
		if (e.getKeyChar() == 'o') {
			try {
				/*
				 * NEVER, EVER, DO THIS! NEVER LOOP FOR LONG, NEVER BLOCK, OR NEVER SLEEP, YOU
				 * WILL BLOCK EVERYTHING.
				 */
				System.err.println("You should not have done that!");
				System.out.println("ZZzzz....");
				Thread.sleep(3000);
				System.out.println("Hey! I am back");
			} catch (InterruptedException ex) {
			}
		} else if (e.getKeyChar() == '+') {
			Overhead h = m_model.getOverhead();
			h.inc();
		} else if (e.getKeyChar() == '-') {
			Overhead h = m_model.getOverhead();
			h.dec();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		char c = e.getKeyChar();
		int code = e.getKeyCode();
		if (Options.ECHO_KEYBOARD)
			System.out.println("KeyPressed: " + c + " code=" + code);
		switch (c) {
		case ' ':
			m_model.m_cowboys.jump = true;
			break;
		case 'q':
			if (!q_pressed)
				m_model.m_cowboys.m_dx += -8;
			q_pressed = true;
			break;
		case 'd':
			if (!d_pressed)
				m_model.m_cowboys.m_dx += 8;
			d_pressed = true;
			break;
		case 'r':
			m_model.m_cowboys.m_canExplode = true;
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		char c = e.getKeyChar();
		int code = e.getKeyCode();
		if (Options.ECHO_KEYBOARD)
			System.out.println("KeyReleased: " + c + " code=" + code);
		switch (c) {
		case ' ':
			m_model.m_cowboys.jump = false;
			break;
		case 'q':
			m_model.m_cowboys.m_dx -= -8;
			q_pressed = false;
			break;
		case 'd':
			m_model.m_cowboys.m_dx -= 8;
			d_pressed = false;
			break;
		}
		/*
		 * if (m_model.m_cowboys.m_dy != 0) m_model.m_cowboys.m_dy = 0;
		 */
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (Options.ECHO_MOUSE)
			System.out.println("MouseClicked: (" + e.getX() + "," + e.getY() + ") button=" + e.getButton());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (Options.ECHO_MOUSE)
			System.out.println("MousePressed: (" + e.getX() + "," + e.getY() + ") button=" + e.getButton());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (Options.ECHO_MOUSE)
			System.out.println("MouseReleased: (" + e.getX() + "," + e.getY() + ") button=" + e.getButton());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (Options.ECHO_MOUSE_MOTION)
			System.out.println("MouseEntered: (" + e.getX() + "," + e.getY());
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (Options.ECHO_MOUSE_MOTION)
			System.out.println("MouseExited: (" + e.getX() + "," + e.getY());
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (Options.ECHO_MOUSE_MOTION)
			System.out.println("MouseDragged: (" + e.getX() + "," + e.getY());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (Options.ECHO_MOUSE_MOTION)
			System.out.println("MouseMoved: (" + e.getX() + "," + e.getY());
	}

	public void notifyVisible() {
		Container cont = new Container();
		cont.setLayout(new FlowLayout());
		
		m_strobesOn = new Button("Strobes");
		m_strobesOn.addActionListener(this);
		cont.add(m_strobesOn);
		
		/*
		 * m_plus = new Button("+"); m_plus.addActionListener(this); cont.add(m_plus);
		 * 
		 * m_minus = new Button("-"); m_minus.addActionListener(this);
		 * cont.add(m_minus);
		 */

		File file;
		file = new File("game.sample/sprites/Future-RPG.wav");

		try {
			m_player = new Music(file);
			cont.add(m_player.getControls());
		} catch (Exception ex) {
		}
		m_game.addSouth(cont);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object s = e.getSource();
		if (s == m_strobesOn)
			Options.STROBBING_SQUARES = !Options.STROBBING_SQUARES;
		/*
		 * else if (s == m_plus && Options.SHOW_NCOWBOYS < Options.MAX_NCOWBOYS)
		 * Options.SHOW_NCOWBOYS++; else if (s == m_minus && Options.SHOW_NCOWBOYS > 0)
		 * Options.SHOW_NCOWBOYS--;
		 */
	}

}
