package edu.ricm3.game.sample;

public class Camera {
	int m_x, m_y;
	int m_centerX, m_centerY;
	Model m_model;
	long m_lastMove;
	
	Camera(Model m){
		m_model = m;
		m_x = m_model.m_cowboys.m_x;
		m_y = m_model.m_cowboys.m_y;
		m_centerX = 1024 / 2;
		m_centerY = 768 / 2;
	}
	
	void step(long now) {
		//long elapsed = now - m_lastMove;
		m_x = m_model.m_cowboys.m_x;
		m_y = m_model.m_cowboys.m_y;
	}
}
