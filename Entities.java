/** 
    This is the Entities class which just contains the data for classes that extend it,
    such as the characters (players and the king), traps, pressure plates, and key objects.
    It initializes fields for their position, size, movement direction for the player, 
    and the sprites.

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

import java.awt.image.*;

public class Entities {
    public int x, y, speed, width, height;
    public String character;
    public BufferedImage boyLeft1, boyRight1, boyUp1, boyDown1, 
                         boyLeft2, boyRight2, boyUp2, boyDown2, 
                         girlUp1, girlDown1, girlLeft1, girlRight1, 
                         girlUp2, girlDown2, girlLeft2, girlRight2;
    public BufferedImage boyUp, boyDown, boyLeft, boyRight, girlUp, 
                         girlDown, girlLeft, girlRight;
    public BufferedImage spikes, spike1, spike2, spike3, spike4, spike5, spike6,
                         fire, fire1, fire2, fire3, fire4, fire5, fire6, fire7, fire8, fire9;
    public BufferedImage goldKey, silverKey, bronzeKey;
    public BufferedImage kingSprite, king;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;

}