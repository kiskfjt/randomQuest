package action.skills;

import action.ActionSkill;
import chr.Chr;
import others.IO;

public class ActionSkillDodgeDance extends ActionSkill {

	public ActionSkillDodgeDance(Chr me) {
		super(me);
		name = "みかわしきゃく";
		rangeMinInt = 2;
		rangeMaxInt = 3;
		actionType = ACTION_TYPE_DANCE;
	}

	// 効果範囲：自分自身
	public boolean playerTarget() {
		me.targets.add(me);
		return true;
	}

	// 効果：回避率50%、2～3ターン継続
	public void execute() {
		IO.msgln("【%sの%s！】", me.name, name);
		IO.msgln("%sは踊るような脚さばきを始めた！", me.name);
		
		me.evasionRate = 50;
		me.evasionTurn = IO.randomNum(rangeMinInt, rangeMaxInt);
	}

}
