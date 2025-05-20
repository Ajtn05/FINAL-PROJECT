/** 
    This is the InteractableObjects interface, which is implemented by multiple classes.
    It has methods for getting images, checking collisions, drawing, and getter methods.

    @author Janelle Angela C. Lopez (242682)
    @author Aldrin Joseph T. Nellas (243215)
	@version April 1, 2025
	
	I have not discussed the Java language code in my program 
	with anyone other than my instructor or the teaching assistants 
	assigned to this course.

	I have not used Java language code obtained from another student, 
	or any other unauthorized source, either modified or unmodified.

	If any Java language code or documentation used in my program 
	was obtained from another source, such as a textbook or website, 
	that has been clearly noted with a proper citation in the comments 
	of my program.
**/


import java.awt.*;

public interface InteractableObjects {
    void getImage();
    void checkCollision(Player player);
    void draw(Graphics2D g);
    boolean isInteracted();
    int getX();
    int getY();
    String getID();
}