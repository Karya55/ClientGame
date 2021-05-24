package ball;

import java.awt.Color;
import java.io.Serial;
import java.io.Serializable;

public class BallDTO implements Serializable {
	float x, y;           
	float speedX, speedY; 
	float radius;         
	Color color;
	@Serial
	private static final long serialVersionUID = 1000;

	public BallDTO(float x, float y, float speedX, float speedY, float radius, Color color) {
		
		this.x = x;
		this.y = y;

		this.speedX = speedX;
		this.speedY = speedY;
		this.radius = radius;
		this.color = color;

	}
}
