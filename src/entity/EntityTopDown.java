package entity;

/**
 * Represents any Top Down tangible component in the game.
 * @author tyson
 *
 */
public abstract class EntityTopDown extends Entity{
	protected int defaultDiagonalSpeed;
	public static final String NORTH = "North";
	public static final String EAST = "East";
	public static final String SOUTH = "South";
	public static final String WEST = "West";
}
