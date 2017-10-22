package Tileset;


import java.io.Serializable;

import State.Coord;
import State.State;
import Tileset.Behaviour.Attack;
import Tileset.Behaviour.Direction;

public class Player extends DynamicObject implements Cloneable {
	private static final long serialVersionUID = 629680615400266941L;
	//private static Attack lightAttack = new Attack()
	private Direction currentFacing;
	private Attack light;
	private Attack special; //heavy
	private Attack selected;
	
	private int attackCooldown;
	
	private Direction dir = Direction.EAST;
	private boolean right;							// What direction we face (moving up and down)
	
	public Player(Coord position,  double hp, double damage, Attack light, Attack special, String img_path) {
		// type, width, height, coords, hp, damage
		super(ObjectType.PLAYER, position, hp, damage, img_path);
		this.light = light;
		this.special = special;
		this.selected = light; 
		this.right = true;
		this.attackCooldown = 0;
	}
	
	public Player(double hp, double damage, Attack light, Attack special, String img_path) {
		// type, width, height, coords, hp, damage
		super(ObjectType.PLAYER, hp, damage, img_path);
		this.light = light;
		//System.out.println("Hey" + this.attackCooldown);
		this.special = special;
		this.selected = light; 
		this.right = true;
		this.attackCooldown = 0;
	}
	
	
	public Player(double hp, double damage, String img_path) {
		// type, width, height, coords, hp, damage
		super(ObjectType.PLAYER, hp, damage, img_path);
		this.attackCooldown = 0;
	}
	
	@Override
	public void step(State s) {
		
		switch (getActionState()) {
		case ATTACK:
			//System.out.print(attackCooldown);
			//System.out.println(getFacing());
			selected.applyAttack(s, getCoord(), getFacing());
			if (attackCooldown > 0) {
				attackCooldown--;
			} else {
				this.setActionState(ActionState.MOVE);
			}
			break;
		case DISABLED:
			break;
		case MOVE:
			break;
		}
		super.step(s);
	}
	
	public void selectSpecial() {
		selected = special;
	}
	
	public void selectLight() {
		selected = light;
	}
	

	public void setFacing(Direction dir) {
		super.setFacing(dir);
		if (dir.equals(Direction.EAST)) {
			this.right = true;
		} else if (dir.equals(Direction.WEST)) {
			this.right = false;
		}
	}
	
//	public void setDirection(Direction dir) {
//		this.dir = dir;
//		if (dir.equals(Direction.EAST)) {
//			this.right = true;
//		} else if (dir.equals(Direction.WEST)) {
//			this.right = false;
//		}
//	}
	
	/*
	 * Returns if the player is facing right
	 */
	public boolean facingRight() {
		return this.right;
	}
	
	/*
	 * Sets the player to face right
	 */
	public void setFacingRight() {
		this.right = true;
	}
	
//	public Direction getDirection(){
//		return this.dir;
//	}
	
	@Override
	public Attack getAttack() {
		return selected;
	}
	
	public boolean canAttack() {
		return (getActionState() == ActionState.MOVE);
	}
	
	
	@Override
	public Player clone() {
		Player p = new Player( getHp(), getContactDamage(), light, special, getImgPath());
		p.setActionState(getActionState());
		return p;
	}
	
	@Override
	public void destroy(State s) {
		// does not destroy self
	}
	
	@Override
	public void setActionState(ActionState a) {
		super.setActionState(a);
		if (a == ActionState.ATTACK) {
			this.attackCooldown = light.getAttackSpeed();
			//System.out.print(this.attackCooldown);
		}
	}
}
