import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class MonsterTest {

    @Rule
    public DatabaseRule database = new DatabaseRule();

    @Test
    public void monster_instantiatesCorrectly_true() {
        Monster testMonster = new Monster("Bubbles", 1);
        assertEquals(true, testMonster instanceof Monster);
    }

    @Test
    public void Monster_instantiatesWithName_String() {
        Monster testMonster = new Monster("Bubbles", 1);
        assertEquals("Bubbles", testMonster.getName());
    }

    @Test
    public void Monster_instantiatesWithPersonId_int() {
        Monster testMonster = new Monster("Bubbles", 1);
        assertEquals(1, testMonster.getPersonId());
    }

    @Test
    public void equals_returnsTrueIfNameAndPersonIdAreSame_true() {
        Monster testMonster = new Monster("Bubbles", 1);
        Monster anotherMonster = new Monster("Bubbles", 1);
        assertTrue(testMonster.equals(anotherMonster));
    }

    @Test
    public void save_returnsTrueIfDescriptionsAretheSame() {
        Monster testMonster = new Monster("Bubbles", 1);
        testMonster.save();
        assertTrue(Monster.all().get(0).equals(testMonster));
    }

    @Test
    public void save_assignsIdToMonster() {
        Monster testMonster = new Monster("Bubbles", 1);
        testMonster.save();
        Monster savedMonster = Monster.all().get(0);
        assertEquals(savedMonster.getId(), testMonster.getId());
    }

    @Test
    public void all_returnsAllInstancesOfMonster_true() {
        Monster firstMonster = new Monster("Bubbles", 1);
        firstMonster.save();
        Monster secondMonster = new Monster("Spud", 1);
        secondMonster.save();
        assertEquals(true, Monster.all().get(0).equals(firstMonster));
        assertEquals(true, Monster.all().get(1).equals(secondMonster));
    }

    @Test
    public void find_returnsMonsterWithSameId_secondMonster() {
        Monster firstMonster = new Monster("Bubbles", 1);
        firstMonster.save();
        Monster secondMonster = new Monster("Spud", 3);
        secondMonster.save();
        assertEquals(Monster.find(secondMonster.getId()), secondMonster);
    }

    @Test
    public void save_savesPersonIdIntoDB_true() {
        Person testPerson = new Person("Henry", "henry@henry.com");
        testPerson.save();
        Monster testMonster = new Monster("Bubbles", testPerson.getId());
        testMonster.save();
        Monster savedMonster = Monster.find(testMonster.getId());
        assertEquals(savedMonster.getPersonId(), testPerson.getId());
    }

    @Test
    public void monster_instantiatesWithHalfFullPlayLevel(){
        Monster testMonster = new Monster("Bubbles", 1);
        assertEquals(testMonster.getPlayLevel(), (Monster.MAX_PLAY_LEVEL / 2));
    }

    @Test
    public void monster_instantiatesWithHalfFullSleepLevel(){
        Monster testMonster = new Monster("Bubbles", 1);
        assertEquals(testMonster.getSleepLevel(), (Monster.MAX_SLEEP_LEVEL / 2));
    }
    @Test
    public void monster_instantiatesWithHalfFullFoodLevel(){
        Monster testMonster = new Monster("Bubbles", 1);
        assertEquals(testMonster.getFoodLevel(), (Monster.MAX_FOOD_LEVEL / 2));
    }

    @Test
    public void isAlive_confirmsMonsterIsAliveIfAllLevelsAboveMinimum_true(){
        Monster testMonster = new Monster("Bubbles", 1);
        assertEquals(testMonster.isAlive(), true);
    }

    @Test
    public void depleteLevels_reducesAllLevels(){
        Monster testMonster = new Monster("Bubbles", 1);
        testMonster.depleteLevels();
        assertEquals(testMonster.getFoodLevel(), (Monster.MAX_FOOD_LEVEL / 2) - 1);
        assertEquals(testMonster.getSleepLevel(), (Monster.MAX_SLEEP_LEVEL / 2) - 1);
        assertEquals(testMonster.getPlayLevel(), (Monster.MAX_PLAY_LEVEL / 2) - 1);
    }

    @Test
    public void isAlive_recognizesMonsterIsDeadWhenLevelsReachMinimum_false(){
        Monster testMonster = new Monster("Bubbles", 1);
        for(int i = Monster.MIN_ALL_LEVELS; i <= Monster.MAX_FOOD_LEVEL; i++){
            testMonster.depleteLevels();
        }
        assertEquals(testMonster.isAlive(), false);
    }

    @Test
    public void play_increasesMonsterPlayLevel(){
        Monster testMonster = new Monster("Bubbles", 1);
        testMonster.play();
        assertTrue(testMonster.getPlayLevel() > (Monster.MAX_PLAY_LEVEL / 2));
    }

    @Test
    public void sleep_increasesMonsterSleepLevel(){
        Monster testMonster = new Monster("Bubbles", 1);
        testMonster.sleep();
        assertTrue(testMonster.getSleepLevel() > (Monster.MAX_SLEEP_LEVEL / 2));
    }

    @Test
    public void feed_increasesMonsterFoodLevel(){
        Monster testMonster = new Monster("Bubbles", 1);
        testMonster.feed();
        assertTrue(testMonster.getFoodLevel() > (Monster.MAX_FOOD_LEVEL / 2));
    }

    @Test
    public void monster_foodLevelCannotGoBeyondMaxValue(){
        Monster testMonster = new Monster("Bubbles", 1);
        for(int i = Monster.MIN_ALL_LEVELS; i <= (Monster.MAX_FOOD_LEVEL + 2); i++){
            try {
                testMonster.feed();
            } catch (UnsupportedOperationException exception) {}
        }
        assertTrue(testMonster.getFoodLevel() <= Monster.MAX_FOOD_LEVEL);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void feed_throwsExceptionIfFoodLevelIsAtMaxValue(){
        Monster testMonster = new Monster("Bubbles", 1);
        for(int i = Monster.MIN_ALL_LEVELS; i <= (Monster.MAX_FOOD_LEVEL); i++){
            testMonster.feed();
        }
    }

    @Test(expected = UnsupportedOperationException.class)
    public void play_throwsExceptionIfPlayLevelIsAtMaxValue(){
        Monster testMonster = new Monster("Bubbles", 1);
        for(int i = Monster.MIN_ALL_LEVELS; i <= (Monster.MAX_PLAY_LEVEL); i++){
            testMonster.play();
        }
    }

    @Test
    public void monster_playLevelCannotGoBeyondMaxValue(){
        Monster testMonster = new Monster("Bubbles", 1);
        for(int i = Monster.MIN_ALL_LEVELS; i <= (Monster.MAX_PLAY_LEVEL); i++){
            try {
                testMonster.play();
            } catch (UnsupportedOperationException exception){ }
        }
        assertTrue(testMonster.getPlayLevel() <= Monster.MAX_PLAY_LEVEL);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void sleep_throwsExceptionIfSleepLevelIsAtMaxValue(){
        Monster testMonster = new Monster("Bubbles", 1);
        for(int i = Monster.MIN_ALL_LEVELS; i <= (Monster.MAX_SLEEP_LEVEL); i++){
            testMonster.sleep();
        }
    }

    @Test
    public void monster_sleepLevelCannotGoBeyondMaxValue(){
        Monster testMonster = new Monster("Bubbles", 1);
        for(int i = Monster.MIN_ALL_LEVELS; i <= (Monster.MAX_SLEEP_LEVEL); i++){
            try {
                testMonster.sleep();
            } catch (UnsupportedOperationException exception){ }
        }
        assertTrue(testMonster.getSleepLevel() <= Monster.MAX_SLEEP_LEVEL);
    }
    
}