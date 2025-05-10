import java.awt.*;

public interface InteractableObjects {
    void getImage();
    void checkCollision(Player player);
    boolean isInteracted();
    void draw(Graphics2D g);
    int getX();
    int getY();
    String getID();
}