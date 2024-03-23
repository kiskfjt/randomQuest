package equipment;

import chr.Chr;
import others.Calc;
import others.IO;

public class EquipmentFireClaw extends Equipment {
	public EquipmentFireClaw(Chr me) {
		super(me);
		name = "炎の爪";
		ATK = 30;
		elementPointFire = 10;
		addStatus();
		rangeMin = 30;
		rangeMax = 50;
	}
	
	// 攻撃対象：敵単体
	public boolean playerTarget() {
		return IO.selectSingleAliveTarget(me.party.enemy.member, me);
	}
	
	// 
	public void execute() {
		IO.msgln("【%sは%sをふりかざした！】", me.name, name);
		
		Calc.ignoreDefenseDmg(me, rangeMin, rangeMax);
	}

}
