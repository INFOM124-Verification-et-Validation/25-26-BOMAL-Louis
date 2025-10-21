package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlayerCollisionsTest {
    private PacManSprites sprites = new PacManSprites();
    private GhostFactory ghostFactory = new GhostFactory(sprites);
    private PlayerFactory playerFactory = new PlayerFactory(sprites);
    private PlayerCollisions collisions = new PlayerCollisions();
    @Test
    public void testPlayerGhostCollisions() {

        Ghost inky = ghostFactory.createInky();
        Player pacman = playerFactory.createPacMan();

        collisions.collide(pacman, inky);
        assertTrue(!pacman.isAlive());
    }

    @Test
    public void testPlayerPelletCollisions() {
        Player pacman = playerFactory.createPacMan();
        Pellet pel = new Pellet(3, sprites.getPelletSprite());

        int previous_score = pacman.getScore();
        collisions.collide(pel, pacman);
        assertEquals(previous_score+3, pacman.getScore());
    }

    @Test
    public void testGhostPelletCollisions() {
        Ghost inky = ghostFactory.createInky();
        Pellet pel = new Pellet(3, sprites.getPelletSprite());
        collisions.collide(pel, inky);
        assertTrue(!pel.hasSquare());


    }

}
