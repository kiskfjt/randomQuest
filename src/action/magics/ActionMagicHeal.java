package action.magics;

import action.ActionMagic;
import chr.Chr;
import others.IO;

public class ActionMagicHeal extends ActionMagic {

	public ActionMagicHeal(Chr me) {
		super(me);
		name = "ヒール";
		MPCons = 10;
		rangeMin = 40;
		rangeMax = 60;
	}
	
	// 回復対象：味方単体
	public void playerTarget() {
		Chr tgt = IO.selectSingleTarget(me.party.member);
		me.targets.add(tgt);
	}
	
	// 回復：30～50の範囲
	public void execute() {
		IO.msgln("【%sの%s！】", me.name, name);
		
		int HPbefore = 0;
		int HPafter = 0;
		int value = 0;
		
		Chr target = me.targets.get(0);
		HPbefore = target.HP;
		
		target.HP += IO.randomNum(rangeMin, rangeMax);
		if (target.HP > target.maxHP) {
			target.HP = target.maxHP;
		}
		
		HPafter = target.HP;
		value = HPafter - HPbefore;
		IO.msgln("%sのHPが%d回復した！", target.name, value);
		
		me.MP -= MPCons;
	}
}
