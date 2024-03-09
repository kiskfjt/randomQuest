package action.magics;

import action.Action;
import action.ActionMagic;
import chr.Chr;
import others.Calc;
import others.IO;

public class ActionMagicDemonsLance extends ActionMagic {

	public ActionMagicDemonsLance(Chr me) {
		super(me);
		name = "デモンズランス";
		MPCons = 20;
		multi = 25;
		element = Action.ACTION_ELEMENT_DARK;
	}

	// 対象：敵全体
	public boolean playerTarget() {
		return IO.selectAllTargets(me.party.enemy.member, me);
	}

	// ダメージ：魔法、掛け算方式
	public void execute() {
		IO.msgln("【%sは%sを唱えた！】", me.name, name);

		Calc.mgcMultiDmg(me);

		me.MP -= MPCons;

	}
}
