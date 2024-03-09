package action.magics;

import action.Action;
import action.ActionMagic;
import chr.Chr;
import others.Calc;
import others.IO;

public class ActionMagicThunderBolt extends ActionMagic {

	public ActionMagicThunderBolt(Chr me) {
		super(me);
		name = "サンダーボルト";
		MPCons = 10;
		multi = 25;
		element = Action.ACTION_ELEMENT_THUNDER;
	}

	// 対象：敵単体
	public boolean playerTarget() {
		return IO.selectSingleAliveTarget(me.party.enemy.member, me);
	}

	// ダメージ：魔法、掛け算方式
	public void execute() {
		IO.changeTargetsRandomlyIfDead(me.party.enemy.member, me);

		IO.msgln("【%sは%sを唱えた！】", me.name, name);

		Calc.mgcSingleDmg(me);

		me.MP -= MPCons;

	}
}
