package action;

import chr.Chr;
import others.Display;

public class ActionBasicAttack extends ActionBasic {
	public ActionBasicAttack(Chr me) {
		super(me);
		name = "こうげき";
	}
	
	public void action() {
	}
	
	public void playerTarget() {
		Chr tgt = Display.printAndSelectTargets(me.party.enemy.member);
		me.targets.add(tgt);
	}
	
	public void execute() {
		
		System.out.println(me.name + "のこうげき！");
	}
}
