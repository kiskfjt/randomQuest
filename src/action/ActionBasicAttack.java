package action;

import chr.Chr;
import others.Calc;
import others.IO;

public class ActionBasicAttack extends ActionBasic {
	public ActionBasicAttack(Chr me) {
		super(me);
		name = "こうげき";
		multi = 100;
		rangeMin = 80;
		rangeMax = 120;
	}
	
	public void action() {
	}
	
	public void playerTarget() {
		IO.selectSingleTarget(me.party.enemy.member, me);
	}
	
	public void execute() {
		int Dmg = 0;
		IO.msgln("【%sの%s！】", me.name, name);
		for (Chr c : me.targets) {
			if (c.isDead()) {
				System.out.println(c.name + "はしんでいる！");
			} else if (c.isAlive()) {
				Dmg = Calc.physSingleDmg(me, c);
				System.out.println(c.name + "に" + Dmg + "のダメージ！");
				c.HP -= Dmg;
				IO.judgeHP(me, c);
			}
		}
		
	}
}
