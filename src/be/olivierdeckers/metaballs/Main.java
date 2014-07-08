package be.olivierdeckers.metaballs;
import java.util.Random;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;


public class Main implements ApplicationListener {
	public static final int NR_BALLS = 50;
	
	ShaderProgram shader;
	Mesh mesh;
	
	float[] balls;
	float[] ballVelocity;
	float[] ballColors;
	
	@Override
	public void create() {
		setRandomBalls();
		
		mesh = new Mesh(true, 4, 0, new VertexAttribute(Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE));
		mesh.setVertices(new float[] {
				-1, -1, 0,
				1, -1, 0,
				1, 1, 0, 
				-1, 1, 0
		});
		
		shader = new ShaderProgram(Gdx.files.internal("assets/vertex.shader"), Gdx.files.internal("assets/fragment.shader"));
		
		if(!shader.isCompiled()) {
			System.err.println(shader.getLog());
		}
		
	}

	private void setRandomBalls() {
		balls = new float[NR_BALLS*3];
		ballVelocity = new float[NR_BALLS*3];
		ballColors = new float[NR_BALLS*3];
		Random r = new Random();
		
		for(int i=0; i<NR_BALLS*3; i+=3) {
			balls[i] = r.nextFloat() * Gdx.graphics.getWidth();
			balls[i+1] = r.nextFloat() * Gdx.graphics.getHeight();
			balls[i+2] = r.nextFloat() * 6f+3;
			
			ballVelocity[i] = randomFloat(r, -100, 100);
			ballVelocity[i+1] = randomFloat(r, -100, 100);
			ballVelocity[i+2] = 0;
			
			ballColors[i] = r.nextFloat();
			ballColors[i+1] = r.nextFloat();
			ballColors[i+2] = r.nextFloat();
		}
	}
	
	private float randomFloat(Random r, float min, float max) {
		return r.nextFloat() * (max - min) + min;
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		for(int i=0; i<balls.length; i++) {
			balls[i] += ballVelocity[i] * Gdx.graphics.getDeltaTime();
			if(i % 3 != 2 && (balls[i] < 0 || balls[i] > getSize(i))) 
				ballVelocity[i] = -ballVelocity[i];
		}
		
		shader.begin();
		shader.setUniform3fv("u_balls", balls, 0, balls.length);
		shader.setUniform3fv("u_ball_colors", ballColors, 0, ballColors.length);
		mesh.render(shader, GL10.GL_TRIANGLE_FAN);
		shader.end();
	}

	private float getSize(int i) {
		switch(i % 3) {
		case 0:
			return Gdx.graphics.getWidth();
		case 1:
			return Gdx.graphics.getHeight();
		default:
			throw new IllegalArgumentException();
		}
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
