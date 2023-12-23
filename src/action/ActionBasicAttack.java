package action;

import chr.Chr;
import others.Calc;
import others.IO;

public class ActionBasicAttack extends ActionBasic {
	public ActionBasicAttack(Chr me) {
		super(me);
		name = "こうげき";
		multi = 50;
		rangeMin = 0.8;
		rangeMax = 1.2;
	}
	
	public void action() {
	}
	
	public boolean playerTarget() {
		return IO.selectSingleAliveTarget(me.party.enemy.member, me);
	}
	
	public void execute() {
		IO.msgln("【%sの%s！】", me.name, name);
		
		Calc.physSingleDmg(me);
		
	}
}
