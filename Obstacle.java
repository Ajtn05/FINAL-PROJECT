
public interface Obstacle {
    void checkCollision(Player player, Map map, GameCanvas gc);
    String getID();
}