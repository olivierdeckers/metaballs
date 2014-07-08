package be.olivierdeckers.metaballs;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class Launcher {
	public static void main(String[] args) {
		new LwjglApplication(new Main(), "Metaballs", 800, 600, true);
	}
}