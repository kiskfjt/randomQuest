package action.magics;

import action.ActionMagic;
import chr.Chr;
import others.Calc;
import others.IO;

public class ActionMagicLightningStorm extends ActionMagic {

	public ActionMagicLightningStorm(Chr me) {
		super(me);
		name = "ゴジスパーク";
		multi = 40;
		MPCons = 25;
		missRate = 0;
	}

	// 対象：敵全体
	public boolean playerTarget() {
		return IO.selectAllTargets(me.party.enemy.member, me);
	}

	// ダメージ：魔法、掛け算方式
	public void execute() {
		IO.msgln("【%sの%s！】", me.name, name);
		IO.msgln("%sは地獄からいかずちを呼び寄せた！", me.name);
		IO.msgln("地獄のいかずちが辺りを薙ぎ払う！");
		
		Calc.mgcMultiDmg(me);
		
		me.MP -= MPCons;
	}

}
