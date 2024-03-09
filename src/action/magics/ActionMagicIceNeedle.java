package action.magics;

import action.ActionMagic;
import chr.Chr;
import others.Calc;
import others.IO;

public class ActionMagicIceNeedle extends ActionMagic {

	public ActionMagicIceNeedle(Chr me) {
		super(me);
		MPCons = 10;
		multi = 25;
		name = "アイスニードル";
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
