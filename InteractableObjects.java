import java.awt.*;

public interface InteractableObjects {
    boolean isInteracted();
    void draw(Graphics2D g);
    int getX();
    int getY();
}