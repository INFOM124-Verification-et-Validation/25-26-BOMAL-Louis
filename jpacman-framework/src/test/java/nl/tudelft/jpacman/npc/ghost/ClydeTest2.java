package nl.tudelft.jpacman.npc.ghost;


import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.*;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ClydeTest2 {
    //beforeeach seulement si on doit reset les objets entre différents tests, ici on doit pas donc pas nécessaire
    private PacManSprites sprites = new PacManSprites();

    private PlayerFactory playerFactory = new PlayerFactory(sprites);
    private GhostFactory ghostFactory = new GhostFactory(sprites);
    private LevelFactory levelFactory = new LevelFactory(sprites, ghostFactory);
    private BoardFactory boardFactory = new BoardFactory(sprites);

    MapParser ghostMapParser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);
    @Test
    public void distanceGreaterThan8AndPathIsBlockedTest() {


        List<String> map = Arrays.asList(
            "##########",
            "#C#     P#",
            "##########"

        );

        Level level = ghostMapParser.parseMap(map);
        Player pacman = playerFactory.createPacMan();
        level.registerPlayer(pacman);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        Assert.assertNotNull(clyde);
        Optional<Direction> direction = clyde.nextAiMove();
        //assert direction.equals(Optional.of(Direction.EAST));
        assertEquals(Optional.empty(), direction);

    }


    @Test
    public void distanceGreaterThan8MultiplePathsTest() {


        List<String> map = Arrays.asList(
            "#   ######",
            "#C#     P#",
            "#   ######"

        );

        Level level = ghostMapParser.parseMap(map);
        Player pacman = playerFactory.createPacMan();
        level.registerPlayer(pacman);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        Assert.assertNotNull(clyde);
        Optional<Direction> direction = clyde.nextAiMove();

        //assert direction.equals(Optional.of(Direction.EAST));
        assertTrue(direction.isPresent());
        Direction directionvalue = direction.get();
        assertTrue(directionvalue == Direction.SOUTH|| directionvalue == Direction.NORTH);

    }

    @Test
    public void distanceLesserThan8MultiplePathsTest() {
        List<String> map = Arrays.asList(
            "#   ###",
            "#C#  P#",
            "#   ###"

        );

        Level level = ghostMapParser.parseMap(map);
        Player pacman = playerFactory.createPacMan();
        level.registerPlayer(pacman);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        Assert.assertNotNull(clyde);
        Optional<Direction> direction = clyde.nextAiMove();

        //assert direction.equals(Optional.of(Direction.EAST));
        assertTrue(direction.isPresent());
        Direction directionvalue = direction.get();
        assertTrue(directionvalue == Direction.SOUTH|| directionvalue == Direction.NORTH);

    }


    //this test crashes, the code probably contains a bug in the Clyde class regarding the OPPOSITES logic
    @Test
    public void distanceLesserThan8SinglePathTest() {
        List<String> map = Arrays.asList(
            "#######",
            " C#  P#",
            "#   ###"

        );

        Level level = ghostMapParser.parseMap(map);
        Player pacman = playerFactory.createPacMan();
        level.registerPlayer(pacman);

        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        Assert.assertNotNull(clyde);
        Optional<Direction> direction = clyde.nextAiMove();


        assertTrue(direction.isPresent());
        Direction directionvalue = direction.get();
        assertTrue(directionvalue == Direction.WEST);
    }







}
