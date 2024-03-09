package actions;

import action.Action;
import action.ActionMagic;
import chr.Chr;
import others.Calc;
import others.IO;

public class ActionMagicCyclone extends ActionMagic {

	public ActionMagicCyclone(Chr me) {
		super(me);
		name = "サイクロン";
		MPCons = 20;
		multi = 25;
		element = Action.ACTION_ELEMENT_AIR;
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
