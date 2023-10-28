package equipment;

import chr.Chr;
import others.Calc;
import others.IO;

public class EquipmentFireClaw extends Equipment {
	public EquipmentFireClaw(Chr me) {
		super(me);
		name = "炎の爪";
		multi = 120;
	}
	
	// 攻撃対象：敵単体
	public boolean playerTarget() {
		return IO.selectSingleTarget(me.party.enemy.member, me);
	}
	
	// 
	public void execute() {
		IO.msgln("【%sは%sをつかった！】", me.name, name);
		
		Calc.mgcSingleDmg(me);
	}

}
