package action;

import chr.Chr;
import others.Display;

public class ActionBasicAttack extends ActionBasic {
	public ActionBasicAttack(Chr me) {
		super(me);
		name = "こうげき";
		multi = 100;
	}
	
	public void action() {
	}
	
	public void playerTarget() {
		Chr tgt = Display.printAndSelectTargets(me.party.enemy.member);
		me.targets.add(tgt);
	}
	
	public void execute() {
		int Dmg = 0;
		System.out.println(me.name + "の" + name + "！");
		for (Chr c : me.targets) {
			if (c.isDead()) {
				System.out.println(c.name + "はしんでいる！");
			} else if (c.isAlive()) {
				Dmg = Display.calcMultiDmg(me, c, multi);
				System.out.println(c.name + "に" + Dmg + "のダメージ！");
				c.HP -= Dmg;
				Display.judgeHP(me, c);
			}
		}
		
	}
}
