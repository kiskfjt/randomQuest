package action.skills;

import action.ActionSkill;
import chr.Chr;
import others.IO;

public class ActionSkillPassionateTango extends ActionSkill {

	public ActionSkillPassionateTango(Chr me) {
		super(me);
		name = "情熱タンゴ";
		rangeMinInt = 30;
		rangeMaxInt = 50;
	}

	// 攻撃範囲：敵単体
	public boolean playerTarget() {
		return IO.selectSingleAliveTarget(me.party.enemy.member, me);
	}
	
	// ダメージ：物理、30～50の範囲
	public void execute() {
		IO.msgln("【%sの%s！】", me.name, name);
		
		int value = IO.randomNum(rangeMinInt, rangeMaxInt);

		me.targets.get(0).HP -= value;

		IO.msgln("%sのHPを%d吸収した！", me.targets.get(0).name, value);
	}

}
