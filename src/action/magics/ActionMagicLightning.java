package action.magics;

import action.ActionMagic;
import chr.Chr;
import others.Calc;
import others.IO;

public class ActionMagicLightning extends ActionMagic {

	public ActionMagicLightning(Chr me) {
		super(me);
		name = "いなずま";
		MPCons = 10;
		multi = 25;
	}

	// 攻撃範囲：敵全体
	public boolean playerTarget() {
		return IO.selectAllTargets(me.party.enemy.member, me);
	}

	// ダメージ：魔法、掛け算方式
	public void execute() {
		
		IO.msgln("【%sは%sを呼び寄せた！】", me.name, name);
		
		Calc.mgcMultiDmg(me);
		
		me.MP -= MPCons;
	}

}
