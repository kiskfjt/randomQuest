package chr.jobs;

import action.ActionBasicAttack;
import action.ActionBasicGuard;
import chr.Chr;

public class BraveChr extends Chr {
	private final int A_ATTACK = 0;
	public BraveChr(String name) {
		super(name, 500, 100, 50, 30, 50, 30, 25);
		
		actions.add(new ActionBasicAttack(this));
		actions.add(new ActionBasicGuard(this));
		
		setToNPC();
	}
	
	public void setToNPC() {
		command = this::nonPlayerCommand;
		
	}
	
	@Override
	public void nonPlayerCommand() {
		action = actions.get(A_ATTACK);
		action.target = this::attackTarget;
	}
	
	public void attackTarget() {
		
	}
}
